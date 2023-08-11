package adrian.tfm.crossfit.security.security.dto;

import lombok.Data;

import javax.validation.constraints.*;
import java.util.Set;

@Data
public class SignupRequest {
  @NotBlank
  @Size(min = 3, max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  private Set<String> role;

  @NotBlank
  @Size(min = 6, max = 40)
  private String password;

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public Set<String> getRole() {
    return role;
  }

  public String getPassword() {
    return password;
  }
}
