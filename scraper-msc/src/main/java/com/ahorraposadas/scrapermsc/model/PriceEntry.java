package com.ahorraposadas.scrapermsc.model;

import java.math.BigDecimal;
import java.time.Instant;

public class PriceEntry {

    private BigDecimal price;
    private Instant capturedAt;

    public PriceEntry() {
    }

    public PriceEntry(BigDecimal price, Instant capturedAt) {
        this.price = price;
        this.capturedAt = capturedAt;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Instant getCapturedAt() {
        return capturedAt;
    }

    public void setCapturedAt(Instant capturedAt) {
        this.capturedAt = capturedAt;
    }
}
