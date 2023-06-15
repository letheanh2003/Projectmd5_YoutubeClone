package rikkei.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rikkei.academy.model.Videos;


public interface IVideoRepository extends JpaRepository<Videos,Long> {
}
