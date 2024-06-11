package com.airdnb.chat.service;

import com.airdnb.chat.dto.ChatRoomCreation;
import com.airdnb.chat.entity.ChatRoom;
import com.airdnb.chat.repository.ChatRoomRepository;
import com.airdnb.member.MemberService;
import com.airdnb.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberService memberService;

    public ChatRoom findChatRoomById(Long id) {
        return chatRoomRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));
    }

    public Long createChatRoom(ChatRoomCreation chatRoomCreation) {
        Member host = memberService.findMemberById(chatRoomCreation.getHostId());
        Member guest = memberService.findMemberById(chatRoomCreation.getGuestId());

        ChatRoom chatRoom = ChatRoom.builder()
            .host(host)
            .guest(guest)
            .build();

        chatRoomRepository.save(chatRoom);
        return chatRoom.getId();
    }

    public void softDeleteChatRoom(Long chatRoomId) {
        ChatRoom chatRoom = findChatRoomById(chatRoomId);
        chatRoom.softDelete();
    }

}
