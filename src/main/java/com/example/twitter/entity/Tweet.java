package com.example.twitter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="tweet")
public class Tweet implements Serializable {

    @Id
    @Column(name="id_str", nullable = false, length = 50, unique = true)
    private String idStr;
    @Column(name="text", nullable = false, length = 1000)
    private String text;
    @Column(name="user_id", nullable = false, length = 20)
    private Long userId;
    @Column(name="user_screen_name", nullable = false, length = 50)
    private String userScreenName;
    @Column(name="validado")
    private Boolean validado;
    @Column(name="validador", length = 50)
    private String validador;
    @Column(name = "location", length = 1000)
    private String location;
    @Column(name = "hashtag", length = 1000)
    private String hashtag;

    public Tweet() {
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserScreenName() {
        return userScreenName;
    }

    public void setUserScreenName(String userScreenName) {
        this.userScreenName = userScreenName;
    }

    public Boolean getValidado() {
        return validado;
    }

    public void setValidado(Boolean validado) {
        this.validado = validado;
    }

    public String getValidador() {
        return validador;
    }

    public void setValidador(String validador) {
        this.validador = validador;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tweet tweet = (Tweet) o;
        return Objects.equals(idStr, tweet.idStr) && Objects.equals(text, tweet.text) && Objects.equals(userId, tweet.userId) && Objects.equals(userScreenName, tweet.userScreenName) && Objects.equals(validado, tweet.validado) && Objects.equals(validador, tweet.validador) && Objects.equals(location, tweet.location) && Objects.equals(hashtag, tweet.hashtag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idStr, text, userId, userScreenName, validado, validador, location, hashtag);
    }
}