package org.spring.rest.repository;

import org.spring.rest.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    //select * from catalog.t_product where c_title ilike :some world
    Iterable<Product> findAllByTitleLikeIgnoreCase(String filter);
}
