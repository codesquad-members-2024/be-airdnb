package com.airdnb.chat;

import com.airdnb.chat.dto.ChatRoomCreation;
import com.airdnb.chat.dto.ChatRoomCreationRequest;
import com.airdnb.chat.service.ChatRoomService;
import com.airdnb.global.ApiResponse;
import com.airdnb.global.UriMaker;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping
    public ResponseEntity<ApiResponse> createChatRoom(@Valid @RequestBody ChatRoomCreationRequest chatRoomCreationRequest) {
        Long roomId = chatRoomService.createChatRoom(ChatRoomCreation.from(chatRoomCreationRequest));
        URI location = UriMaker.makeUri(roomId);
        return ResponseEntity.created(location).body(ApiResponse.success(null));

    }

}
