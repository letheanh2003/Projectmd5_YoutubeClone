package rikkei.academy.service.serviceIpm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rikkei.academy.model.Comment;
import rikkei.academy.model.Users;
import rikkei.academy.repository.ICommentRepository;
import rikkei.academy.service.ICommentService;

import java.util.List;

@Service
public class CommentServiceIMPL implements ICommentService {
    @Autowired
    private ICommentRepository commentRepository;

    @Override
    public Iterable<Comment> findCommentByUser(Users user) {
        return commentRepository.findCommentByUser(user);
    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id).get();
    }
}
