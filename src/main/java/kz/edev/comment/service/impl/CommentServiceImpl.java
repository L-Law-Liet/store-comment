package kz.edev.comment.service.impl;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import kz.edev.comment.entity.Comment;
import kz.edev.comment.entity.Profile;
import kz.edev.comment.entity.UserComments;
import kz.edev.comment.repository.CommentRepository;
import kz.edev.comment.service.CommentService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
            fallbackMethod = "getUserByIdFallback",
            threadPoolKey = "getUserById",
            threadPoolProperties = {
                    @HystrixProperty(name="coreSize", value="40"),
                    @HystrixProperty(name="maxQueueSize", value="20"),
            })
    public Profile getProfileById(Long id) {
        String apiCredentials = "rest-client:p@ssword";
        String base64Credentials = new String(Base64.encodeBase64(apiCredentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        HttpEntity<String> entity = new HttpEntity<>(headers);

//        return restTemplate.getForObject("http://store-profile-service/profile/" + id, Profile.class);

        return restTemplate.exchange("http://store-profile-service/profile/" + id,
                HttpMethod.GET, entity, Profile.class).getBody();
    }

    public Profile getUserByIdFallback(Long id) {
        Profile profile = new Profile();
        profile.setName("Profile is not available: Service Unavailable");
        profile.setSurname("Unknown");
        return profile;
    }

}
