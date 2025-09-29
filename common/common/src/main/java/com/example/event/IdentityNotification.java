package com.example.event;

import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

// This class is used to represent notifications related to identity events, when user is created success.
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class IdentityNotification {
    String channel;
    String receiver;
    String templateCode;
    Map<String, Object> params;
    String subject;
    String body;

}
