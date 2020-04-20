package id.simpus.wallpaper.ui.collection.model;

import com.google.gson.annotations.SerializedName;

import id.simpus.wallpaper.ui.photos.model.Photo;
import id.simpus.wallpaper.ui.photos.model.User;

public class Collection {

    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String tittle;
    @SerializedName("description")
    private String description;
    @SerializedName("total_photos")
    private int totalPhotos;
    @SerializedName("cover_photo")
    private Photo coverPhoto = new Photo();
    @SerializedName("user")
    private User user = new User();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotalPhotos() {
        return totalPhotos;
    }

    public void setTotalPhotos(int totalPhotos) {
        this.totalPhotos = totalPhotos;
    }

    public Photo getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(Photo coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
