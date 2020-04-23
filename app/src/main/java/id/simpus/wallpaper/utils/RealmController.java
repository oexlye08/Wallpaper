package id.simpus.wallpaper.utils;

import java.util.List;

import id.simpus.wallpaper.ui.photos.model.Photo;
import io.realm.Realm;

public class RealmController {
    public final Realm realm;

    public RealmController() {
        realm = Realm.getDefaultInstance();
    }

    public void savePhoto (Photo photo){
        realm.beginTransaction();
        realm.copyToRealm(photo);
        realm.commitTransaction();
    }

    public void deletePhoto (Photo photo){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Photo resultPhoto = realm.where(Photo.class).equalTo("id", photo.getId()).findFirst();
                resultPhoto.deleteFromRealm();
            }
        });
    }

    public boolean isPhotoExist (String photoId){
        Photo resultPhoto = realm.where(Photo.class).equalTo("id", photoId).findFirst();
        if (resultPhoto == null)
            return false;
        return true;
    }

    public List<Photo> getPhoto(){
        return realm.where(Photo.class).findAll();
    }
}
