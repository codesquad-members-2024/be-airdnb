package com.airdnb.chat.entity;


import com.airdnb.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "chatroom")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "host_id")
    private Member host;
    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Member guest;
    @Enumerated(EnumType.STRING)
    ChatStatus status;

    @Builder
    public ChatRoom(Member host, Member guest) {
        this.host = host;
        this.guest = guest;
        this.status = ChatStatus.ACTIVE;
    }

    public void softDelete() {
        this.status = ChatStatus.INACTIVE;
    }
}
