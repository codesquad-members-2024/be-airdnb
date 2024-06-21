package team8.airbnb.hostroomtags;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
public class HostroomTagId implements Serializable {

  private Long hostroomId;
  private Long tagId;

  public HostroomTagId(Long hostroomId, Long tagId) {
    this.hostroomId = hostroomId;
    this.tagId = tagId;
  }

}