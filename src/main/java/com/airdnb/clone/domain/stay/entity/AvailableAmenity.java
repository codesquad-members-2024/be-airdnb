package com.airdnb.clone.domain.stay.entity;

import jakarta.persistence.CascadeType;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "AVAILABLE_AMENITY")
@Entity
public class AvailableAmenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AVAILABLE_ID")
    private Long id;

    @ManyToOne(fetch =  FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "STAY_ID", foreignKey = @ForeignKey(name = "FK_STAY_AVAILABLE_ID"))
    private Stay stay;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.�ALL)
    @JoinColumn(name = "AMENITY_ID", foreignKey = @ForeignKey(name = "FK_AMENITY_AVAILABLE_ID"))
    private Amenity amenity;

    public void setStay(Stay stay) {
        if (this.stay != null) {
            this.stay.getAmenities().remove(this);
        }
        stay.getAmenities().add(this);
        this.stay = stay;
    }
}
