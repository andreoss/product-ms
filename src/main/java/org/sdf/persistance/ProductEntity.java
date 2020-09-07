package org.sdf.persistance;

import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Table("PRODUCT")
@Builder(toBuilder = true)
@Getter(AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductEntity implements Persistable<String> {
    @Id
    private String name;

    private BigDecimal price;

    private Timestamp createdDttm;

    @Override
    public String getId() {
        return this.name;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
