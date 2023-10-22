package adrian.tfm.crossfit.security.service;

import adrian.tfm.crossfit.security.payload.request.LoginRequest;
import adrian.tfm.crossfit.security.payload.request.LogoutRequest;
import adrian.tfm.crossfit.security.payload.request.SignupRequest;
import adrian.tfm.crossfit.security.payload.response.JwtResponse;
import adrian.tfm.crossfit.security.payload.response.MessageResponse;

public interface AuthService {
    JwtResponse authenticateUser(LoginRequest loginRequest);

    void logout(LogoutRequest logoutRequest);

    MessageResponse registerUser(SignupRequest signUpRequest) throws Exception;
}
