package adrian.tfm.crossfit.security.controllers;

import adrian.tfm.crossfit.security.payload.request.LogoutRequest;
import adrian.tfm.crossfit.security.service.AuthService;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
  private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
  @Autowired
  AuthService authService;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    logger.info("### authenticateUser ###");
    try {
      JwtResponse jwtResponse = this.authService.authenticateUser(loginRequest);
      return ResponseEntity.ok(jwtResponse);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout(@RequestBody LogoutRequest logoutRequest) {
    logger.info("### logout ###");
      try {
        this.authService.logout(logoutRequest);
        return ResponseEntity.ok("Logout successful");
      } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
      }
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    logger.info("### registerUser ###");
    try {
      MessageResponse messageResponse = this.authService.registerUser(signUpRequest);
      return ResponseEntity.ok(messageResponse);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }
}
