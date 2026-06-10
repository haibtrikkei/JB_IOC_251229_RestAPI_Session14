package ra.demo.model.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
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
public class ProductResonse {
    private Long id;
    private String code;
    private String name;
    private Double price;
    private Integer quantity;
    private StatusType status;
    private Category category;
}
