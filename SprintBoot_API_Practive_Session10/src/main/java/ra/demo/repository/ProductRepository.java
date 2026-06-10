package ra.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.demo.common.StatusType;
import ra.demo.model.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByNameContainsAndCategory_Id(String name, Long categoryId);

    List<Product> findAllByNameContainsAndCategory_IdAndStatus(String name, Long categoryId, StatusType status);

    boolean existsByCode(String code);
}
