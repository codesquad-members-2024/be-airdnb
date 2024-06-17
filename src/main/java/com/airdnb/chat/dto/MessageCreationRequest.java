package com.airdnb.chat.dto;

import com.airdnb.chat.entity.MessageType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MessageCreationRequest {

    @NotNull
    String memberId;
    String content;
    @NotNull
    Long roomId;
    @NotNull
    MessageType messageType;


    public MessageCreation toMessageCreation() {
        return MessageCreation.builder()
            .memberId(getMemberId())
            .content(getContent())
            .roomId(getRoomId())
            .messageType(getMessageType())
            .build();
    }
}
