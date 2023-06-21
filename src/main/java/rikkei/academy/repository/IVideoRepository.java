package rikkei.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rikkei.academy.model.Videos;

import java.util.List;


public interface IVideoRepository extends JpaRepository<Videos, Long> {
    List<Videos> findByTitleContaining(String title);
}
