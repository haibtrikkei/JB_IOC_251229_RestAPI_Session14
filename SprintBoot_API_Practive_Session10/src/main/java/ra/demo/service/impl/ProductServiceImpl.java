package ra.demo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ra.demo.common.StatusType;
import ra.demo.exception.DuplicateResourceException;
import ra.demo.exception.ResourceNotFoundException;
import ra.demo.model.dto.request.ProductRequest;
import ra.demo.model.dto.response.ProductResonse;
import ra.demo.model.entity.Product;
import ra.demo.repository.ProductRepository;
import ra.demo.service.ProductService;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    @Override
    public ProductResonse insertProduct(ProductRequest productRequest) {
        if(productRepository.existsByCode(productRequest.getCode())){
            throw new DuplicateResourceException("Code sản phẩm đã tồn tại "+productRequest.getCode());
        }
        Product product = Product.builder()
                .code(productRequest.getCode())
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .category(productRequest.getCategory())
                .quantity(productRequest.getQuantity())
                .status(productRequest.getStatus())
                .build();
        return objectMapper.convertValue(productRepository.save(product), ProductResonse.class);
    }

    @Override
    public List<ProductResonse> getProducts(String keyword, Long cateId, String status) {
        if (status.isEmpty()){
            List<Product> products = productRepository.findAllByNameContainsAndCategory_Id(keyword, cateId);
            return products.stream().map(p -> objectMapper.convertValue(p, ProductResonse.class)).toList();
        }else{
            StatusType statusType = StatusType.valueOf(status);
            List<Product> products = productRepository.findAllByNameContainsAndCategory_IdAndStatus(keyword, cateId, statusType);
            return products.stream().map(p -> objectMapper.convertValue(p, ProductResonse.class)).toList();
        }
    }

    @Override
    public ProductResonse getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm " + id));
        return objectMapper.convertValue(product, ProductResonse.class);
    }

    @Override
    public ProductResonse updateProduct(Long id, ProductRequest productRequest) {
        productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm " + id));
        Product product = Product.builder()
                .id(id)
                .code(productRequest.getCode())
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .category(productRequest.getCategory())
                .quantity(productRequest.getQuantity())
                .status(productRequest.getStatus())
                .build();
        return objectMapper.convertValue(productRepository.save(product), ProductResonse.class);
    }

    @Override
    public ProductResonse deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm " + id));
        product.setStatus(StatusType.INACTIVE);
        return objectMapper.convertValue(product, ProductResonse.class);
    }
}
