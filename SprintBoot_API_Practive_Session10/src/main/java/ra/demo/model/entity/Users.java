package ra.demo.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "username",length = 100,nullable = false,unique = true)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "full_name",length = 70)
    private String fullName;
    @Column(name = "email",length = 100, nullable = false, unique = true)
    private String email;
    @Column(name = "phone",length = 20, nullable = false, unique = true)
    private String phone;
    private Boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;
}
