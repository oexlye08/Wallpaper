package id.simpus.wallpaper.ui.collections;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import id.simpus.wallpaper.R;
import id.simpus.wallpaper.adapter.PhotosAdapter;
import id.simpus.wallpaper.ui.collection.model.Collection;
import id.simpus.wallpaper.ui.photos.model.Photo;
import id.simpus.wallpaper.utils.GlideApp;
import id.simpus.wallpaper.web_services.ApiInterfaces;
import id.simpus.wallpaper.web_services.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionsFragment extends Fragment   {

    private final String TAG = CollectionsFragment.class.getSimpleName();

    @BindView(R.id.fragment_collections_username)
    TextView username;

    @BindView(R.id.fragment_collections_description)
    TextView description;

    @BindView(R.id.fragment_collections_tittle)
    TextView tittle;

    @BindView(R.id.fragment_collections_user_avatar)
    CircleImageView userAvatar;

    @BindView(R.id.fragment_collections_ProgressBar)
    ProgressBar progressBar;

    @BindView(R.id.rv_fragment_collections)
    RecyclerView recyclerView;

    private List<Photo> photos = new ArrayList<>();
    private PhotosAdapter photosAdapter;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collections, container, false);
        unbinder = ButterKnife.bind(this, view);

        //RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        photosAdapter = new PhotosAdapter(getActivity(), photos);
        recyclerView.setAdapter(photosAdapter);

        Bundle bundle = getArguments();
        int collectionId = bundle.getInt("collectionId");

        showProgressBar(true);

        getInformationOfCollections(collectionId);
        getPhotosOfCollections(collectionId);
        return view;
    }

    private void  getInformationOfCollections (int collectionId){
        ApiInterfaces apiInterfaces = ServiceGenerator.createService(ApiInterfaces.class);
        Call<Collection> call = apiInterfaces.getInformationOfCollection(collectionId);
        call.enqueue(new Callback<Collection>() {
            @Override
            public void onResponse(Call<Collection> call, Response<Collection> response) {
                if (response.isSuccessful()){
                    Collection collection = response.body();
                    tittle.setText(collection.getTittle());
                    description.setText(collection.getDescription());
                    username.setText(collection.getUser().getUsername());
                    GlideApp.with(getActivity())
                            .load(collection.getUser().getProfilImage().getSmall())
                            .into(userAvatar);
                } else {
                    Log.d(TAG, "fail" +response.message());
                }
                showProgressBar(false);
            }

            @Override
            public void onFailure(Call<Collection> call, Throwable t) {
                Log.d(TAG, "fail" +t.getMessage());
                showProgressBar(false);
            }
        });
    }

    private void getPhotosOfCollections (int collectionId){
        ApiInterfaces apiInterfaces = ServiceGenerator.createService(ApiInterfaces.class);
        Call<List<Photo>> call = apiInterfaces.getPhotosOfCollection(collectionId);
        call.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                if (response.isSuccessful()){
                    photos.addAll(response.body());
                    photosAdapter.notifyDataSetChanged();

                } else {
                    Log.d(TAG, "fail" +response.message());
                }
                showProgressBar(false);
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Log.d(TAG, "fail" +t.getMessage());
                showProgressBar(false);
            }
        });

    }

    private void showProgressBar(boolean isShow) {
        if (isShow){
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
