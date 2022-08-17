package com.api.ecommerce.controllers;

import com.api.ecommerce.dtos.ProductDto;
import com.api.ecommerce.models.ProductModel;
import com.api.ecommerce.services.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins="*", maxAge = 3600)
@RequestMapping("/product")
public class ProductController {

    final ProductService productService;

    public ProductController(ProductService pS){
        productService = pS;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProduct(@PathVariable(value = "id") UUID id){
        Optional<ProductModel> productModelOptional = productService.findById(id);

        if(!productModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The product was not found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(productModelOptional.get());
    }

    @GetMapping
    public ResponseEntity<Object> getAllProducts(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<Object> saveProduct(@RequestBody @Valid ProductDto productDto){
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productDto, productModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productModel));

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id,
                                          @RequestBody @Valid ProductDto productDto){
        Optional<ProductModel> productModelOptional = productService.findById(id);
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productDto, productModel);
        productModel.setId(productModelOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(productService.save(productModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id){
        Optional<ProductModel> productModelOptional = productService.findById(id);
        productService.delete(productModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("The product has been deleted.");
    }
}
