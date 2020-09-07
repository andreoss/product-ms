package org.sdf.rest;

import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.sql.Timestamp;
import java.util.List;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.sdf.FakeProducts;
import org.sdf.ProductOf;
import org.sdf.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
@Import(FakeProducts.class)
@ComponentScan(basePackageClasses = ProductEndpoint.class)
final class ProductEndpointTest {
    @Autowired
    private Products products;
    @Autowired
    private MockMvc mvc;

    @Test
    void findsAll() throws Exception {
        this.products.save(new ProductOf("FOO", BigDecimal.valueOf(10)));
        this.products.save(new ProductOf("BAR", BigDecimal.valueOf(20)));
        this.mvc.perform(MockMvcRequestBuilders.get("/products"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.jsonPath(
                    "$._embedded.productRepList",
                    Matchers.hasSize(2)
                )
            );
    }

    @Test
    void findsOne() throws Exception {
        this.products.save(new ProductOf("foo", BigDecimal.valueOf(10)));
        this.mvc.perform(MockMvcRequestBuilders.get("/products/foo"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.price", Matchers.is(10))
            )
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.name", Matchers.is("foo"))
            );
    }

    @Test
    void gets404IfNoSuchItem() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/products/xxx"))
            .andExpect(MockMvcResultMatchers.status().is(HttpURLConnection.HTTP_NOT_FOUND));
    }

    @Test
    void gets404IfNoSuchItemWhenDeletes() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.delete("/products/xxx"))
            .andExpect(MockMvcResultMatchers.status().is(HttpURLConnection.HTTP_NOT_FOUND));
    }

    @Test
    void deletes() throws Exception {
        this.products.save(new ProductOf("foo", BigDecimal.valueOf(10)));
        this.mvc.perform(MockMvcRequestBuilders.delete("/products/foo"))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        MatcherAssert.assertThat(
            this.products.exists("foo"),
            Matchers.is(false)
        );
    }

    @Test
    void adds() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\"name\": \"xyz\", "
                    + "\"price\": 123.4, "
                    + "\"date\": \"2000-01-01T00:00:00\"}"
            )
        )
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
            .andReturn();
        final var result = this.products.find("xyz");
        System.err.println(result);
        MatcherAssert.assertThat(
            List.of(result.name(), result.price(), result.date()),
            Matchers.contains(
                Matchers.is("xyz"),
                Matchers.is(BigDecimal.valueOf(123.4)),
                Matchers.isA(Timestamp.class)
            )
        );
    }

    @Test
    void failsToAddIfAlreadyExists() throws Exception {
        this.products.save(new ProductOf("foo", BigDecimal.valueOf(10)));
        this.mvc.perform(MockMvcRequestBuilders.post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"foo\", \"price\": 123.4}")
        ).andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}