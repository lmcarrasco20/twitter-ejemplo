package com.example.twitter.repository;

import com.example.twitter.entity.Tweet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TweetRepository extends CrudRepository<Tweet, Long> {

    List<Tweet> findAll();
    Optional<Tweet> findByIdStr(String idStr);
    Tweet save(Tweet tweet);
    List<Tweet> findAllByValidador(String validador);
    @Query(value = "select * from Tweet where LENGTH(hashtag) > 0", nativeQuery = true)
    List<Tweet> findAllByHashtag();

}