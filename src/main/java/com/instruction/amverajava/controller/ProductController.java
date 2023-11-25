package com.instruction.amverajava.controller;

import com.instruction.amverajava.models.Product;
import com.instruction.amverajava.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> findAll() {
        return productService.getAll();
    }

    @GetMapping("/total")
    public Double getTotalPrice() {
        return productService.totalPrice();
    }


    @GetMapping("/find")
    public Product findByName(
            @RequestParam(defaultValue = "") String name
    ) {
        return productService.findByName(name);
    }

    @PostMapping
    public void save(
            @RequestBody Product product
    ) {
        productService.save(product);
    }

}
