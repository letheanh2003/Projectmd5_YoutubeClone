package rikkei.academy.service.serviceIpm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rikkei.academy.model.Subscription;
import rikkei.academy.repository.ISubscriptionRepository;
import rikkei.academy.service.ISubscriptionService;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionServiceIMPL implements ISubscriptionService {
    @Autowired
    private ISubscriptionRepository subscriptionRepository;

    @Override
    public List<Subscription> findAll() {
        return subscriptionRepository.findAll();
    }

    @Override
    public Subscription save(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    @Override
    public void deleteById(Long id) {
        subscriptionRepository.deleteById(id);
    }

    @Override
    public Subscription findById(Long id) {
        return subscriptionRepository.findById(id).get();
    }

    @Override
    public Subscription findSubscriptionByChannel(Long channelId) {
        return subscriptionRepository.findSubscriptionByChannel(channelId);
    }

    @Override
    public Optional<Subscription> findSubscriptionByUserIdAndChannelId(Long userId, Long channelId) {
        return subscriptionRepository.findSubscriptionByUserIdAndChannelId(userId, channelId);
    }

    @Override
    public Long countSubscriptionByChannelId(Long channelId) {
        return subscriptionRepository.countSubscriptionByChannelId(channelId);
    }

    @Override
    public void deleteBySubId(Long id) {
        subscriptionRepository.deleteById(id);
    }
}
