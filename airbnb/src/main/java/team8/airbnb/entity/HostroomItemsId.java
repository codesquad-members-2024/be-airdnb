package team8.airbnb.entity;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class HostroomItemsId implements Serializable {

  private Long hostroomId;
  private Long itemId;

  public HostroomItemsId(Long hostroomId, Long itemId) {
    this.hostroomId = hostroomId;
    this.itemId = itemId;
  }
}
