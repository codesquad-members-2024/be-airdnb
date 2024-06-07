package com.airdnb.tag.entity;

import com.airdnb.staytag.StayTag;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "tag")
    private List<StayTag> stayTags = new ArrayList<>();

    @Builder
    public Tag(String name, List<StayTag> stayTags) {
        this.name = name;
        this.stayTags = stayTags;
    }
}
