package team8.airbnb.offereditems;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferedItemService {

  @Autowired
  private OfferedItemRepository offeredItemRepository;

  public OfferedItem createOfferedItem(OfferedItem offeredItem) {
    return offeredItemRepository.save(offeredItem);
  }

  public List<OfferedItem> getAllOfferedItems() {
    return offeredItemRepository.findAll();
  }

  public OfferedItem getOfferedItemById(Long id) {
    return offeredItemRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("OfferedItem not found with id: " + id));
  }

  public OfferedItem updateOfferedItem(Long id, OfferedItem offeredItem) {
    offeredItem.setId(id);
    return offeredItemRepository.save(offeredItem);
  }

  public void deleteOfferedItem(Long id) {
    offeredItemRepository.deleteById(id);
  }
}

