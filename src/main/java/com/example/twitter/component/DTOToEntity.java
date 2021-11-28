package com.example.twitter.component;

import com.example.twitter.DTO.TweetDTO;
import com.example.twitter.entity.Tweet;

public class DTOToEntity {

    // convertimos el dto TweetDTO a la entidad Tweet
    public static Tweet TweetDTOToEntityTweet(TweetDTO tweetDTO){
        Tweet tweet = new Tweet();

        tweet.setIdStr(tweetDTO.getIdStr());
        tweet.setText(tweetDTO.getText());
        tweet.setUserId(tweetDTO.getUserId());
        tweet.setUserScreenName(tweetDTO.getUserScreenName());
        tweet.setValidado(tweetDTO.getValidado());
        tweet.setValidador(tweetDTO.getValidador());
        tweet.setLocation(tweetDTO.getLocation());
        tweet.setHashtag(tweetDTO.getHashtag());

        return tweet;
    }
}