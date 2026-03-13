package org.majorproject.contactsphere.Services;

import org.majorproject.contactsphere.entities.Contact;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String uploadImage(MultipartFile file);

    String getUrlFromPublicId(String publicId);
}
