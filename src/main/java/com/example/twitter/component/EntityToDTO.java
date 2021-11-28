package com.example.twitter.component;

import com.example.twitter.DTO.HashtagDTO;
import com.example.twitter.DTO.TweetDTO;
import com.example.twitter.entity.Tweet;

public class EntityToDTO {

    // convertimos la entidad Tweet al dto TweetDTO
    public static TweetDTO EntityTweetToTweetDTO(Tweet tweet){
        TweetDTO tweetDTO = new TweetDTO();

        tweetDTO.setIdStr(tweet.getIdStr());
        tweetDTO.setText(tweet.getText());
        tweetDTO.setUserId(tweet.getUserId());
        tweetDTO.setUserScreenName(tweet.getUserScreenName());
        tweetDTO.setValidado(tweet.getValidado());
        tweetDTO.setValidador(tweet.getValidador());
        tweetDTO.setLocation(tweet.getLocation());
        tweetDTO.setHashtag(tweet.getHashtag());

        return tweetDTO;
    }

    // insertamos en el dto HashtagDETO los parámetros recogidos
    public static HashtagDTO CreateHashtagDTO(String hashtag, Integer nVeces){
        HashtagDTO hashtagDTO = new HashtagDTO();

        hashtagDTO.setHashtag(hashtag);
        hashtagDTO.setnVeces(nVeces);

        return hashtagDTO;
    }
}