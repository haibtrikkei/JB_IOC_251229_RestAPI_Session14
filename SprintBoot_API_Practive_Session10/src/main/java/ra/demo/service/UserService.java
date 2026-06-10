package ra.demo.service;

import ra.demo.model.dto.request.RefreshTokenRequest;
import ra.demo.model.dto.request.UserLogin;
import ra.demo.model.dto.request.UserRegister;
import ra.demo.model.dto.response.JWTResponse;
import ra.demo.model.entity.Users;

import java.util.List;

public interface UserService {
    Users registerUser(UserRegister userRegister);
    JWTResponse login(UserLogin userLogin);
    List<Users> getAllUsers();
    JWTResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
