package team8.airbnb.offereditems;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/offered-items")
public class OfferedItemController {

  @Autowired
  private OfferedItemService offeredItemService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public OfferedItem createOfferedItem(@RequestBody OfferedItem offeredItem) {
    return offeredItemService.createOfferedItem(offeredItem);
  }

  @GetMapping
  public List<OfferedItem> getAllOfferedItems() {
    return offeredItemService.getAllOfferedItems();
  }

  @GetMapping("/{id}")
  public OfferedItem getOfferedItemById(@PathVariable Long id) {
    return offeredItemService.getOfferedItemById(id);
  }

  @PutMapping("/{id}")
  public OfferedItem updateOfferedItem(@PathVariable Long id,
      @RequestBody OfferedItem offeredItem) {
    return offeredItemService.updateOfferedItem(id, offeredItem);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteOfferedItem(@PathVariable Long id) {
    offeredItemService.deleteOfferedItem(id);
  }
}
