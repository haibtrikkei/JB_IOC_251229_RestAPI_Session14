package ra.demo.service;

import ra.demo.model.dto.request.CategoryRequest;
import ra.demo.model.dto.response.CategoryResponse;
import ra.demo.model.entity.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getCategories(String status);
    CategoryResponse insertCategory(CategoryRequest categoryRequest);
    CategoryResponse getCategoryById(Long id);
    CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest);
    CategoryResponse deleteCategory(Long id);
}
