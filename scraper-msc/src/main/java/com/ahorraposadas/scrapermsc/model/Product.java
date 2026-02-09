package com.ahorraposadas.scrapermsc.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class Product {

    @Id
    private String id;
    private String name;
    private String productUrl;
    private String imageUrl;
    private BigDecimal currentPrice;
    private Instant lastCheckedAt;
    private boolean active = true;
    private List<PriceEntry> priceHistory = new ArrayList<>();

    public Product() {
    }

    public Product(String name, String productUrl, String imageUrl, BigDecimal currentPrice) {
        this.name = name;
        this.productUrl = productUrl;
        this.imageUrl = imageUrl;
        this.currentPrice = currentPrice;
        this.lastCheckedAt = Instant.now();
        this.priceHistory.add(new PriceEntry(currentPrice, this.lastCheckedAt));
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Instant getLastCheckedAt() {
        return lastCheckedAt;
    }

    public void setLastCheckedAt(Instant lastCheckedAt) {
        this.lastCheckedAt = lastCheckedAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<PriceEntry> getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(List<PriceEntry> priceHistory) {
        this.priceHistory = priceHistory;
    }

    public void addPriceEntry(PriceEntry entry) {
        this.priceHistory.add(entry);
    }
}
