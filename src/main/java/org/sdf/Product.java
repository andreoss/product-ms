package org.sdf;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Product.
 */
public interface Product {
    String name();

    BigDecimal price();

    Timestamp date();
}
