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
  @Builder.Default
  private Integer beds = 1;
  @Builder.Default
  private Integer bathrooms = 0;

  @Enumerated(EnumType.STRING)
  private AmenityStatus kitchen = AmenityStatus.NOT_AVAILABLE;
  @Builder.Default
  @Enumerated(EnumType.STRING)
  private AmenityStatus dedicatedWorkspace = AmenityStatus.NOT_AVAILABLE;
  @Builder.Default
  @Enumerated(EnumType.STRING)
  private AmenityStatus tv = AmenityStatus.NOT_AVAILABLE;
  @Builder.Default
  @Enumerated(EnumType.STRING)
  private AmenityStatus washingMachine = AmenityStatus.NOT_AVAILABLE;
  @Builder.Default
  @Enumerated(EnumType.STRING)
  private AmenityStatus airConditioner = AmenityStatus.NOT_AVAILABLE;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  private AmenityStatus wirelessInternet = AmenityStatus.NOT_AVAILABLE;
  @Builder.Default
  @Enumerated(EnumType.STRING)
  private AmenityStatus freeParking = AmenityStatus.NOT_AVAILABLE;
  @Builder.Default
  @Enumerated(EnumType.STRING)
  private AmenityStatus paidParking = AmenityStatus.NOT_AVAILABLE;

  @Setter
  @OneToOne
  @JoinColumn(name = "accommodation_id")
  private Accommodation accommodation;
}
