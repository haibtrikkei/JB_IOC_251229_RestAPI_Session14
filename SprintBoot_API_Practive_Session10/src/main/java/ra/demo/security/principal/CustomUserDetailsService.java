package ra.demo.security.principal;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ra.demo.model.entity.Role;
import ra.demo.model.entity.Users;
import ra.demo.repository.UserRepository;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Không tồn tại user: " + username));

        return CustomUserDetails.builder()
                .username(users.getUsername())
                .password(users.getPassword())
                .fullName(users.getFullName())
                .email(users.getEmail())
                .phone(users.getPhone())
                .enabled(users.getEnabled())
                .authorities(mapToGrandAuthority(users.getRoles()))
                .build();
    }

    private Collection<? extends GrantedAuthority> mapToGrandAuthority(List<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRoleName())).toList();
    }
}
