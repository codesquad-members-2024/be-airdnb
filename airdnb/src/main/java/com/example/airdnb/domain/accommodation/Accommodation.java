package com.example.airdnb.domain.accommodation;

import com.example.airdnb.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Address address;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long pricePerNight;

    @Column(nullable = false)
    private Integer maxGuests;

    @CreatedDate
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "accommodation")
    private List<Image> images = new ArrayList<>();

    @Builder
    public Accommodation(Address address, User user, String name, String description,
        Long pricePerNight, Integer maxGuests, List<Image> images) {
        this.address = address;
        this.user = user;
        this.name = name;
        this.description = description;
        this.pricePerNight = pricePerNight;
        this.maxGuests = maxGuests;
        for (Image image : images) {
            addImage(image);
        }
    }

    public void addImage(Image image) {
        image.setAccommodation(this);
        this.images.add(image);
    }

    public void setUser(User user) {
        this.user = user;
    }
}
