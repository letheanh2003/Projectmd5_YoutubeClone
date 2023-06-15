package rikkei.academy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rikkei.academy.model.Comment;
import rikkei.academy.model.Users;

public interface ICommentRepository extends JpaRepository<Comment,Long> {
    Iterable<Comment> findCommentByUser(Users user);
}
