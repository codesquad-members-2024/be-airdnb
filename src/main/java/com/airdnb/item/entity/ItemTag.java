package com.airdnb.item.entity;

import com.airdnb.item.entity.ItemTag.ItemTagId;
import com.airdnb.tag.entity.Tag;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@IdClass(ItemTagId.class)
@NoArgsConstructor
@Getter
@Setter
public class ItemTag {
    @Id
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Id
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public class ItemTagId implements Serializable {
        private Long itemId;
        private Long tagId;
    }
}

