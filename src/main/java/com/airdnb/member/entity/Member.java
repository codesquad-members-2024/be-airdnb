package com.airdnb.member.entity;

import com.airdnb.image.entity.Image;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    private String id;
    @Setter
    private String password;

    private String name;

    private String role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "img_id")
    private Image image;
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Builder
    public Member(String id, String password, String name, String role) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.role = role;
        this.status = MemberStatus.ACTIVE;
    }

    public boolean hasSameId(String memberId) {
        return id.equals(memberId);
    }

    public enum MemberStatus {
        ACTIVE, DELETED
    }
}
