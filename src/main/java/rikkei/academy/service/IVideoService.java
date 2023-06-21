package rikkei.academy.service;

import rikkei.academy.model.Videos;

import java.util.List;

public interface IVideoService extends IGenericService<Videos,Long> {
    List<Videos> findByTitleContaining(String title);
}
