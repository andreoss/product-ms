package org.sdf.persistance;

import java.util.ArrayList;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.sdf.Product;
import org.sdf.Products;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersistedProducts implements Products {
    private final CrudRepository<ProductEntity, String> repo;

    @Override
    public boolean exists(final String name) {
        return this.repo.findById(name).isPresent();
    }

    @Override
    public Product find(final String name) {
        final var found = this.repo.findById(name);
        return found.map(PersistedProduct::new).orElseThrow();
    }

    @Override
    public void remove(final Product product) {
        this.repo.deleteById(product.name());
    }

    @Override
    public void save(final Product product) {
        this.repo.save(
            ProductEntity
                .builder()
                .name(product.name())
                .price(product.price())
                .createdDttm(product.date())
                .build()
        );
    }

    @Override
    public Iterable<Product> all() {
        final Iterable<ProductEntity> all = this.repo.findAll();
        final Collection<Product> result = new ArrayList<>();
        for (final var entity : all) {
            result.add(new PersistedProduct(entity));
        }
        return result;
    }
}
