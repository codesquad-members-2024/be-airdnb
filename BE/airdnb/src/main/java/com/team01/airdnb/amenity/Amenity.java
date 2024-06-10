package com.team01.airdnb.amenity;

import com.team01.airdnb.accommadation.Accommodation;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "amenities")
public class Amenity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Enumerated(EnumType.STRING)
  private AmenityStatus tv;
  @Enumerated(EnumType.STRING)
  private AmenityStatus kitchen;
  private Integer beds;
  private Integer bathrooms;
  @Enumerated(EnumType.STRING)
  private AmenityStatus wirelessInternet;
  @Enumerated(EnumType.STRING)
  private AmenityStatus washingMachine;
  @Enumerated(EnumType.STRING)
  private AmenityStatus freeParking;
  @Enumerated(EnumType.STRING)
  private AmenityStatus paidParking;
  @Enumerated(EnumType.STRING)
  private AmenityStatus airConditioner;
  @Enumerated(EnumType.STRING)
  private AmenityStatus dedicatedWorkspace;

  @OneToOne
  @JoinColumn(name = "accommodation_id")
  private Accommodation accommodation;
}
