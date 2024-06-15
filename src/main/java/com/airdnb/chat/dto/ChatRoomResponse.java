package com.airdnb.chat.dto;

import com.airdnb.chat.entity.ChatRoom;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ChatRoomResponse {

    Long id;
    String recipientName;

    public static ChatRoomResponse from(ChatRoom chatRoom, String recipientName) {
        return ChatRoomResponse.builder()
            .id(chatRoom.getId())
            .recipientName(recipientName)
            .build();
    }


}
