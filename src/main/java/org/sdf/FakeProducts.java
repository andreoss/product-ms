package org.sdf;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public final class FakeProducts implements Products {
    private final Map<String, Product> storage = new ConcurrentHashMap<>();

    @Override
    public boolean exists(final String name) {
        return this.storage.containsKey(name);
    }

    @Override
    public Product find(final String name) {
        return Objects.requireNonNull(this.storage.get(name));
    }

    @Override
    public void remove(final Product product) {
        this.storage.remove(product.name());
    }

    @Override
    public void save(final Product product) {
        this.storage.put(product.name(), product);

    }

    @Override
    public Iterable<Product> all() {
        return this.storage.values();
    }
}
