package com.airdnb.chat.service;

import com.airdnb.chat.dto.MessageCreation;
import com.airdnb.chat.entity.ChatMessage;
import com.airdnb.chat.entity.ChatRoom;
import com.airdnb.chat.entity.MessageType;
import com.airdnb.chat.repository.ChatMessageRepository;
import com.airdnb.member.MemberService;
import com.airdnb.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

    private final MemberService memberService;
    private final MemberChatRoomService memberChatRoomService;
    private final ChatRoomService chatRoomService;
    private final ChatMessageRepository chatMessageRepository;


    public void createChat(MessageCreation messageCreation) {
        Member member = memberService.findMemberById(messageCreation.getMemberId());
        ChatRoom chatRoom = chatRoomService.findChatRoomById(messageCreation.getRoomId());
        boolean isMemberInChatRoom = memberChatRoomService.existsByMemberId(messageCreation.getMemberId());
        String message = "";

        if (messageCreation.getMessageType().equals(MessageType.ENTER)) {
            if (!isMemberInChatRoom) {
                log.info("채팅방 입장");
                memberChatRoomService.createMemberChatRoom(member, chatRoom);
                message = member.getName() + "가 입장했습니다.";
            }
        }

        if (messageCreation.getMessageType().equals(MessageType.LEAVE)) {
            log.info("채팅방 퇴장");
            chatRoomService.softDeleteChatRoom(chatRoom.getId());
            message = member.getName() + "가 퇴장했습니다.";
        }

        if (messageCreation.getMessageType().equals(MessageType.CHAT)) {
            log.info("채팅 메시지 전송");
            message = messageCreation.getContent();
        }

        ChatMessage chatMessage = ChatMessage.builder()
            .userName(member.getName())
            .content(message)
            .chatRoom(chatRoom)
            .build();

        chatMessageRepository.save(chatMessage);

    }

}
