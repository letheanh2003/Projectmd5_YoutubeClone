package rikkei.academy.service.serviceIpm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rikkei.academy.model.Like;
import rikkei.academy.repository.ILikeRepository;
import rikkei.academy.service.ILikesService;

import java.util.List;
import java.util.Optional;

@Service
public class LikeServiceIMPL implements ILikesService {
    @Autowired
    private ILikeRepository likeRepository;


    @Override
    public List<Like> findAll() {
        return likeRepository.findAll();
    }

    @Override
    public Like save(Like like) {
        return likeRepository.save(like);
    }

    @Override
    public void deleteById(Long id) {
        likeRepository.deleteById(id);
    }

    @Override
    public Like findById(Long id) {
        return likeRepository.findById(id).get();
    }

    @Override
    public Like findLikesByVideosId(Long videoId) {
        return likeRepository.findLikesByVideosId(videoId);
    }

    @Override
    public Optional<Like> findLikesByUserIdAndVideosId(Long userId, Long videoId) {
        return likeRepository.findLikesByUserIdAndVideosId(userId, videoId);
    }

    @Override
    public Long countLikesByVideoId(Long videoId) {
        return null;
    }
}
