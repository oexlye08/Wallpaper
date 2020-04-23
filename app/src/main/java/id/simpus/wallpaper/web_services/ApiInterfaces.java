package id.simpus.wallpaper.web_services;

import java.util.List;

import id.simpus.wallpaper.ui.collection.model.Collection;
import id.simpus.wallpaper.ui.photos.model.Photo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterfaces {

    @GET("photos")
    Call<List<Photo>> getPhotos();

    @GET("collections/featured")
    Call<List<Collection>> getCollection();

    @GET("collections/{id}")
    Call<Collection> getInformationOfCollection(@Path("id") int id);

    @GET("collections/{id}/photos")
    Call<List<Photo>> getPhotosOfCollection(@Path("id") int id);

    @GET("photos/{id}")
    Call<Photo> getFullscreenPhoto(@Path("id") String id);
}
