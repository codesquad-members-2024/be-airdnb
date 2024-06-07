package com.team01.airdnb.accommadation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Amenity {

  @Id
  @GeneratedValue
  private Long id;
  @OneToOne
  @JoinColumn(name = "accommodation_id")
  private Accommodation accommodation;
  private Boolean wirelessInternet;
  private Boolean tv;
  private Boolean kitchen;
  private Boolean washingMachine;
  private Boolean freeParking;
  private Boolean paidParking;
  private Boolean airConditioning;
  private Boolean dedicatedWorkspace;
  private Integer bedCount;
  private Integer bathroomCount;
}
