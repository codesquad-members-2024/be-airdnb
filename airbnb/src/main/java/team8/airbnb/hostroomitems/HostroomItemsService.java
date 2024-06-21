package team8.airbnb.hostroomitems;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team8.airbnb.exception.HostroomItemNotFoundException;
import team8.airbnb.hostroom.Hostroom;
import team8.airbnb.hostroom.HostroomRepository;
import team8.airbnb.offereditems.OfferedItem;
import team8.airbnb.offereditems.OfferedItemRequest;
import team8.airbnb.offereditems.OfferedItemRepository;

@Service
public class HostroomItemsService {

  @Autowired
  private HostroomItemsRepository hostroomItemsRepository;

  @Autowired
  private HostroomRepository hostroomRepository;

  @Autowired
  private OfferedItemRepository offeredItemRepository;

  public HostroomItems createHostroomItem(Long hostroomId, Long itemId) {
    Hostroom hostroom = hostroomRepository.findById(hostroomId)
        .orElseThrow(() -> new HostroomItemNotFoundException("Hostroom not found with id: " + hostroomId));
    OfferedItem offeredItem = offeredItemRepository.findById(itemId)
        .orElseThrow(() -> new HostroomItemNotFoundException("OfferedItem not found with id: " + itemId));

    HostroomItems hostroomItems = new HostroomItems();
    hostroomItems.setHostroom(hostroom);
    hostroomItems.setOfferedItem(offeredItem);
    hostroomItems.setHostroom_id(hostroomId);
    hostroomItems.setItem_id(itemId);

    return hostroomItemsRepository.save(hostroomItems);
  }

  public HostroomItems updateHostroomItem(HostroomItemsId id, HostroomItems hostroomItemsDetails) {
    HostroomItems hostroomItems = hostroomItemsRepository.findById(id)
        .orElseThrow(() -> new HostroomItemNotFoundException("HostroomItem not found with id: " + id));
    hostroomItems.setHostroom(hostroomItemsDetails.getHostroom());
    hostroomItems.setOfferedItem(hostroomItemsDetails.getOfferedItem());
    return hostroomItemsRepository.save(hostroomItems);
  }

  public void deleteHostroomItem(HostroomItemsId id) {
    if (!hostroomItemsRepository.existsById(id)) {
      throw new HostroomItemNotFoundException("HostroomItem not found with id: " + id);
    }
    hostroomItemsRepository.deleteById(id);
  }

  public HostroomItems getHostroomItemById(HostroomItemsId id) {
    return hostroomItemsRepository.findById(id)
        .orElseThrow(() -> new HostroomItemNotFoundException("HostroomItem not found with id: " + id));
  }

  public List<HostroomItems> getAllHostroomItems() {
    return hostroomItemsRepository.findAll();
  }

  public List<HostroomItems> getHostroomItemsByHostroomId(Long hostroomId) {
    return hostroomItemsRepository.findAll().stream()
        .filter(item -> item.getHostroom().getId().equals(hostroomId))
        .collect(Collectors.toList());
  }

  public List<OfferedItemRequest> getOfferedItemsByHostroomId(Long hostroomId) {
    return hostroomItemsRepository.findAll().stream()
        .filter(item -> item.getHostroom().getId().equals(hostroomId))
        .map(item -> new OfferedItemRequest(item.getOfferedItem().getId(), item.getOfferedItem().getItemName()))
        .collect(Collectors.toList());
  }
}
