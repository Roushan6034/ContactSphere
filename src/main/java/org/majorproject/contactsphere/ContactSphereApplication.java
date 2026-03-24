package org.majorproject.contactsphere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class ContactSphereApplication {

    public static void main(String[] args) {
//        System.out.println("DB_PASSWORD: " + System.getenv("DB_PASSWORD"));
        SpringApplication.run(ContactSphereApplication.class, args);
    }

}
