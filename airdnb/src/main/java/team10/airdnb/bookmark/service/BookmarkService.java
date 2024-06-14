package team10.airdnb.bookmark.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team10.airdnb.accommodation.entity.Accommodation;
import team10.airdnb.accommodation.exception.AccommodationIdNotFoundException;
import team10.airdnb.accommodation.repository.AccommodationRepository;
import team10.airdnb.bookmark.controller.request.BookmarkRequest;
import team10.airdnb.bookmark.controller.response.BookmarkSummaryResponse;
import team10.airdnb.bookmark.entity.Bookmark;
import team10.airdnb.bookmark.exception.BookmarkDuplicateException;
import team10.airdnb.bookmark.exception.BookmarkNotFoundException;
import team10.airdnb.bookmark.repository.BookmarkRepository;
import team10.airdnb.member.entity.Member;
import team10.airdnb.member.exception.MemberIdNotFoundException;
import team10.airdnb.member.repository.MemberRepository;

import java.awt.print.Book;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;
    private final AccommodationRepository accommodationRepository;

    public BookmarkSummaryResponse createBookmark(BookmarkRequest request) {
        Member member = getMemberById(request.memberId());

        Accommodation accommodation = getAccommodationById(request.accommodationId());

        validateIsBookmarked(member, accommodation);

        Bookmark bookmark = request.toEntity(member, accommodation);

        return BookmarkSummaryResponse.from(bookmarkRepository.save(bookmark));
    }

    public BookmarkSummaryResponse deleteBookmark(BookmarkRequest request) {
        Member member = getMemberById(request.memberId());

        Accommodation accommodation = getAccommodationById(request.accommodationId());

        Bookmark bookmark = getBookmarkByMemberAndAccommodation(member, accommodation);

        bookmarkRepository.delete(bookmark);

        return BookmarkSummaryResponse.from(bookmark);
    }

    public boolean checkBookmarked(BookmarkRequest request) {
        Member member = getMemberById(request.memberId());

        Accommodation accommodation = getAccommodationById(request.accommodationId());

        return bookmarkRepository.existsByMemberAndAccommodation(member, accommodation);
    }

    private void validateIsBookmarked(Member member, Accommodation accommodation) {
        if (bookmarkRepository.existsByMemberAndAccommodation(member, accommodation)) {
            throw new BookmarkDuplicateException();
        }
    }

    private Bookmark getBookmarkByMemberAndAccommodation(Member member, Accommodation accommodation) {
        return bookmarkRepository.findByMemberAndAccommodation(member, accommodation)
                .orElseThrow(BookmarkNotFoundException::new);
    }

    private Member getMemberById(String memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberIdNotFoundException::new);
    }

    private Accommodation getAccommodationById(long accommodationId) {
        return accommodationRepository.findById(accommodationId)
                .orElseThrow(AccommodationIdNotFoundException::new);
    }
}
