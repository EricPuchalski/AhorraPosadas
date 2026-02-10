package com.ahorraposadas.scrapermsc.config;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "scraper.california")
public class CaliforniaScraperProperties {

    private String cron;
    private String baseUrl;
    private String productCardSelector;
    private String titleSelector;
    private String imageSelector;
    private String productLinkSelector;
    private String priceSelector;
    private String nextPageSelector;
    private Duration requestDelay;

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

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
