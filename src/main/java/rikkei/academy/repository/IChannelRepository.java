package rikkei.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rikkei.academy.model.Channel;
import rikkei.academy.model.Users;

import java.util.List;

@Repository
public interface IChannelRepository extends JpaRepository<Channel, Long> {
    boolean existsByUser(Users users);

    List<Channel> findByChanelNameContaining(String channelName);


}

