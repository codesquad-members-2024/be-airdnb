package team8.airbnb.hostroomtags;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team8.airbnb.tag.TagsRequest;

@RestController
public class HostroomTagController {

  private HostroomTagService hostroomTagService;

  @Autowired
  public HostroomTagController(HostroomTagService hostroomTagService) {
    this.hostroomTagService = hostroomTagService;
  }

//  @PostMapping("/addTag/{hostroomId}")
//  public void addSingleTag(@PathVariable Long hostroomId , @RequestBody TagRequest tagRequest) {
//    hostroomTagService.addSingleTag(hostroomId, tagRequest.tagName());
//  }

  @PostMapping("/addTag/{hostroomId}")
  public void addTags(@PathVariable Long hostroomId , @RequestBody TagsRequest tagsRequest) {
    hostroomTagService.addTags(hostroomId, tagsRequest.tagsName());
  }
}
