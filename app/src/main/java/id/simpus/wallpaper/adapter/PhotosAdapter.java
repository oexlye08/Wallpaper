package id.simpus.wallpaper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import id.simpus.wallpaper.R;
import id.simpus.wallpaper.ui.photos.model.Photo;
import id.simpus.wallpaper.utils.GlideApp;
import id.simpus.wallpaper.utils.SquareImage;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {

    private Context context;
    private List<Photo> photos;

    public PhotosAdapter (Context context, List<Photo> photos){
        this.context = context;
        this.photos = photos;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photos, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Photo photo = photos.get(position);
        holder.username.setText(photo.getUser().getUsername());
        GlideApp
                .with(context)
                .load(photo.getUrl().getSmall())
                .placeholder(R.drawable.placeholder)
                .override(600,600)
                .into(holder.photo);

        GlideApp
                .with(context)
                .load(photo.getUser().getProfilImage().getSmall())
                .into(holder.userAvatar);

    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_photo_user_avatar)
        CircleImageView userAvatar;

        @BindView(R.id.item_photo_username)
        TextView username;

        @BindView(R.id.item_photo_photo)
        SquareImage photo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
