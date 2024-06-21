package team8.airbnb.tag;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team8.airbnb.hostroomtags.HostroomTag;
import team8.airbnb.hostroomtags.HostroomTagRepository;

@Slf4j
@Service
public class TagService {
  private TagRepository tagRepository;
  private HostroomTagRepository hostroomTagRepository;

  @Autowired
  public TagService(TagRepository tagRepository, HostroomTagRepository hostroomTagRepository) {
    this.tagRepository = tagRepository;
    this.hostroomTagRepository = hostroomTagRepository;
  }

  public void saveTag(TagRequest tagRequest) {
    Tag tag = new Tag();
    tag.setTagName(tagRequest.tagName());

    tagRepository.save(tag);
    log.debug("태그 저장 완료 : {}", tag.getTagName());
  }

  public void updateTag(Long tagId , TagRequest tagRequest) {
    Tag tag = tagRepository.findById(tagId)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 태그입니다."));
    String tagName = tag.getTagName();
    tag.setTagName(tagRequest.tagName());

    tagRepository.save(tag);
    log.debug("{} -> {} 수정 완료", tagName, tagRequest.tagName());
  }

  public void deleteTag(Long tagId) {
    Tag tag = tagRepository.findById(tagId)
        .orElseThrow(() -> new IllegalArgumentException("태그를 찾을 수 없습니다. id: " + tagId));

    // 태그를 사용하는 호스트룸 태그를 먼저 삭제
    List<HostroomTag> hostroomTags = hostroomTagRepository.findByTagId(tagId);
    hostroomTagRepository.deleteAll(hostroomTags);

    // 태그 삭제
    tagRepository.delete(tag);

    log.debug("태그 삭제 완료: {}", tag.getTagName());
  }
}
