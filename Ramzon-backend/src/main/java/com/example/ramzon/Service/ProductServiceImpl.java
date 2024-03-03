package com.example.ramzon.Service;

import com.example.ramzon.Exception.ProductException;
import com.example.ramzon.Model.Category;
import com.example.ramzon.Model.Product;
import com.example.ramzon.Repository.CategoryRepository;
import com.example.ramzon.Repository.ProductRepository;
import com.example.ramzon.Request.CreateProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{


    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Product createProduct(CreateProductRequest req) {

        Category topLevel = categoryRepository.findByName(req.getTopLevelCategory());

        if (topLevel == null){
            Category topLevelCategory = new Category();
            topLevelCategory.setName(req.getTopLevelCategory());
            topLevelCategory.setLevel(1);

            topLevel = categoryRepository.save(topLevelCategory);
        }
        Category secondLevel = categoryRepository.findByNameAndParent(req.getTopLevelCategory(),topLevel.getName());

        if (secondLevel == null){
            Category secondLevelCategory = new Category();
            secondLevelCategory.setName(req.getSecLevelCategory());
            secondLevelCategory.setLevel(2);

            secondLevel = categoryRepository.save(secondLevelCategory);

        }

        Category thirdLevel = categoryRepository.findByNameAndParent(req.getTopLevelCategory(),secondLevel.getName());

        if (thirdLevel == null){
            Category thirdLevelCategory = new Category();
            thirdLevelCategory.setName(req.getThirdLevelCategory());
            thirdLevelCategory.setLevel(3);

            thirdLevel = categoryRepository.save(thirdLevelCategory);

        }

        Product product = new Product();
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setBrand(req.getBrand());
        product.setDescription(req.getDescription());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountPresent(req.getDiscountPresent());
        product.setImageUrl(req.getImageUrl());
        product.setPrice(req.getPrice());
        product.setSizes(req.getSize());
        product.setQuantity(req.getQuantity());
        product.setCategory(thirdLevel);
        product.setCreatedAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product = findByProductById(productId);
        product.getSizes().clear();
        productRepository.delete(product);

        return "Product Deleted Succesfully";
    }

    @Override
    public Product updateProduct(Product product, Long productId) throws ProductException {
        Product product1 = findByProductById(productId);
        if (product.getQuantity()!=0){
            product1.setQuantity(product.getQuantity());
        }
        return productRepository.save(product1);
    }

    @Override
    public Product findByProductById(Long productId) throws ProductException {
        Optional<Product> list = productRepository.findById(productId);
        if (list.isPresent()){
            return list.get();
        }
        throw  new ProductException("Product not found with id");
    }

    @Override
    public List<Product> findProductByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> color, List<String> sizes, Integer minPrice,Integer minDiscount, Integer maxPrice, String sort, Integer pageNumber, Integer pageSize,String stock) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        List<Product> products = productRepository.filterProducts(category,minPrice,maxPrice,minDiscount,sort);

        if (!color.isEmpty()){
            products = products.stream().filter(p -> color.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor()))).collect(Collectors.toList());

        } if (stock!=null) {
            if (stock.equalsIgnoreCase("in_Stocks")) {
                products = products.stream().filter(p -> p.getQuantity() >0).collect(Collectors.toList());
            } else if(stock.equalsIgnoreCase("out_of_stocks")){
                products = products.stream().filter(p -> p.getQuantity() >0).collect(Collectors.toList());
            }

        }
        int startIndex = (int) pageable.getOffset();
        int endIndex  = Math.min(startIndex+pageable.getPageSize(),products.size());

        List<Product> pageContent = products.subList(startIndex,endIndex);

        Page<Product> filteredContent = new PageImpl<>(pageContent,pageable,products.size());

        return filteredContent;
    }

    @Override
    public List<Product> searchProduct(String q) {

        return productRepository.searchProduct(q);
    }
}
