package kz.edev.comment.controller;

import kz.edev.comment.entity.Comment;
import kz.edev.comment.entity.Product;
import kz.edev.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Optional;


@RestController
@RequestMapping("/comments")
public class CommentController {

    private static final String TOPIC = "store_rating_test";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

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
        kafkaTemplate.send(TOPIC, comment.getText());
        return commentService.create(comment);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        commentService.delete(id);
    }
}
