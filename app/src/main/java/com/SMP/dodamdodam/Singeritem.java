package com.SMP.dodamdodam;

public class Singeritem {
    String videoId;
    String title;
    String description;
    String  url;


    //생성
    public Singeritem(String videoId, String title, String description, String url) {
        this.videoId = videoId;
        this.title = title;
        this.description = description;
        this.url = url;
    }

    //변수에 접근할 때 .OO 접근하기보다는 안전하게 getter, setter를 이용합니다.
    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Singeritem{" +
                "videoId='" + videoId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' + '}';
    }
}
