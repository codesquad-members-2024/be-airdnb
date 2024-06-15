package com.airdnb.chat.dto;

import com.airdnb.chat.entity.MessageType;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class MessageCreationRequest {

    @NotNull
    String memberId;
    String content;
    @NotNull
    Long roomId;
    @NotNull
    MessageType messageType;

}
