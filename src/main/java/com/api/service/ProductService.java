package com.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import com.api.model.Product;
import com.api.repository.ProductRepository;
import com.api.dto.ProductDTO;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Retrieves a list of all products.
     *
     * @return a list of product DTOs
     */
    public List<ProductDTO> findAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertProductToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a list of products sorted in ascending order based on the specified
     * field.
     *
     * @param field the field to sort the products by
     * @return a list of product DTOs sorted in ascending order
     */
    public List<ProductDTO> findProductsWithSorting(String field) {
        return productRepository.findAll(Sort.by(Sort.Direction.ASC, field)).stream()
                .map(this::convertProductToDTO)
                .collect(Collectors.toList());
    }

    public Page<ProductDTO> findProductsWithPagination(int offset, int pageSize) {
        Page<Product> productPage = productRepository.findAll(PageRequest.of(offset, pageSize));
        /**
         * Retrieves a page of products with pagination.
         *
         * @param offset   the offset of the page
         * @param pageSize the size of the page
         * @return a page of product DTOs
         */
        return productPage.map(this::convertProductToDTO);
    }

    /**
     * Finds products with pagination and sorting.
     *
     * @param offset   the offset of the page
     * @param pageSize the size of the page
     * @param field    the field to sort by
     * @return a page of products with pagination and sorting
     */
    public Page<ProductDTO> findProductsWithPaginationAndSorting(int offset, int pageSize, String field) {
        Page<Product> productPage = productRepository
                .findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
        return productPage.map(this::convertProductToDTO);
    }

    /**
     * Saves a product to the database.
     *
     * @param productDTO The product to be saved
     * @return The saved product
     */
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = convertProductToEntity(productDTO);
        return convertProductToDTO(productRepository.save(product));
    }

    /**
     * Converts a Product entity to a ProductDTO object.
     *
     * @param product the Product entity to be converted
     * @return the converted ProductDTO object
     */
    private ProductDTO convertProductToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setIdProduct(product.getIdProduct());
        productDTO.setName(product.getName());
        productDTO.setTotalQuantity(product.getTotalQuantity());
        productDTO.setPrice(product.getPrice());
        return productDTO;
    }

    /**
     * Converts a ProductDTO object to a Product entity.
     *
     * @param productDTO the ProductDTO object to be converted
     * @return the converted Product entity
     */
    private Product convertProductToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setIdProduct(productDTO.getIdProduct());
        product.setName(productDTO.getName());
        product.setTotalQuantity(productDTO.getTotalQuantity());
        product.setPrice(productDTO.getPrice());
        return product;
    }
}
