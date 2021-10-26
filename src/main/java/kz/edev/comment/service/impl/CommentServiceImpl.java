package kz.edev.comment.service.impl;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import kz.edev.comment.entity.Comment;
import kz.edev.comment.entity.Profile;
import kz.edev.comment.entity.UserComments;
import kz.edev.comment.repository.CommentRepository;
import kz.edev.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    private RestTemplate restTemplate;

    public UserComments getUserComments(Long id){
        List<Comment> comments = commentRepository.getByProfileId(id);

        UserComments userComments = new UserComments();
        userComments.setComments(comments);

        return userComments;
    }

    public Comment create(Comment comment){
        return commentRepository.saveAndFlush(comment);
    }

    public void delete(Long id){
        commentRepository.deleteById(id);
    }

    @Override
    @HystrixCommand(
            fallbackMethod = "getUserByIdFallback"
    )
    public Profile getProfileById(Long id) {
        return restTemplate.getForObject("http://store-profile-service/profile/" + id, Profile.class);
    }

    public Profile getUserByIdFallback(Long id) {
        Profile profile = new Profile();
        profile.setName("Profile is not available: Service Unavailable");
        profile.setSurname("Unknown");
        return profile;
    }

}
