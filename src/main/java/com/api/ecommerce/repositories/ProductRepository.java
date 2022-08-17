package com.api.ecommerce.repositories;

import com.api.ecommerce.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID> {

    Optional<ProductModel> findByPrice(float price);

}
