package id.simpus.wallpaper.ui.photos.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class PhotoUrl extends RealmObject {

    @SerializedName("full")
    private String full;

    @SerializedName("regular")
    private String regular;

    @SerializedName("small")
    private String small;

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }
}
