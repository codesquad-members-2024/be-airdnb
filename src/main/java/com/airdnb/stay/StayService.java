package com.airdnb.stay;

import com.airdnb.global.exception.ForbiddenException;
import com.airdnb.global.exception.NotFoundException;
import com.airdnb.image.ImageService;
import com.airdnb.image.entity.Image;
import com.airdnb.member.MemberService;
import com.airdnb.member.entity.Member;
import com.airdnb.stay.dto.StayCommentDetail;
import com.airdnb.stay.dto.StayCreateRequest;
import com.airdnb.stay.dto.StayDetailQueryResponse;
import com.airdnb.stay.entity.CommentStatus;
import com.airdnb.stay.entity.Location;
import com.airdnb.stay.entity.Stay;
import com.airdnb.stay.entity.Stay.StayType;
import com.airdnb.stay.entity.StayComment;
import com.airdnb.stay.entity.StayStatus;
import com.airdnb.stay.repository.StayCommentRepository;
import com.airdnb.stay.repository.StayRepository;
import com.airdnb.staytag.StayTag;
import com.airdnb.staytag.StayTagService;
import com.airdnb.tag.entity.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StayService {
    private final StayRepository stayRepository;
    private final StayCommentRepository stayCommentRepository;
    private final StayTagService stayTagService;
    private final ImageService imageService;
    private final MemberService memberService;

    @Transactional
    public Long createStay(StayCreateRequest stayCreateRequest) {
        Stay stay = buildStay(stayCreateRequest);
        stayRepository.save(stay);

        stayTagService.createStayTags(stay, stayCreateRequest.getTagIds());
        return stay.getId();
    }

    @Transactional(readOnly = true)
    public StayDetailQueryResponse queryStayDetailById(Long id) {
        Stay stay = findActiveStayById(id);

        return StayDetailQueryResponse.builder()
                .id(stay.getId())
                .name(stay.getName())
                .hostName(stay.getHost().getName())
                .imageUrl(stay.getImage().getUrl())
                .price(stay.getPrice())
                .maxGuests(stay.getMaxGuests())
                .address(stay.getLocation().getAddress())
                .latitude(stay.getLocation().getLatitude())
                .longitude(stay.getLocation().getLongitude())
                .startDate(stay.getStartDate())
                .endDate(stay.getEndDate())
                .type(stay.getType().name())
                .tagNames(getTagNames(stay))
                .comments(getActiveCommentDetails(stay))
                .rating(getActiveRating(stay))
                .closedDates(stay.getClosedStayDates())
                .build();
    }

    @Transactional(readOnly = true)
    public Stay findActiveStayById(Long id) {
        return stayRepository.findByIdAndStatus(id, StayStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException("id와 일치하는 숙소를 찾을 수 없습니다."));
    }

    @Transactional
    public void softDeleteStay(Long id) {
        Stay stay = findActiveStayById(id);
        String currentMemberId = memberService.getCurrentMemberId();
        if (!stay.hasSameHostId(currentMemberId)) {
            throw new ForbiddenException("숙소 삭제 권한이 없습니다.");
        }
        stay.softDelete();
    }

    private Stay buildStay(StayCreateRequest stayCreateRequest) {
        Image image = getImage(stayCreateRequest.getImageId());
        Member host = getHost();
        StayType stayType = StayType.of(stayCreateRequest.getType());
        Location location = new Location(stayCreateRequest.getAddress(), stayCreateRequest.getLatitude(),
                stayCreateRequest.getLongitude());

        return Stay.builder()
                .name(stayCreateRequest.getName())
                .image(image)
                .price(stayCreateRequest.getPrice())
                .host(host)
                .startDate(stayCreateRequest.getStartDate())
                .endDate(stayCreateRequest.getEndDate())
                .status(StayStatus.ACTIVE)
                .maxGuests(stayCreateRequest.getMaxGuests())
                .location(location)
                .type(stayType)
                .build();
    }

    private Member getHost() {
        String currentMemberId = memberService.getCurrentMemberId();
        return memberService.findMemberById(currentMemberId);
    }

    private Image getImage(Long imageId) {
        if (imageId == null) {
            return null;
        }
        return imageService.findImageById(imageId);
    }

    private List<String> getTagNames(Stay stay) {
        return stay.getStayTags().stream()
                .map(StayTag::getTag)
                .map(Tag::getName)
                .toList();
    }

    private List<StayCommentDetail> getActiveCommentDetails(Stay stay) {
        List<StayComment> comments = stayCommentRepository.findCommentsByStayIdAndStatus(stay.getId(),
                CommentStatus.ACTIVE);

        return comments.stream()
                .map(comment -> StayCommentDetail.builder()
                        .id(comment.getId())
                        .content(comment.getContent())
                        .writer(comment.getWriter())
                        .createdAt(comment.getCreatedAt())
                        .rating(comment.getRating())
                        .build())
                .toList();
    }

    private Double getActiveRating(Stay stay) {
        return stayCommentRepository.findCommentRatingAvg(stay.getId(), CommentStatus.ACTIVE);
    }
}
