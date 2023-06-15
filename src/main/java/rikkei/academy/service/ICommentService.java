package rikkei.academy.service;


import rikkei.academy.model.Comment;
import rikkei.academy.model.Users;


public interface ICommentService extends IGenericService<Comment, Long> {
    Iterable<Comment> findCommentByUser(Users user);
}
