package com.example.springsecurity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurity.DTO.CreateTweetDTO;
import com.example.springsecurity.DTO.FeedDTO;
import com.example.springsecurity.services.TweetService;

@RestController
public class TweetController {

    @Autowired
    private TweetService tweetService;

    @PostMapping("/tweets")
    public ResponseEntity<Void> createTweet(@RequestBody CreateTweetDTO tweetBody, JwtAuthenticationToken token) {

        tweetService.createTweetService(tweetBody, token);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/feed")
    public ResponseEntity<FeedDTO> feed(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        var tweets = tweetService.feedService(page, pageSize);

        return ResponseEntity.ok(
                new FeedDTO(tweets.getContent(), page, pageSize, tweets.getTotalPages(), tweets.getNumberOfElements()));
    }

    @DeleteMapping("/tweets/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable("id") Long tweetId, JwtAuthenticationToken token) {

        this.tweetService.deleteTweetService(tweetId, token);

        return ResponseEntity.ok().build();
    }

}
