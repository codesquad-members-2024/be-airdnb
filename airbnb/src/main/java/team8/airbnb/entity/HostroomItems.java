package team8.airbnb.entity;

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

@Entity
@Getter
@Setter
@Table(name = "hostroomItems")
@IdClass(HostroomItemsId.class)
public class HostroomItems {

  @Id
  @Column(name = "hostroom_id")
  private Long hostroomId;

  @Id
  @Column(name = "item_id")
  private Long itemId;

  //많은 HostroomItems가 하나의 Hostroom에 속한다
  @ManyToOne
  @MapsId("hostroomId")
  @JoinColumn(name = "hostroom_id")
  private Hostroom hostroom;

  @ManyToOne
  @MapsId("itemId")
  @JoinColumn(name = "item_id")
  private OfferedItem offeredItem;


}
