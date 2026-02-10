package com.ahorraposadas.scrapermsc.config;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "scraper.hiperlibertad")
public class HiperLibertadScraperProperties {

    private String cron;
    private String productCardSelector;
    private String titleSelector;
    private String productLinkSelector;
    private String imageSelector;
    private String priceContainerSelector;
    private String priceSpanSelector;
    private Duration requestDelay;

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
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

    public Duration getRequestDelay() {
        return requestDelay;
    }

    public void setRequestDelay(Duration requestDelay) {
        this.requestDelay = requestDelay;
    }
}
