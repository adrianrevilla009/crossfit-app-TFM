package adrian.tfm.crossfit.security.controllers;

import adrian.tfm.crossfit.security.payload.request.LogoutRequest;
import adrian.tfm.crossfit.security.service.AuthService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import adrian.tfm.crossfit.security.payload.request.LoginRequest;
import adrian.tfm.crossfit.security.payload.request.SignupRequest;
import adrian.tfm.crossfit.security.payload.response.JwtResponse;
import adrian.tfm.crossfit.security.payload.response.MessageResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthService authService;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    try {
      JwtResponse jwtResponse = this.authService.authenticateUser(loginRequest);
      return ResponseEntity.ok(jwtResponse);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout(@RequestBody LogoutRequest logoutRequest) {
      try {
        this.authService.logout(logoutRequest);
        return ResponseEntity.ok("Logout successful");
      } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
      }
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    try {
      MessageResponse messageResponse = this.authService.registerUser(signUpRequest);
      return ResponseEntity.ok(messageResponse);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
