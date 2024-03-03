package com.example.ramzon.Service;

import com.example.ramzon.Exception.ProductException;
import com.example.ramzon.Model.Product;
import com.example.ramzon.Request.CreateProductRequest;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    public Product createProduct(CreateProductRequest req);

    public String deleteProduct(Long productId) throws ProductException;

    public Product updateProduct(Product product, Long productId) throws  ProductException;

    public Product findByProductById(Long productId) throws  ProductException;

    public List<Product> findProductByCategory(String category);

    public Page<Product> getAllProduct(String category, List<String> color, List<String> sizes, Integer minPrice,Integer minDiscount, Integer maxPrice, String sort
    ,Integer pageNumber,Integer pageSize,String stock);

    List<Product> searchProduct(String q);
}
