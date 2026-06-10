package ra.demo.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.demo.common.StatusType;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryRequest {
    @NotBlank(message = "Không được để trống tên danh mục")
    private String name;
    private String description;
    @NotNull(message = "Không được để trống status")
    private StatusType status;
}
