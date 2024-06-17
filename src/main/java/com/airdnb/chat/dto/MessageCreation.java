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


}
