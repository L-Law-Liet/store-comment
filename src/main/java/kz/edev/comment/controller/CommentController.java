package kz.edev.comment.controller;

import kz.edev.comment.entity.Comment;
import kz.edev.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;


@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{profileId}")
    public ResponseEntity<?> getByUserId(@PathVariable("profileId") Long profileId) {
        return ResponseEntity.ok(commentService.getUserComments(profileId));
    }

    @GetMapping("/{profileId}/profile")
    public ResponseEntity<?> getUserByProfileId(@PathVariable("profileId") Long profileId) {
        return ResponseEntity.ok(commentService.getProfileById(profileId));
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
