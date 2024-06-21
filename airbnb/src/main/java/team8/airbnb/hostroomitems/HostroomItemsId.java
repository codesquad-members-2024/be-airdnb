package team8.airbnb.hostroomitems;

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

  private Long hostroom_id;
  private Long item_id;

  public HostroomItemsId(Long hostroom_id, Long item_id) {
    this.hostroom_id = hostroom_id;
    this.item_id = item_id;
  }
}