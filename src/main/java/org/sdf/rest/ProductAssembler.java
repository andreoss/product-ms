package org.sdf.rest;

import org.sdf.Product;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public final class ProductAssembler implements
    RepresentationModelAssembler<Product, EntityModel<ProductRep>> {
    @Override
    public EntityModel<ProductRep> toModel(
        final Product entity
    ) {
        return EntityModel.of(
            ProductRep
                .builder()
                .name(entity.name())
                .price(entity.price())
                .date(entity.date())
                .build(),
            Link.of(
                WebMvcLinkBuilder
                    .linkTo(
                        WebMvcLinkBuilder
                            .methodOn(ProductEndpoint.class)
                            .findOne(entity.name())
                    ).toUriComponentsBuilder().toUriString(),
                IanaLinkRelations.SELF
            ),
            Link.of(
                WebMvcLinkBuilder
                    .linkTo(
                        WebMvcLinkBuilder
                            .methodOn(ProductEndpoint.class)
                            .findAll()
                    ).toUriComponentsBuilder().toUriString(),
                IanaLinkRelations.COLLECTION_VALUE
            )
        );
    }

}
