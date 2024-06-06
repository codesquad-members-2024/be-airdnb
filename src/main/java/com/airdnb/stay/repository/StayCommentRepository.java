package com.airdnb.stay.repository;

import com.airdnb.stay.entity.StayComment;
import java.util.List;

public interface StayCommentRepository {
    List<StayComment> findCommentsByStayId(Long stayId);

    Double findCommentRatingAvgByStayId(Long stayId);
}
