package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Sort;

import java.util.List;

import exercise.model.Product;
import exercise.repository.ProductRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    // BEGIN
    @GetMapping
    public ResponseEntity<List<Product>> findByPriceBetween(@RequestParam(required = false) Integer min, @RequestParam(required = false) Integer max) {
        List<Product> products;
        var sort = Sort.by(Sort.Order.asc("price"));

        if (min != null && max != null) products = productRepository.findByPriceBetween(min, max, sort);
        else if (min != null) products = productRepository.findByPriceGreaterThan(min, sort);
        else if (max != null) products = productRepository.findByPriceLessThan(max, sort);
        else products = productRepository.findAll(Sort.by(Sort.Order.asc("price")));

        return ResponseEntity.ok(products);
    }
    // END

    @GetMapping(path = "/{id}")
    public Product show(@PathVariable long id) {

        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        return product;
    }
}
