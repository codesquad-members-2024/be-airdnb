package team8.airbnb.hostroomtags;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import team8.airbnb.hostroom.Hostroom;
import team8.airbnb.hostroom.HostroomRepository;
import team8.airbnb.tag.Tag;
import team8.airbnb.tag.TagRepository;

@Service
public class HostroomTagService {

  private HostroomTagRepository hostroomTagRepository;
  private HostroomRepository hostroomRepository;
  private TagRepository tagRepository;

  @Autowired
  public HostroomTagService(HostroomTagRepository hostroomTagRepository,
      HostroomRepository hostroomRepository, TagRepository tagRepository) {
    this.hostroomTagRepository = hostroomTagRepository;
    this.hostroomRepository = hostroomRepository;
    this.tagRepository = tagRepository;
  }

//  public void addSingleTag(@PathVariable Long hostroomId, String tagName) {
//    Hostroom hostroom = getHostroom(hostroomId);
//    saveTag(hostroomId, tagName, hostroom);
//  }

  public void addTags(@PathVariable Long hostroomId, List<String> tagsName) {
    /*
    처음에는 삭제 코드를 구현하려다가 생각해보니 수정할 때도 다시 선택 후 저장하는 것과 마찬가지라서
    모든 태그를 삭제하면 null을 받아서 return으로 그냥 아무것도 저장하지 않은 상태로 만들고
    수정하는 기능도 어차피 다시 추가하는 것과 마찬가지여서 구현할 필요가 없다고 생각함.
     */
    // tagsName이 null이면 아무 작업도 수행하지 않고 종료
    if (tagsName == null) {
      removeAllTags(hostroomId);
      return;
    }

    Hostroom hostroom = getHostroom(hostroomId);

    tagsName.forEach(tagName -> {
      Tag tag = getTag(tagName);
      checkHostroomtag(hostroomId, tag.getId());

      HostroomTag hostroomTag = new HostroomTag();
      hostroomTag.setHostroomId(hostroomId);
      hostroomTag.setTagId(tag.getId());
      hostroomTag.setHostroom(hostroom);
      hostroomTag.setTag(tag);

      hostroomTagRepository.save(hostroomTag);
    });
  }

  private Tag getTag(String tagName) {
    return tagRepository.findByTagName(tagName)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 태그입니다." + tagName));
  }

  private Hostroom getHostroom(Long hostroomId) {
    return hostroomRepository.findById(hostroomId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 방입니다." + hostroomId));
  }

  private void checkHostroomtag(Long hostroomId, Long tagId) {
    Optional<HostroomTag> existHostroomTag = hostroomTagRepository.findById(
        new HostroomTagId(hostroomId, tagId));
    if (existHostroomTag.isPresent()) {
      throw new IllegalArgumentException("이미 존재하는 태그입니다.");
    }
  }

  private void removeAllTags(Long hostroomId) {
    List<HostroomTag> hostroomTags = hostroomTagRepository.findByHostroomId(hostroomId);
    hostroomTagRepository.deleteAll(hostroomTags);
  }
}
