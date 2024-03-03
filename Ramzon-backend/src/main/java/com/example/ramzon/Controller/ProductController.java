package com.example.ramzon.Controller;

import com.example.ramzon.Exception.ProductException;
import com.example.ramzon.Model.Product;
import com.example.ramzon.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> findProductByCategoryHandler(@RequestParam String category, @RequestParam List<String> color,
                                                                      @RequestParam  List<String> sizes, @RequestParam Integer minPrice,
                                                                      @RequestParam Integer minDiscount, @RequestParam Integer maxPrice, @RequestParam String sort,
                                                                      @RequestParam Integer pageNumber, @RequestParam Integer pageSize, @RequestParam String stock) {
        Page<Product> res = productService.getAllProduct(category,color,sizes,minPrice,minDiscount,maxPrice,sort,pageNumber,pageSize,stock);
        System.out.println("CompleteProduct");

        return new  ResponseEntity(res, HttpStatus.ACCEPTED);


    }

    @GetMapping("/products/id/{productId}")
    public ResponseEntity<Product> findProductByHandler(@PathVariable Long productId) throws ProductException{
        Product products = productService.findByProductById(productId);

        return  new ResponseEntity<>(products,HttpStatus.OK);

    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProductHandler(@RequestParam String q){

        List <Product> products  = productService.searchProduct(q);

        return new ResponseEntity<>(products,HttpStatus.OK);
    }

}
