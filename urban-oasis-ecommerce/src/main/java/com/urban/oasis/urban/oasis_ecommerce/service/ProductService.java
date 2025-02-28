package com.urban.oasis.urban.oasis_ecommerce.service;

import com.urban.oasis.urban.oasis_ecommerce.exception.ProductException;
import com.urban.oasis.urban.oasis_ecommerce.model.Product;
import com.urban.oasis.urban.oasis_ecommerce.model.Seller;
import com.urban.oasis.urban.oasis_ecommerce.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    public Product createProduct(CreateProductRequest req, Seller seller);
    public void deleteProduct(Long productId) throws ProductException;
    public Product updateProduct(Long productId, Product product) throws ProductException;
    Product findProductById(Long productId) throws ProductException;
    List<Product> searchProduct(String query);
    public Page<Product> getAllProducts(String category, String brand, String colors, String sizes,
                                        Integer minPrice, Integer maxPrice, Integer minDiscount,
                                        String sort, String stock, Integer pageNumber);
    List<Product> getProductBySellerId(Long sellerId);
}
