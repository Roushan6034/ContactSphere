package org.majorproject.contactsphere.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserForm {
    @NotBlank
    @Size(min = 4,message = "Min 4 characters name is required")
    private String name;
    @Email(message = "Invalid Email Address")
    private String email;
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
//    @Pattern(
//            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
//            message = "Password must contain uppercase, lowercase, number and special character"
//    )
    private String password;
    @NotBlank(message = "About Required")
    private String about;
    @Size(min=10, max = 13, message = "Phone Number should be between 10-13 character")
    private String phoneNumber;

}
