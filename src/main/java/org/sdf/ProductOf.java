package org.sdf;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ProductOf implements Product {
    private final String name;

    private final BigDecimal price;

    private final Timestamp date;

    public ProductOf(final String name, final BigDecimal price) {
        this(name, price, Timestamp.from(Instant.now()));
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public BigDecimal price() {
        return this.price;
    }

    @Override
    public Timestamp date() {
        return this.date;
    }
}
