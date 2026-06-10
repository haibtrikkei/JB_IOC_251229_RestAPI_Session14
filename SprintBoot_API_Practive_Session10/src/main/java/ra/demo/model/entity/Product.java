package ra.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.demo.common.StatusType;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false, unique = true)
    private String code;
    @Column(nullable = false)
    private String name;
    @Min(value = 1)
    private Double price;
    @Min(value = 0)
    private Integer quantity;
    private StatusType status;

    @ManyToOne
    @JoinColumn(name = "cate_id", referencedColumnName = "id")
    private Category category;
}
