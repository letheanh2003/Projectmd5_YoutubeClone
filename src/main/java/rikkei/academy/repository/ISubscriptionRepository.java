package rikkei.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rikkei.academy.model.Subscription;

import java.util.Optional;

@Repository
public interface ISubscriptionRepository extends JpaRepository<Subscription, Long> {
    Subscription findSubscriptionByChannel(Long channelId);

    Optional<Subscription> findSubscriptionByUserIdAndChannelId(Long userId, Long channelId);

    @Query("SELECT COUNT(s) FROM Subscription s WHERE s.id = :channelId")
    Long countSubscriptionByChannelId(@Param("channelId") Long channelId);
}
