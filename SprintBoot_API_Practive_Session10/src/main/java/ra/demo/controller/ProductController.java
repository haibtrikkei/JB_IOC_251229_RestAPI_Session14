package ra.demo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.demo.model.dto.request.ProductRequest;
import ra.demo.model.dto.response.ApiDataResponse;
import ra.demo.model.dto.response.ProductResonse;
import ra.demo.repository.ProductRepository;
import ra.demo.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiDataResponse<ProductResonse>> insertProduct(@Valid @RequestBody ProductRequest productRequest) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Thêm mới sản phẩm thành công",
                productService.insertProduct(productRequest),
                null,
                HttpStatus.CREATED
        ), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<ProductResonse>>> getProducts(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                                                             @RequestParam(name = "cateId", defaultValue = "2") Long cateId,
                                                                             @RequestParam(name = "status", defaultValue = "") String status) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy danh sách sản phẩm thành công",
                productService.getProducts(keyword, cateId, status),
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<ProductResonse>> getProductById(@PathVariable Long id) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy thông tin sản phẩm " + id + " thành công",
                productService.getProductById(id),
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }
}
