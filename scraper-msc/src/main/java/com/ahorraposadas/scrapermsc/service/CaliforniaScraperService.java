package com.ahorraposadas.scrapermsc.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.ahorraposadas.scrapermsc.config.CaliforniaScraperProperties;
import com.ahorraposadas.scrapermsc.event.PriceChangeEvent;
import com.ahorraposadas.scrapermsc.model.PriceEntry;
import com.ahorraposadas.scrapermsc.model.Product;
import com.ahorraposadas.scrapermsc.repository.ProductRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CaliforniaScraperService implements ScraperService {

    private static final Logger logger = LoggerFactory.getLogger(CaliforniaScraperService.class);
    private static final String PRICE_TOPIC = "price-changes";

    private final ProductRepository productRepository;
    private final KafkaTemplate<String, PriceChangeEvent> kafkaTemplate;
    private final CaliforniaScraperProperties properties;

    public CaliforniaScraperService(ProductRepository productRepository,
                                    KafkaTemplate<String, PriceChangeEvent> kafkaTemplate,
                                    CaliforniaScraperProperties properties) {
        this.productRepository = productRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.properties = properties;
    }

    @Override
    @Scheduled(cron = "${scraper.california.cron:0 0 19 * * ?}")
    public void scrapeProducts() {
        Set<String> foundProductUrls = new HashSet<>();
        int pageNumber = 1;
        boolean hasNextPage = true;

        while (hasNextPage) {
            String pageUrl = properties.getBaseUrl() + "?product_page=" + pageNumber;
            logger.info("Procesando página {}: {}", pageNumber, pageUrl);
            try {
                Document document = Jsoup.connect(pageUrl)
                        .userAgent("Mozilla/5.0")
                        .timeout(15_000)
                        .get();
                Elements productElements = document.select(properties.getProductCardSelector());
                if (productElements.isEmpty()) {
                    logger.info("No se encontraron productos en la página {}.", pageNumber);
                    break;
                }

                for (Element productElement : productElements) {
                    String productUrl = processProductElement(productElement);
                    if (productUrl != null) {
                        foundProductUrls.add(productUrl);
                    }
                }

                hasNextPage = document.selectFirst(properties.getNextPageSelector()) != null;
                if (hasNextPage) {
                    pageNumber++;
                    Thread.sleep(properties.getRequestDelay().toMillis());
                }
            } catch (IOException e) {
                logger.error("Error procesando la página {}", pageNumber, e);
                break;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.warn("Scraping interrumpido en la página {}", pageNumber, e);
                break;
            }
        }

        updateProductAvailability(foundProductUrls);
    }

    private String processProductElement(Element productElement) {
        try {
            Element titleElement = productElement.selectFirst(properties.getTitleSelector());
            Element imageElement = productElement.selectFirst(properties.getImageSelector());
            Element linkElement = productElement.selectFirst(properties.getProductLinkSelector());
            Element priceElement = productElement.selectFirst(properties.getPriceSelector());

            if (titleElement == null || imageElement == null || linkElement == null || priceElement == null) {
                logger.warn("Producto omitido por campos faltantes.");
                return null;
            }

            String title = titleElement.text();
            String productUrl = linkElement.absUrl("href");
            String imageUrl = imageElement.absUrl("src");
            BigDecimal price = parsePrice(priceElement.text());

            Optional<Product> existingProduct = productRepository.findByProductUrl(productUrl);
            if (existingProduct.isPresent()) {
                updateExistingProduct(existingProduct.get(), title, imageUrl, price);
            } else {
                insertNewProduct(title, imageUrl, productUrl, price);
            }

            return productUrl;
        } catch (Exception e) {
            logger.warn("Error procesando producto", e);
            return null;
        }
    }

    private void updateExistingProduct(Product product, String title, String imageUrl, BigDecimal price) {
        boolean priceChanged = product.getCurrentPrice() != null && price.compareTo(product.getCurrentPrice()) != 0;
        BigDecimal previousPrice = product.getCurrentPrice();

        product.setName(title);
        product.setImageUrl(imageUrl);
        product.setCurrentPrice(price);
        product.setActive(true);
        product.setLastCheckedAt(Instant.now());
        product.addPriceEntry(new PriceEntry(price, product.getLastCheckedAt()));

        productRepository.save(product);

        if (priceChanged) {
            publishPriceChange(product, previousPrice, price);
        }
    }

    private void insertNewProduct(String title, String imageUrl, String productUrl, BigDecimal price) {
        Product product = new Product(title, productUrl, imageUrl, price);
        productRepository.save(product);
        publishPriceChange(product, null, price);
    }

    private void publishPriceChange(Product product, BigDecimal previousPrice, BigDecimal newPrice) {
        PriceChangeEvent event = new PriceChangeEvent(
                product.getId(),
                product.getProductUrl(),
                product.getName(),
                previousPrice,
                newPrice,
                Instant.now()
        );
        kafkaTemplate.send(PRICE_TOPIC, product.getProductUrl(), event);
    }

    private void updateProductAvailability(Set<String> foundProductUrls) {
        if (foundProductUrls.isEmpty()) {
            logger.warn("No se encontraron productos para actualizar disponibilidad.");
            return;
        }
        productRepository.findByActiveTrue().forEach(product -> {
            if (!foundProductUrls.contains(product.getProductUrl())) {
                product.setActive(false);
                product.setLastCheckedAt(Instant.now());
                productRepository.save(product);
            }
        });
    }

    private BigDecimal parsePrice(String priceText) {
        String cleanedText = priceText.replaceAll("[^\\d.,]", "");
        if (cleanedText.isBlank()) {
            return BigDecimal.ZERO;
        }

        String normalized = cleanedText;
        int lastComma = cleanedText.lastIndexOf(',');
        int lastDot = cleanedText.lastIndexOf('.');
        if (lastComma > lastDot) {
            normalized = cleanedText.replace(".", "").replace(",", ".");
        } else {
            normalized = cleanedText.replace(",", "");
        }

        return new BigDecimal(normalized);
    }
}
