package kz.edev.comment.service;

import kz.edev.comment.entity.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    List<Comment> getByUserId(Long id);
    Comment create(Comment comment);
    void delete(Long id);
}
