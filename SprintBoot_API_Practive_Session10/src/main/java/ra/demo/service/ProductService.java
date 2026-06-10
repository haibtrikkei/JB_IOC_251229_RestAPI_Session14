package ra.demo.service;

import ra.demo.model.dto.request.ProductRequest;
import ra.demo.model.dto.response.ProductResonse;

import java.util.List;

public interface ProductService {
    ProductResonse insertProduct(ProductRequest productRequest);
    List<ProductResonse> getProducts(String keyword, Long cateId, String status);
    ProductResonse getProductById(Long id);
    ProductResonse updateProduct(Long id, ProductRequest productRequest);
    ProductResonse deleteProduct(Long id);
}
