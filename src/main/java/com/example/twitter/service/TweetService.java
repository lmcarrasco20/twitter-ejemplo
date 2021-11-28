package com.example.twitter.service;

import com.example.twitter.component.DTOToEntity;
import com.example.twitter.component.EntityToDTO;
import com.example.twitter.DTO.HashtagDTO;
import com.example.twitter.DTO.TweetDTO;
import com.example.twitter.controller.TweetController;
import com.example.twitter.entity.Tweet;
import com.example.twitter.repository.TweetRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.*;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class TweetService {

    private static final Log LOG = LogFactory.getLog(TweetController.class);

    @Autowired
    TweetRepository tweetRepository;

    /*
     nos conectamos a la API de Twitter y nos traemos todos lo tweets que cumplen una serie de requisitos:
     - El lenguaje del tweet sea unos de los siguientes; Español, Inglés o Italiano
     - Que el usuario tenga más de 1500 followers

     De los tweets guadamos los siguientes datos:
     - Id del tweet
     - Texto
     - Id del usuario
     - Nombre del usuario
     - Localiazación
     - Hashtag que lleva el tweet
     */
    public void getTweets(){
        Set<String> langSet = new HashSet<>();
        langSet.add("es");
        langSet.add("en");
        langSet.add("it");

        StatusListener listener = new StatusAdapter() {
            @Override
            public void onStatus(Status status) {
                for (java.lang.String lang : langSet) {
                    User user = status.getUser();

                    if (status.getLang().equals(lang) && user.getFollowersCount() > 1500) {
                        String hashtag = "";
                        HashtagEntity[] hashtagEntity = status.getHashtagEntities();
                        for(int i = 0; i < hashtagEntity.length; i++){
                            if (hashtag.length() == 0) hashtag = hashtagEntity[i].getText();
                                else hashtag += "#" + hashtagEntity[i].getText();
                        }

                        TweetDTO tweetDTO = new TweetDTO();

                        tweetDTO.setIdStr(String.valueOf(status.getId()));
                        tweetDTO.setText(new String(status.getText().getBytes(), StandardCharsets.UTF_8));
                        tweetDTO.setUserId(user.getId());
                        tweetDTO.setUserScreenName(user.getScreenName());
                        tweetDTO.setValidado(false);
                        tweetDTO.setValidador("");
                        tweetDTO.setLocation(user.getLocation());
                        tweetDTO.setHashtag(hashtag);

                        newTweet(tweetDTO);
                    }
                }
            }
        };

        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        FilterQuery filterQuery = new FilterQuery();
        filterQuery.track(langSet.toArray(new String[0]));
        twitterStream.addListener(listener);
        twitterStream.filter(filterQuery);
    }

    // Obtenemos una lista de tweets volcados a una lista dto para pasarlo al controller
    public List<TweetDTO> findAll(){
        List<TweetDTO> listTweetDTO = new ArrayList<>();

        List<Tweet> listTweet = tweetRepository.findAll();
        for(Tweet tweet : listTweet) listTweetDTO.add(EntityToDTO.EntityTweetToTweetDTO(tweet));

        return listTweetDTO;
    }

    // guardamos en la BBDD un nuevo tweet
    public void newTweet(TweetDTO tweetDTO){
        Optional<Tweet> optionalTweet = tweetRepository.findByIdStr(tweetDTO.getIdStr());

        if (!optionalTweet.isPresent()) tweetRepository.save(DTOToEntity.TweetDTOToEntityTweet(tweetDTO));
    }

    // validamos un tweet por id he indicamos el nombre del validador
    public boolean validate(String validator, String idStr){
        boolean result = false;

        Optional<Tweet> optionalTweet = tweetRepository.findByIdStr(idStr);

        if (optionalTweet.isPresent()) {
            Tweet tweet = optionalTweet.get();

            tweet.setValidado(true);
            tweet.setValidador(validator);

            Tweet tweetSave = tweetRepository.save(tweet);

            if (tweetSave != null) result = true;
        }

        return result;
    }

    // lista de tweets de un validador pasados a una lista dto para el controller
    public List<TweetDTO> listByValidator(String validator){
        List<TweetDTO> listTweetDTO = new ArrayList<>();

        List<Tweet> listTweet = tweetRepository.findAllByValidador(validator);
        for(Tweet tweet : listTweet) listTweetDTO.add(EntityToDTO.EntityTweetToTweetDTO(tweet));

        return listTweetDTO;
    }

    // listamos los 10 hashtag más utilizados de más a menos utilizado
    public List<HashtagDTO> listHashtag(){
        List<HashtagDTO> hashtagList = new ArrayList<>();

        // de la tabla de tweets nos traemos todos los hashtag y los insertamos en una lista
        List<Tweet> tweetList = tweetRepository.findAllByHashtag();
        List<String> stringList = new ArrayList<>();
        for(Tweet tweet : tweetList){
            String[] tmp = tweet.getHashtag().split("#");
            for(String string : tmp) stringList.add(string);
        }

        // creamos un map con los hashtag obtenidos en la lista quitando los duplicados y contabilizando el n. de apariciones
        Map<String, Integer> map = new HashMap<>();
        for(String string : stringList) {
            if (!map.containsKey(string)) map.put(string, Collections.frequency(stringList, string));
        }

        // convertimos el map anterior a un array
        String[][] list = new String[map.size()][2];
        int h = 0;
        for(Map.Entry<String, Integer> entry : map.entrySet()) {
            list[h][0] = entry.getKey();
            list[h][1] = entry.getValue().toString();
            h++;
        }

        // ordenamos el array por n. de apariciones de un hashtag ordenado descendentemente
        // y nos quedamos con los 10 más usados
        String[][] listOrder = Arrays.stream(list).sorted(Comparator.comparing(x -> -Integer.parseInt(x[1]))).toArray(String[][]::new);
        if (listOrder.length > 0) {
            hashtagList.add(EntityToDTO.CreateHashtagDTO(listOrder[0][0], Integer.parseInt(listOrder[0][1])));

            int nVeces = 1;
            int next;
            for (int i = 0; i < listOrder.length; i++) {
                next = i + 1;

                if (next < listOrder.length && listOrder[i][0] != listOrder[next][0]) {
                    hashtagList.add(EntityToDTO.CreateHashtagDTO(listOrder[next][0], Integer.parseInt(listOrder[next][1])));
                    nVeces++;
                }

                if (nVeces == 10) break;
            }
        }

        return hashtagList;
    }
}