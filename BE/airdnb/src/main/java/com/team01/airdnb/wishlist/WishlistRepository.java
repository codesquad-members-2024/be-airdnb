package com.team01.airdnb.wishlist;

import com.team01.airdnb.accommadation.Accommodation;
import com.team01.airdnb.amenity.Amenity;
import com.team01.airdnb.wishlist.dto.WishlistListResponse;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

  @Query("SELECT w FROM Wishlist w WHERE w.user.id = :userId ")
  List<WishlistListResponse> findWishlistByUserId(@Param("userId") String userId);

}
