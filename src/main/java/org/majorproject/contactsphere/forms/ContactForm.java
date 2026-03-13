package org.majorproject.contactsphere.forms;

import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ContactForm {

    @Id
    private String id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^[+]?[(]?[0-9]{1,4}[)]?[-\\s./0-9]{7,14}$",
            message = "Please provide a valid phone number"
    )
    private String phoneNumber;

    @Size(max = 200, message = "Address must not exceed 200 characters")
    private String address;

//    @Pattern(
//            regexp = "^(https?://.*\\.(?:png|jpg|jpeg|gif|webp|svg))?$",
//            message = "Picture must be a valid image URL"
//    )
    private MultipartFile picture;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @Pattern(
            regexp = "^(true|false)?$",
            message = "Favorite must be 'true' or 'false'"
    )
    private String favorite;

    @Pattern(
            regexp = "^(https?://(www\\.)?linkedin\\.com/.*)?$",
            message = "Please provide a valid LinkedIn URL"
    )
    private String linkedinLink;

    @Pattern(
            regexp = "^(https?://(www\\.)?instagram\\.com/.*)?$",
            message = "Please provide a valid Instagram URL"
    )
    private String instagramLink;
}