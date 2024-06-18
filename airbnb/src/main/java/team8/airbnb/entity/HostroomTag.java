package team8.airbnb.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "hostroomTag")
public class HostroomTag {

  @Id
  private Long hostroomId;

  @Id
  private Long tagId;

  @ManyToOne
  /*
  FK를 PK로 지정할 때 사용하는 어노테이션
   */
  @MapsId("hostroomId")
  private Hostroom hostroom;

  @ManyToOne
  @MapsId("tagId")
  private Tag tag;

}
