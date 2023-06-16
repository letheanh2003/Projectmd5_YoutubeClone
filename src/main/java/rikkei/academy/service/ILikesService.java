package rikkei.academy.service;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rikkei.academy.model.Like;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ILikesService extends IGenericService<Like, Long> {
    Like findLikesByVideosId(Long videoId);

    Optional<Like> findLikesByUserIdAndVideosId(Long userId, Long videoId);

    @Query("SELECT COUNT(l) FROM Like l WHERE l.id = :videoId")
    Long countLikesByVideoId(@Param("videoId") Long videoId);

    @Modifying
    @Transactional
    @Query(value = "delete from likes where id =?1", nativeQuery = true)
    void deleteByLikeId(Long id);
}
