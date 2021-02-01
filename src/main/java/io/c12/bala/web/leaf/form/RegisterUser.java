package io.c12.bala.web.leaf.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import javax.validation.constraints.*;

@Log4j2
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
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=\\S+$).{8,255}$", message = "Password is not complex enough. Please click on help icon.")
    private String password;

    @NotNull(message = "Confirm Password is required")
    @NotEmpty(message = "Confirm Password is required")
    private String confirmPassword;

    @AssertTrue(message = "Agree to Terms and Conditions to continue")
    private Boolean termsAgreed;

    // added to validate password and confirm password matches or not
    private Boolean passwordMatch;

    @AssertTrue(message = "Password and Confirm password have to match")
    private boolean isPasswordMatch() {
        log.warn("Password and Confirm password not matching for user registration");
        return this.password.equals(this.confirmPassword);
    }
}
