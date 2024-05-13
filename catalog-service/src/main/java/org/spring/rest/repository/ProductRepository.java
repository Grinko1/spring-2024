package org.spring.rest.repository;

import org.spring.rest.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends CrudRepository<Product, Integer> {

//    native sql
//    @Query(value = "select * from  catalog.t_product where c_title ilike :filter", nativeQuery = true)
//    Jpql
//    @Query(value="select p from Product p where p.title ilike :filter")
//    named query from entity
    @Query(name = "Product.findAllByTitleLikeIgnoreCase", nativeQuery = true)
    Iterable<Product> findAllByTitleLikeIgnoreCase(@Param("filter") String filter);


}
