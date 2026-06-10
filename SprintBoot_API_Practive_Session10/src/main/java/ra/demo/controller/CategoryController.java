package ra.demo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.demo.model.dto.request.CategoryRequest;
import ra.demo.model.dto.response.ApiDataResponse;
import ra.demo.model.dto.response.CategoryResponse;
import ra.demo.service.CategoryService;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<CategoryResponse>>> getCategories(@RequestParam(name = "status", defaultValue = "ACTIVE") String status) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy danh sách danh mục thành công",
                categoryService.getCategories(status),
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<CategoryResponse>> insertCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Thêm mới danh mục thành công",
                categoryService.insertCategory(categoryRequest),
                null,
                HttpStatus.CREATED
        ), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<CategoryResponse>> getCategoryById(@PathVariable Long id) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy thông tin danh mục " + id + " thành công",
                categoryService.getCategoryById(id),
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<CategoryResponse>> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequest categoryRequest) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Cập nhật danh mục "+id+" thành công",
                categoryService.updateCategory(id, categoryRequest),
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<CategoryResponse>> deleteCategory(@PathVariable Long id) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Xóa thông tin danh mục " + id + " thành công",
                categoryService.deleteCategory(id),
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }
}
