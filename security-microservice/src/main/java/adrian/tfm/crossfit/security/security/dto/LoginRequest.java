package adrian.tfm.crossfit.security.security.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
	@NotBlank
  	private String username;

	@NotBlank
	private String password;

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
