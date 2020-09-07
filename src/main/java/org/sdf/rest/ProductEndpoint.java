package org.sdf.rest;

import lombok.RequiredArgsConstructor;
import org.sdf.Product;
import org.sdf.ProductOf;
import org.sdf.Products;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;


@RestController
@RequiredArgsConstructor
public class ProductEndpoint {
    private final Products products;

    private final RepresentationModelAssembler<Product,
        EntityModel<ProductRep>> assembler;

    @GetMapping("/products")
    public ResponseEntity<CollectionModel<EntityModel<ProductRep>>> findAll() {
        return ResponseEntity.ok(
            this.assembler.toCollectionModel(this.products.all())
        );
    }

    @GetMapping("/products/{name}")
    public ResponseEntity<EntityModel<ProductRep>> findOne(
        @PathVariable final String name
    ) {
        if (this.products.exists(name)) {
            return ResponseEntity.ok(
                this.assembler.toModel(this.products.find(name))
            );
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/products/{name}")
    public ResponseEntity<EntityModel<ProductRep>> delete(
        @PathVariable final String name
    ) {
        final ResponseEntity<EntityModel<ProductRep>> result;
        if (this.products.exists(name)) {
            this.products.remove(this.products.find(name));
            result = ResponseEntity.noContent().build();
        } else {
            result = ResponseEntity.notFound().build();
        }
        return result;
    }


    @PostMapping("/products")
    public ResponseEntity<EntityModel<ProductRep>> add(
        @RequestBody ProductRep rep
    ) {
        final ResponseEntity<EntityModel<ProductRep>> result;
        final var prod = new ProductOf(
            rep.getName(),
            rep.getPrice(),
            Objects.requireNonNullElseGet(
                rep.getDate(),
                () -> Timestamp.from(Instant.now())
            )
        );
        if (this.products.exists(rep.getName())) {
            result = ResponseEntity.unprocessableEntity().build();
        } else {
            this.products.save(prod);
            result = ResponseEntity.created(
                WebMvcLinkBuilder
                    .linkTo(
                        WebMvcLinkBuilder
                            .methodOn(ProductEndpoint.class)
                            .findOne(prod.name())
                    ).toUri()
            ).build();
        }
        return result;
    }
}
