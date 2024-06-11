package com.airdnb.chat.repository;

import com.airdnb.chat.entity.ChatRoom;
import com.airdnb.chat.entity.ChatStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByIdAndStatus(Long id, ChatStatus status);
}
