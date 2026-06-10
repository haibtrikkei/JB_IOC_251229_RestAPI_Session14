package ra.demo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ra.demo.common.StatusType;
import ra.demo.exception.DuplicateResourceException;
import ra.demo.exception.ResourceNotFoundException;
import ra.demo.model.dto.request.CategoryRequest;
import ra.demo.model.dto.response.CategoryResponse;
import ra.demo.model.entity.Category;
import ra.demo.repository.CategoryRepository;
import ra.demo.service.CategoryService;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ObjectMapper objectMapper;

    @Override
    public List<CategoryResponse> getCategories(String status) {
        StatusType statusType = StatusType.valueOf(status);
        if(statusType==null){
            return categoryRepository.findAll().stream().map(c -> objectMapper.convertValue(c, CategoryResponse.class)).toList();
        }else{
            return categoryRepository.findAllByStatus(statusType).stream().map(c -> objectMapper.convertValue(c, CategoryResponse.class)).toList();
        }
    }

    @Override
    public CategoryResponse insertCategory(CategoryRequest categoryRequest) {
        if(categoryRepository.existsByName(categoryRequest.getName())){
            throw new DuplicateResourceException("Tên danh mục đã tồn tại");
        }
        Category category = Category.builder()
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .status(categoryRequest.getStatus())
                .build();
        return objectMapper.convertValue(categoryRepository.save(category), CategoryResponse.class);
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Không tồn tại danh mục: " + id));
        return objectMapper.convertValue(category, CategoryResponse.class);
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        categoryRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Không tồn tại danh mục: "+id));
        Category category = Category.builder()
                .id(id)
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .status(categoryRequest.getStatus())
                .build();
        return objectMapper.convertValue(categoryRepository.save(category), CategoryResponse.class);
    }

    @Override
    public CategoryResponse deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Không tồn tại danh mục: " + id));
        category.setStatus(StatusType.INACTIVE);
        return objectMapper.convertValue(categoryRepository.save(category), CategoryResponse.class);
    }
}
