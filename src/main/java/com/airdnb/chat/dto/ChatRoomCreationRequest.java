package com.airdnb.chat.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class ChatRoomCreationRequest {

    @NotNull
    String senderId;
    @NotNull
    String receiverId;
    @NotNull
    String role;
}
