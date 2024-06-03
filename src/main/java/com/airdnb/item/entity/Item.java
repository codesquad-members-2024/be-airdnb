package com.airdnb.item.entity;

import com.airdnb.global.status.Status;
import com.airdnb.img.entity.Image;
import com.airdnb.member.entity.Member;
import com.airdnb.tag.entity.Tag;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public abstract class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int price;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private Status status;
    @ManyToOne
    @JoinColumn(name = "host")
    private Member host;
    @ManyToOne
    @JoinColumn(name = "image")
    private Image image;
    @Enumerated(EnumType.STRING)
    private ItemType type;
    @OneToMany(mappedBy = "item")
    private Set<Tag> tags;
    @OneToMany(mappedBy = "item")
    private Set<ItemComment> itemComments;

    public enum ItemType {
        STAY, EXPERIENCE, ONLINE_EXP
    }

}
