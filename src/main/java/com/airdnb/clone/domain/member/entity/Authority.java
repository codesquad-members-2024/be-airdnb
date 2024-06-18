package com.airdnb.clone.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "AUTHORITY")
@Entity
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AUTHORITY_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", foreignKey = @ForeignKey(name = "FK_MEMBER_AUTH_ID"))
    private Member member;

    @Column(name = "NAME")
    private String name;
}
