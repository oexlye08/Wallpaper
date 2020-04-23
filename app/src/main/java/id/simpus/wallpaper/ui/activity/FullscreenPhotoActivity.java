package id.simpus.wallpaper.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.w3c.dom.Text;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import id.simpus.wallpaper.R;
import id.simpus.wallpaper.ui.photos.model.Photo;
import id.simpus.wallpaper.utils.Functions;
import id.simpus.wallpaper.utils.GlideApp;
import id.simpus.wallpaper.utils.RealmController;
import id.simpus.wallpaper.web_services.ApiInterfaces;
import id.simpus.wallpaper.web_services.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullscreenPhotoActivity extends AppCompatActivity {


    @BindView(R.id.activity_fullscreenphoto_photo)
    ImageView fullscreenPhoto;
    @BindView(R.id.activity_fullscreenphoto_user_avatar)
    CircleImageView avatar;
    @BindView(R.id.activity_fullscreenphoto_username)
    TextView username;
    @BindView(R.id.activity_fullscreenphoto_fab_menu)
    FloatingActionMenu fab_menu;
    @BindView(R.id.activity_fullscreenphoto_fab_favorit)
    FloatingActionButton fab_favorit;
    @BindView(R.id.activity_fullscreenphoto_fab_wallpaper)
    FloatingActionButton fab_wallpaper;

    @BindDrawable(R.drawable.ic_check_favorite)
    Drawable icFavorite;
    @BindDrawable(R.drawable.ic_check_favorited)
    Drawable icFavorited;

    private Unbinder unbinder;
    private Bitmap photoBitmap;

    private RealmController realmController;

    private Photo photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_photo);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        unbinder = ButterKnife.bind(this);
        Intent intent = getIntent();
        String photoId = intent.getStringExtra("photoId");

        realmController = new RealmController();
        if (realmController.isPhotoExist(photoId)){
            fab_favorit.setImageDrawable(icFavorited);
        }

        getPhotosIntent(photoId);
    }

    private void getPhotosIntent(String id){
        ApiInterfaces apiInterfaces = ServiceGenerator.createService(ApiInterfaces.class);
        Call<Photo> call = apiInterfaces.getFullscreenPhoto(id);
        call.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {
                if (response.isSuccessful()){
                    photo = response.body();
                    updateUI(photo);
                }
            }

            @Override
            public void onFailure(Call<Photo> call, Throwable t) {

            }
        });
    }

    private void updateUI(Photo photo) {
        try {
            username.setText(photo.getUser().getUsername());
            GlideApp.with(FullscreenPhotoActivity.this)
                    .load(photo.getUser().getProfilImage().getSmall())
                    .into(avatar);

            GlideApp.with(FullscreenPhotoActivity.this)
                    .asBitmap()
                    .load(photo.getUrl().getRegular())
                    .centerCrop()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            fullscreenPhoto.setImageBitmap(resource);

                            photoBitmap =resource;
                        }
                    });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @OnClick(R.id.activity_fullscreenphoto_fab_favorit)
    public void setFab_favorit(){
        if (realmController.isPhotoExist(photo.getId())){
            realmController.deletePhoto(photo);
            fab_favorit.setImageDrawable(icFavorite);
            Toast.makeText(this, "Remove Favorite", Toast.LENGTH_SHORT).show();
        }else {
            realmController.savePhoto(photo);
            fab_favorit.setImageDrawable(icFavorited);
            Toast.makeText(this, "Favorited", Toast.LENGTH_SHORT).show();
        }

        fab_menu.close(true);
    }

    @OnClick(R.id.activity_fullscreenphoto_fab_wallpaper)
    public void setFab_wallpaper(){
        if (photoBitmap != null){
            if (Functions.setWallpaper(FullscreenPhotoActivity.this, photoBitmap)){
                Toast.makeText(this, "Set Wallpaper Successfully", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Set Wallpaper Failed", Toast.LENGTH_SHORT).show();
            }
        }
        fab_menu.close(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
