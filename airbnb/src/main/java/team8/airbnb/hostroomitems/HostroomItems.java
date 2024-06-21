package team8.airbnb.hostroomitems;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import team8.airbnb.hostroom.Hostroom;
import team8.airbnb.offereditems.OfferedItem;

@Entity
@Getter
@Setter
@Table(name = "hostroomItems")
@IdClass(HostroomItemsId.class)
public class HostroomItems {

  @Id
  @Column(name = "hostroom_id")
  private Long hostroom_id;

  @Id
  @Column(name = "item_id")
  private Long item_id;

  @ManyToOne
  @MapsId("hostroom_id")
  @JoinColumn(name = "hostroom_id")
  @JsonBackReference
  private Hostroom hostroom;

  @ManyToOne
  @MapsId("item_id")
  @JoinColumn(name = "item_id")
  private OfferedItem offeredItem;
}
