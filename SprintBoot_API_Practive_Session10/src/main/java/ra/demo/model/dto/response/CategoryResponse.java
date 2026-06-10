package ra.demo.model.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.demo.common.StatusType;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
    private StatusType status;
}
