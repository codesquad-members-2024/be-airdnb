package com.airdnb.chat.dto;

import com.airdnb.chat.entity.MessageType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MessageCreation {

    String memberId;
    String content;
    Long roomId;
    MessageType messageType;

    public static MessageCreation from(MessageCreationRequest messageCreationRequest) {
        return MessageCreation.builder()
            .memberId(messageCreationRequest.getMemberId())
            .content(messageCreationRequest.getContent())
            .roomId(messageCreationRequest.getRoomId())
            .messageType(messageCreationRequest.getMessageType())
            .build();
    }

}
