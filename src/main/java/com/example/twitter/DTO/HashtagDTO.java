package com.example.twitter.DTO;

public class HashtagDTO {
    private String hashtag;
    private int nVeces;

    public HashtagDTO(){}

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public int getnVeces() {
        return nVeces;
    }

    public void setnVeces(int nVeces) {
        this.nVeces = nVeces;
    }
}