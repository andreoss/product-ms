package org.sdf.rest;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * Product on Representation level.
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
class ProductRep {
    @NotNull
    private final String name;

    @NotNull
    private final BigDecimal price;

    @Builder.Default
    private final Timestamp date = Timestamp.from(Instant.now());
}
