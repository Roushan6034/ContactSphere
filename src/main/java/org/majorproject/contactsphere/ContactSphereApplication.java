package org.majorproject.contactsphere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.Socket;

@SpringBootApplication

public class ContactSphereApplication {


    public static void main(String[] args) {
        System.setProperty("java.net.useSystemProxies", "false");


        SpringApplication.run(ContactSphereApplication.class, args);
    }

}
