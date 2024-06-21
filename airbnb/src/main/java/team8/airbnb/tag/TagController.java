package team8.airbnb.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController {

  private TagService tagService;

  @Autowired
  public TagController(TagService tagService) {
    this.tagService = tagService;
  }

  @PostMapping("/saveTag")
  public void saveTag(@RequestBody TagRequest tagRequest) {
    tagService.saveTag(tagRequest);
  }

  @PutMapping("/update/tag/{tagId}")
  public void updateTag(@PathVariable Long tagId, @RequestBody TagRequest tagRequest) {
    tagService.updateTag(tagId, tagRequest);
  }

  @DeleteMapping("/delete/tag/{tagId}")
    public void deleteTag(@PathVariable Long tagId) {
    tagService.deleteTag(tagId);
  }
}
