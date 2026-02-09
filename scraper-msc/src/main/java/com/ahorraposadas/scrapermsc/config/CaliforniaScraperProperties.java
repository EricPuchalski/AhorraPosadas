package com.ahorraposadas.scrapermsc.config;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "scraper.california")
public class CaliforniaScraperProperties {

    private String baseUrl = "https://www.californiasa.com.ar/productos";
    private String productCardSelector = ".csc-product-card";
    private String titleSelector = ".csc-product-title a";
    private String imageSelector = ".attachment-woocommerce_thumbnail";
    private String productLinkSelector = ".csc-product-image-link";
    private String priceSelector = ".csc-product-price .woocommerce-Price-amount";
    private String nextPageSelector = ".next";
    private Duration requestDelay = Duration.ofMillis(250);

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getProductCardSelector() {
        return productCardSelector;
    }

    public void setProductCardSelector(String productCardSelector) {
        this.productCardSelector = productCardSelector;
    }

    public String getTitleSelector() {
        return titleSelector;
    }

    public void setTitleSelector(String titleSelector) {
        this.titleSelector = titleSelector;
    }

    public String getImageSelector() {
        return imageSelector;
    }

    public void setImageSelector(String imageSelector) {
        this.imageSelector = imageSelector;
    }

    public String getProductLinkSelector() {
        return productLinkSelector;
    }

    public void setProductLinkSelector(String productLinkSelector) {
        this.productLinkSelector = productLinkSelector;
    }

    public String getPriceSelector() {
        return priceSelector;
    }

    public void setPriceSelector(String priceSelector) {
        this.priceSelector = priceSelector;
    }

    public String getNextPageSelector() {
        return nextPageSelector;
    }

    public void setNextPageSelector(String nextPageSelector) {
        this.nextPageSelector = nextPageSelector;
    }

    public Duration getRequestDelay() {
        return requestDelay;
    }

    public void setRequestDelay(Duration requestDelay) {
        this.requestDelay = requestDelay;
    }
}
