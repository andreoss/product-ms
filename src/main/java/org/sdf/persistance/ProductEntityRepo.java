package org.sdf.persistance;

import org.springframework.data.repository.CrudRepository;

interface ProductEntityRepo extends
    CrudRepository<ProductEntity, String> {
}
