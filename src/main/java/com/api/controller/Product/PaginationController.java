package com.api.controller.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.api.dto.PageResponseDTO;
import com.api.dto.ProductDTO;
import com.api.service.ProductService;

@RestController
@RequestMapping("/api/v1/paginated-products")
public class PaginationController {

    @Autowired
    private ProductService productService;

    /**
     * Retrieves all products from the database.
     *
     * @return A list of ProductDTO objects wrapped in a ResponseEntity with a 200 OK status code.
     */
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.findAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * Retrieves products from the database sorted by a specified field.
     *
     * @param field the field to sort the products by
     * @return a PageResponseDTO containing a list of sorted ProductDTO objects
     */
    @GetMapping("/{field}")
    public PageResponseDTO<List<ProductDTO>> getProductsWithSort(@PathVariable String field) {
        List<ProductDTO> allProducts = productService.findProductsWithSorting(field);
        return new PageResponseDTO<>(allProducts.size(), allProducts);
    }

    /**
     * Retrieves a page of products from the database with pagination.
     *
     * @param offset   the offset of the page
     * @param pageSize the size of the page
     * @return a PageResponseDTO containing a page of ProductDTO objects
     */
    @GetMapping("/pagination/{offset}/{pageSize}")
    public PageResponseDTO<Page<ProductDTO>> getProductsWithPagination(@PathVariable int offset,
            @PathVariable int pageSize) {
        Page<ProductDTO> productsWithPagination = productService.findProductsWithPagination(offset, pageSize);
        return new PageResponseDTO<>(productsWithPagination.getNumberOfElements(), productsWithPagination);
    }

    /**
     * Retrieves a page of products from the database with pagination and sorting.
     *
     * @param offset   the offset of the page
     * @param pageSize the size of the page
     * @param field    the field to sort the products by
     * @return a PageResponseDTO containing a page of ProductDTO objects
     */
    @GetMapping("/paginationAndSort/{offset}/{pageSize}/{field}")
    public PageResponseDTO<Page<ProductDTO>> getProductsWithPaginationAndSort(@PathVariable int offset,
            @PathVariable int pageSize, @PathVariable String field) {
        Page<ProductDTO> productsWithPagination = productService.findProductsWithPaginationAndSorting(offset, pageSize,
                field);
        return new PageResponseDTO<>(productsWithPagination.getSize(), productsWithPagination);
    }
}
