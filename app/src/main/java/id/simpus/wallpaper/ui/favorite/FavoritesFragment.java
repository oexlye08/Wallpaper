package id.simpus.wallpaper.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import id.simpus.wallpaper.R;
import id.simpus.wallpaper.adapter.PhotosAdapter;
import id.simpus.wallpaper.ui.photos.model.Photo;
import id.simpus.wallpaper.utils.RealmController;

public class FavoritesFragment extends Fragment {
    @BindView(R.id.fragment_favorite_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_favorite_notif)
    TextView notif;

    private PhotosAdapter photosAdapter;
    private List<Photo> photos = new ArrayList<>();
    private Unbinder unbinder;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        unbinder = ButterKnife.bind(this, view);

        //RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        photosAdapter = new PhotosAdapter(getActivity(), photos);
        recyclerView.setAdapter(photosAdapter);

        getPhotos();

        return view;
    }

    private void getPhotos() {
        RealmController realmController  = new RealmController();
        photos.addAll(realmController.getPhoto());

        if (photos.size() == 0){
            notif.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            photosAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}