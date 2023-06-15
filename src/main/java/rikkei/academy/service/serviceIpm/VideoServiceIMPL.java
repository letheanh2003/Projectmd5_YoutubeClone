package rikkei.academy.service.serviceIpm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rikkei.academy.model.Videos;
import rikkei.academy.repository.IVideoRepository;
import rikkei.academy.service.IVideoService;

import java.util.List;

@Service
public class VideoServiceIMPL implements IVideoService {
    @Autowired
    private IVideoRepository videoRepository;


    @Override
    public List<Videos> findAll() {
        return videoRepository.findAll();
    }

    @Override
    public Videos save(Videos videos) {
        return videoRepository.save(videos);
    }

    @Override
    public void deleteById(Long id) {
        videoRepository.deleteById(id);
    }

    @Override
    public Videos findById(Long id) {
        return videoRepository.findById(id).get();
    }
}
