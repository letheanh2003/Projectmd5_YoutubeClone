package rikkei.academy.service;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rikkei.academy.model.Subscription;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ISubscriptionService extends IGenericService<Subscription, Long> {
    Subscription findSubscriptionByChannel(Long channelId);

    Optional<Subscription> findSubscriptionByUserIdAndChannelId(Long userId, Long channelId);

    @Query("SELECT COUNT(s) FROM Subscription s WHERE s.id = :channelId")
    Long countSubscriptionByChannelId(@Param("channelId") Long channelId);

    @Modifying
    @Transactional
    @Query(value = "delete from subscription where id =?1", nativeQuery = true)
    void deleteBySubId(Long id);
}
