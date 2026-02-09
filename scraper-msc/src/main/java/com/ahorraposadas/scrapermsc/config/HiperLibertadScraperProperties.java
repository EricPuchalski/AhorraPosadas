package com.ahorraposadas.scrapermsc.config;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "scraper.hiperlibertad")
public class HiperLibertadScraperProperties {

    private Duration requestDelay = Duration.ofMillis(250);
    private String productCardSelector = ".vtex-search-result-3-x-galleryItem";
    private String titleSelector = ".vtex-product-summary-2-x-productBrand";
    private String productLinkSelector = ".vtex-product-summary-2-x-clearLink";
    private String imageSelector = ".vtex-product-summary-2-x-imageNormal";
    private String priceContainerSelector = ".vtex-product-price-1-x-sellingPriceValue";
    private String priceSpanSelector = "span";

    public Duration getRequestDelay() {
        return requestDelay;
    }

    public void setRequestDelay(Duration requestDelay) {
        this.requestDelay = requestDelay;
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

    public String getProductLinkSelector() {
        return productLinkSelector;
    }

    public void setProductLinkSelector(String productLinkSelector) {
        this.productLinkSelector = productLinkSelector;
    }

    public String getImageSelector() {
        return imageSelector;
    }

    public void setImageSelector(String imageSelector) {
        this.imageSelector = imageSelector;
    }

    public String getPriceContainerSelector() {
        return priceContainerSelector;
    }

    public void setPriceContainerSelector(String priceContainerSelector) {
        this.priceContainerSelector = priceContainerSelector;
    }

    public String getPriceSpanSelector() {
        return priceSpanSelector;
    }

    public void setPriceSpanSelector(String priceSpanSelector) {
        this.priceSpanSelector = priceSpanSelector;
    }
}
