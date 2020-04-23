package id.simpus.wallpaper.ui.photos.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class User extends RealmObject {

    @SerializedName("id")
    private String id;

    @SerializedName("username")
    private String username;

    @SerializedName("profile_image")
    private ProfilImage profilImage = new ProfilImage();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ProfilImage getProfilImage() {
        return profilImage;
    }

    public void setProfilImage(ProfilImage profilImage) {
        this.profilImage = profilImage;
    }
}
