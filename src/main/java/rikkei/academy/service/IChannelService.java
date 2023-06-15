package rikkei.academy.service;


import rikkei.academy.model.Channel;
import rikkei.academy.model.Users;

public interface IChannelService extends IGenericService<Channel, Long> {

    boolean existsChannelByUser(Users users);


}
