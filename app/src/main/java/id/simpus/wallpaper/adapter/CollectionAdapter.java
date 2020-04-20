package id.simpus.wallpaper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.simpus.wallpaper.R;
import id.simpus.wallpaper.ui.collection.model.Collection;
import id.simpus.wallpaper.utils.GlideApp;
import id.simpus.wallpaper.utils.SquareImage;

public class CollectionAdapter extends BaseAdapter {

    private Context context;
    private List<Collection> collections;
    public CollectionAdapter(Context context, List<Collection> collections){

        this.context = context;
        this.collections = collections;
    }

    @Override
    public int getCount() {
        return collections.size();
    }

    @Override
    public Object getItem(int position) {
        return collections.get(position);
    }

    @Override
    public long getItemId(int position) {
        return collections.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_collection, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        ButterKnife.bind(this, convertView);
        Collection collection = collections.get(position);
        if (collection.getTittle() !=null){
            holder.tittle.setText(collection.getTittle());
        }
        holder.totalPhotos.setText(String.valueOf(collection.getTotalPhotos()) + "Photos");
        GlideApp
                .with(context)
                .load(collection.getCoverPhoto().getUrl().getSmall())
                .into(holder.collectionPhotos);
        return convertView;
    }

    static class ViewHolder{
        @BindView(R.id.item_collection_title)
        TextView tittle;
        @BindView(R.id.item_collection_total_photos)
        TextView totalPhotos;
        @BindView(R.id.item_collection_photos)
        SquareImage collectionPhotos;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
