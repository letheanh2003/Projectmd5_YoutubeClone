package rikkei.academy.service.serviceIpm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rikkei.academy.model.Channel;
import rikkei.academy.model.Users;
import rikkei.academy.repository.IChannelRepository;
import rikkei.academy.service.IChannelService;

import java.util.List;

@Service
public class ChannelServiceIMPL implements IChannelService {
    @Autowired
    private IChannelRepository channelRepository;


    @Override
    public boolean existsChannelByUser(Users users) {
        return channelRepository.existsByUser(users);
    }

    @Override
    public List<Channel> findByChanelNameContaining(String channelName) {
        return channelRepository.findByChanelNameContaining(channelName);
    }


    @Override
    public List<Channel> findAll() {
        return channelRepository.findAll();
    }

    @Override
    public Channel save(Channel channel) {
        return channelRepository.save(channel);
    }

    @Override
    public void deleteById(Long id) {
        channelRepository.deleteById(id);
    }

    @Override
    public Channel findById(Long id) {
        return channelRepository.findById(id).get();
    }

}
