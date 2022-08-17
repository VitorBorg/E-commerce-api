package com.api.ecommerce.services;

import com.api.ecommerce.models.ProductModel;
import com.api.ecommerce.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    final ProductRepository productRepository;

    public ProductService(ProductRepository pR){
        this.productRepository = pR;
    }

    public Optional<ProductModel> findById(UUID id){
        return productRepository.findById(id);
    }

    public Page<ProductModel> findAll(Pageable pageable){
        return productRepository.findAll(pageable);
    }

    @Transactional
    public ProductModel save(ProductModel productModel){
        return productRepository.save(productModel);
    }

    @Transactional
    public void delete(ProductModel productModel){
        productRepository.delete(productModel);
    }
}
