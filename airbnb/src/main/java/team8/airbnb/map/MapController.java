package team8.airbnb.map;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team8.airbnb.hostroom.HostroomService;

@Controller
@RequestMapping("/api")
public class MapController {

  private HostroomService hostroomService;

  @Autowired
  public MapController(HostroomService hostroomService) {
    this.hostroomService = hostroomService;
  }

  @GetMapping("/map")
  public String map() {
    return "map";
  }


  @GetMapping("/positions")
  @ResponseBody
  public List<MapPointResponse> getPositions() {
    return hostroomService.getAllposition();
  }
}
