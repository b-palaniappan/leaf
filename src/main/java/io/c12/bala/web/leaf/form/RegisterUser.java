package io.c12.bala.web.leaf.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.*;

@Data
@ToString
@EqualsAndHashCode
public class RegisterUser {

    @NotNull(message = "First Name is required")
    @NotEmpty(message = "First Name is required")
    @Size(min = 2, message = "First Name should be minimum 2 character")
    private String firstName;

    @NotNull(message = "Last Name is required")
    @NotEmpty(message = "Last Name is required")
    private String lastName;

    @NotNull(message = "Email is required")
    @NotEmpty(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;

    @NotNull(message = "Password is required")
    @NotEmpty(message = "Password is required")
    @Size(min = 8, max = 255, message = "Password should be minimum 8 characters and maximum of 255 characters")
    private String password;

    @NotNull(message = "Confirm Password is required")
    @NotEmpty(message = "Confirm Password is required")
    private String confirmPassword;

    // to validate password and confirm password are same.
    @AssertTrue(message = "Confirm Password should match Password")
    private boolean isConfirmPassword() {
        return this.password.equals(this.confirmPassword);
    }
}
