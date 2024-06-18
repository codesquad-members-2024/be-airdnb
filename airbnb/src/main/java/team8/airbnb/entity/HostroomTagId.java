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
public class HostroomTagId implements Serializable {

  private Long hostroomId;
  private Long tagId;

  public HostroomTagId(Long hostroomId, Long tagId) {
    this.hostroomId = hostroomId;
    this.tagId = tagId;
  }

  /*
  복합키를 정의할 때는 equals와 hashCode 메서드를 모두 구현해야,
  JPA와 해시 기반 컬렉션에서 객체가 일관되게 작동하도록 보장할 수 있다.
   */
}
