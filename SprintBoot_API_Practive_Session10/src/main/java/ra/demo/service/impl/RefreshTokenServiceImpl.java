package ra.demo.service.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.AnyDiscriminatorValues;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ra.demo.model.entity.RefreshToken;
import ra.demo.repository.RefreshTokenRepository;
import ra.demo.service.RefreshTokenService;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt-refresh-expired}")
    private Long refreshTokenExpired;

    @Override
    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = RefreshToken.builder()
                .username(username)
                .refreshToken(UUID.randomUUID().toString())
                .expired(Instant.now().plusMillis(refreshTokenExpired))
                .revoked(false)
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if(token.getExpired().compareTo(Instant.now())<0){
            token.setRevoked(true);
            refreshTokenRepository.save(token);
            throw new RuntimeException("Refresh token đã hết hạn");
        }
        if(token.getRevoked()){
            throw new RuntimeException("Refresh token đã bị thu hồi");
        }
        return token;
    }
}
