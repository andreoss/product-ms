package org.sdf.persistance;

import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.RequiredArgsConstructor;
import org.sdf.Product;

@RequiredArgsConstructor
public final class PersistedProduct implements Product {
    private final ProductEntity dto;

    @Override
    public String name() {
        return this.dto.getName();
    }

    @Override
    public BigDecimal price() {
        return this.dto.getPrice();
    }

    @Override
    public Timestamp date() {
        return this.dto.getCreatedDttm();
    }
}
