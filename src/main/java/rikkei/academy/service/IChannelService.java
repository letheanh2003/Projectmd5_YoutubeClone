package rikkei.academy.service;


import rikkei.academy.model.Channel;
import rikkei.academy.model.Users;

import java.util.List;

public interface IChannelService extends IGenericService<Channel, Long> {

    boolean existsChannelByUser(Users users);
    List<Channel> findByChanelNameContaining(String channelName);

}
