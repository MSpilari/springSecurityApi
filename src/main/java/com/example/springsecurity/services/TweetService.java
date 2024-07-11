package com.example.springsecurity.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.springsecurity.DTO.CreateTweetDTO;
import com.example.springsecurity.DTO.FeedItemDTO;
import com.example.springsecurity.entities.Role;
import com.example.springsecurity.entities.Tweet;
import com.example.springsecurity.exceptions.CustomException;
import com.example.springsecurity.repositories.TweetRepository;
import com.example.springsecurity.repositories.UserRepository;

@Service
public class TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    public Void createTweetService(CreateTweetDTO tweetBody, JwtAuthenticationToken token) {

        var user = userRepository.findById(UUID.fromString(token.getName()));

        var tweet = new Tweet();
        tweet.setUser(user.get());
        tweet.setContent(tweetBody.content());

        tweetRepository.save(tweet);

        return null;
    }

    public Page<FeedItemDTO> feedService(int page, int pageSize) {

        var tweets = tweetRepository.findAll(
                PageRequest.of(
                        page,
                        pageSize,
                        Sort.Direction.DESC,
                        "creationTimestamp"))
                .map(tweet -> new FeedItemDTO(
                        tweet.getTweetId(),
                        tweet.getContent(),
                        tweet.getUser().getUserName()));

        return tweets;
    }

    public void deleteTweetService(Long tweetId, JwtAuthenticationToken token) {

        var user = userRepository.findById(UUID.fromString(token.getName()));
        var tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var isAdmin = user.get().getRoles().stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase(Role.Values.admin.name()));

        if (isAdmin || tweet.getUser().getUserId().equals(UUID.fromString(token.getName()))) {
            tweetRepository.deleteById(tweetId);
            return;
        } else {
            throw new CustomException(HttpStatus.FORBIDDEN, "Don't have permission to delete this tweet");
        }

    }
}
