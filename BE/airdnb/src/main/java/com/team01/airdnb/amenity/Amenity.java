package com.team01.airdnb.amenity;

import com.team01.airdnb.accommadation.Accommodation;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "amenities")
public class Amenity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Boolean tv;
  private Boolean kitchen;
  private Integer beds;
  private Integer bathrooms;
  private Boolean wirelessInternet;
  private Boolean washingMachine;
  private Boolean freeParking;
  private Boolean paidParking;
  private Boolean airConditioner;
  private Boolean dedicatedWorkspace;

  @OneToOne
  @JoinColumn(name = "accommodation_id")
  private Accommodation accommodation;
}
