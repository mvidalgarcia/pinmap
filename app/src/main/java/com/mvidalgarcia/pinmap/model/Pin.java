package com.mvidalgarcia.pinmap.model;

/**
 * Created by mvidalgarcia on 15/4/15.
 */
public class Pin {
    private int id;
    private String title;
    private double rating;
    private String description;
    private double lat;
    private double lng;
    private String photo;
    private int date;
    private String googleplusId;

    public Pin(int id, String title, String description,
               double rating, double lat, double lng,
               String photo, int date, String googleplusId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.lat = lat;
        this.lng = lng;
        this.photo = photo;
        this.date = date;
        this.googleplusId = googleplusId;
    }

    public Pin(String title, String description,
               double rating, double lat, double lng,
               String photo, int date, String googleplusId) {
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.lat = lat;
        this.lng = lng;
        this.photo = photo;
        this.date = date;
        this.googleplusId = googleplusId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
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

    public double getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getGooglePlusId() {
        return googleplusId;
    }

    public void setGooglePlusId(String googlePlusId) {
        this.googleplusId = googlePlusId;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Pin{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", rating=" + rating +
                ", description='" + description + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", photo='" + photo + '\'' +
                ", date=" + date +
                ", googleplusId='" + googleplusId + '\'' +
                '}';
    }
}
