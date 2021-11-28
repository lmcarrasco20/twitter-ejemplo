package com.example.twitter.controller;

import com.example.twitter.DTO.HashtagDTO;
import com.example.twitter.DTO.TweetDTO;
import com.example.twitter.service.TweetService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/twitter")
public class TweetController {

    private static final Log LOG = LogFactory.getLog(TweetController.class);

    @Autowired
    TweetService tweetService;

    // revisamos que se hayan cargado los tweets en BBDD antes de hacer cualquier operación, si no están los cargamos
    public void findData() {
        List<TweetDTO> list = tweetService.findAll();

        if (list == null || list.size() == 0) {
            tweetService.getTweets();

            try {
                Thread.sleep(2000);
            }
            catch (java.lang.InterruptedException e) {}
        }
    }

    // listado completo de Tweets
    @GetMapping("/list")
    public List<TweetDTO> list() {
        findData();

        return tweetService.findAll();
    }

    // listado completo de Tweets por validador
    @GetMapping("/list/{validator}")
    public List<TweetDTO> listByValidator(@PathVariable String validator){
        findData();

        return tweetService.listByValidator(validator);
    }

    // validación de un Tweet por un validador
    @GetMapping("/validate/{validator}/{id}")
    public ResponseEntity<String> validate(@PathVariable String validator, @PathVariable String id){
        findData();

        boolean result = tweetService.validate(validator, id);

        if (result) return new ResponseEntity<>("SUCESSFULLY", HttpStatus.OK);
            else return new ResponseEntity<>("UNSUCESSFULLY", HttpStatus.BAD_REQUEST);
    }

    // listado de los 10 hashtag más utilizazodos
    @GetMapping("/hashtag")
    public List<HashtagDTO> top10hashtag(){
        findData();

        return tweetService.listHashtag();
    }
}