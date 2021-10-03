package kz.edev.comment.controller;

import kz.edev.comment.entity.Comment;
import kz.edev.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getByUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(commentService.getByUserId(userId));
    }

    @PostMapping("")
    public Comment create(@RequestBody Comment comment){
        comment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return commentService.create(comment);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        commentService.delete(id);
    }
}
