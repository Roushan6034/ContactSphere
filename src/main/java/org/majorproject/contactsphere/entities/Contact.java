package org.majorproject.contactsphere.entities;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contact_info")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Contact {
    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String picture;
    @Column(length = 1000)
    private String description;
    private String favorite;
    private String linkedinLink;
    private String instagramLink;
    @Column(length = 1000)
    private String websiteLink;
    @ManyToOne
    @JsonIgnore
    private User user;
    @OneToMany(mappedBy = "contact",cascade = CascadeType.ALL , fetch = FetchType.LAZY, orphanRemoval = true)
    private List<SocialLink> socialLinks=new ArrayList<>();
}
