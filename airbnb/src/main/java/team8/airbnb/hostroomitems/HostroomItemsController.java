package team8.airbnb.hostroomitems;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team8.airbnb.exception.ErrorResponse;
import team8.airbnb.exception.HostroomItemNotFoundException;
import team8.airbnb.offereditems.OfferedItemRequest;

@RestController
@RequestMapping("/api/hostroom-items")
public class HostroomItemsController {

  @Autowired
  private HostroomItemsService hostroomItemsService;

  @PostMapping("/{hostroomId}/{itemId}")
  @ResponseStatus(HttpStatus.CREATED)
  public HostroomItems createHostroomItem(@PathVariable Long hostroomId, @PathVariable Long itemId) {
    return hostroomItemsService.createHostroomItem(hostroomId, itemId);
  }

  @PutMapping("/{hostroomId}/{itemId}")
  public HostroomItems updateHostroomItem(
      @PathVariable Long hostroomId,
      @PathVariable Long itemId,
      @RequestBody HostroomItems hostroomItemsDetails) {
    HostroomItemsId id = new HostroomItemsId(hostroomId, itemId);
    return hostroomItemsService.updateHostroomItem(id, hostroomItemsDetails);
  }

  @DeleteMapping("/{hostroomId}/{itemId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteHostroomItem(@PathVariable Long hostroomId, @PathVariable Long itemId) {
    HostroomItemsId id = new HostroomItemsId(hostroomId, itemId);
    hostroomItemsService.deleteHostroomItem(id);
  }

  @GetMapping("/{hostroomId}/{itemId}")
  public HostroomItems getHostroomItemById(@PathVariable Long hostroomId, @PathVariable Long itemId) {
    HostroomItemsId id = new HostroomItemsId(hostroomId, itemId);
    return hostroomItemsService.getHostroomItemById(id);
  }

  @GetMapping
  public List<HostroomItems> getAllHostroomItems() {
    return hostroomItemsService.getAllHostroomItems();
  }

  @GetMapping("/{hostroomId}")
  public List<OfferedItemRequest> getOfferedItemsByHostroomId(@PathVariable Long hostroomId) {
    return hostroomItemsService.getOfferedItemsByHostroomId(hostroomId);
  }

  @ExceptionHandler(HostroomItemNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponse handleHostroomItemNotFoundException(HostroomItemNotFoundException ex) {
    return new ErrorResponse(ex.getMessage());
  }
}
