package team8.airbnb.offereditems;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OfferedItemRequest {
  private Long id;
  private String itemName;
}
