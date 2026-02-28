package org.majorproject.contactsphere.helpers;

import lombok.*;

import java.awt.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    private String message;
    private MessageType messageType=MessageType.blue;
}
