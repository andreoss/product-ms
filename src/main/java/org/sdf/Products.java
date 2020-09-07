package org.sdf;

public interface Products {
    boolean exists(String name);

    Product find(String name);

    void remove(Product product);

    void save(Product product);

    Iterable<Product> all();
}
