package com.ahorraposadas.scrapermsc.event;

import java.math.BigDecimal;
import java.time.Instant;

public record PriceChangeEvent(
        String productId,
        String productUrl,
        String name,
        BigDecimal previousPrice,
        BigDecimal newPrice,
        Instant detectedAt
) {
}
