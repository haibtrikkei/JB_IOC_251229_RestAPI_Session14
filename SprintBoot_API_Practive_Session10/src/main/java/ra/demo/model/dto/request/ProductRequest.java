package ra.demo.model.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.demo.common.StatusType;
import ra.demo.model.entity.Category;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductRequest {
    @NotBlank(message = "Không được để trống mã sản phẩm")
    private String code;
    @NotBlank(message = "Không được để trống tên sản phẩm")
    private String name;
    @NotNull(message = "Không được để trống giá sản phẩm")
    @Min(value = 1, message = "Giá sản phẩm phải >0")
    private Double price;
    @NotNull(message = "Không được để trống số lượng sản phẩm")
    @Min(value = 0, message = "Số lượng sản phẩm phải >=0")
    private Integer quantity;
    @NotNull(message = "Không được để trống trạng thái sản phẩm")
    private StatusType status;
    @NotNull(message = "Không được để trống danh mục sản phẩm")
    private Category category;
}
