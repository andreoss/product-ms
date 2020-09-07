package org.sdf.persistance;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.sdf.ProductOf;
import org.sdf.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@DataJdbcTest
@Import(PersistedProducts.class)
final class PersistedProductsTest {
    @Autowired
    private Products products;

    @Test
    @Sql(statements =
        "INSERT INTO PRODUCT VALUES('foo', 1.0, current_timestamp())")
    void findsRecord() {
        MatcherAssert.assertThat(
            this.products.find("foo"),
            Matchers.notNullValue()
        );
    }

    @Test
    void savesRecord() {
        this.products.save(
            new ProductOf("bar", BigDecimal.TEN)
        );
        MatcherAssert.assertThat(
            this.products.find("bar").price(),
            Matchers.is(BigDecimal.TEN)
        );
    }


    @Test
    @Sql(statements =
        "INSERT INTO PRODUCT VALUES('foo', 1.0, current_timestamp())")
    void existsIfInDb() {
        MatcherAssert.assertThat(
            this.products.exists("foo"),
            Matchers.is(true)
        );
        this.products.remove(this.products.find("foo"));
        MatcherAssert.assertThat(
            this.products.exists("foo"),
            Matchers.is(false)
        );
    }


    @Test
    @Sql(statements = {
        "INSERT INTO PRODUCT VALUES('foo', 1.0, current_timestamp())",
        "INSERT INTO PRODUCT VALUES('bar', 2.0, current_timestamp())",
        "INSERT INTO PRODUCT VALUES('baz', 3.0, current_timestamp())",
    })
    void findsAll() {
        MatcherAssert.assertThat(
            this.products.all(),
            Matchers.iterableWithSize(3)
        );
    }

    @Test
    void storesWithDate() {
        final var time = Timestamp.from(Instant.now());
        this.products.save(
            new ProductOf("foo", BigDecimal.valueOf(10), time)
        );
        MatcherAssert.assertThat(
            this.products.find("foo").date(),
            Matchers.equalTo(time)
        );
    }
}