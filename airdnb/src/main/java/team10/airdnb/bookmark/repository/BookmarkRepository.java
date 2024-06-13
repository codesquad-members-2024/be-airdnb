package team10.airdnb.bookmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team10.airdnb.accommodation.entity.Accommodation;
import team10.airdnb.bookmark.entity.Bookmark;
import team10.airdnb.member.entity.Member;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByMemberAndAccommodation(Member member, Accommodation accommodation);
    boolean existsByMemberAndAccommodation(Member member, Accommodation accommodation);
}
