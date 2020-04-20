package id.simpus.wallpaper.ui.collection;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import butterknife.BindView;
import butterknife.OnItemClick;
import butterknife.Unbinder;
import butterknife.ButterKnife;
import id.simpus.wallpaper.R;
import id.simpus.wallpaper.adapter.CollectionAdapter;
import id.simpus.wallpaper.ui.collection.model.Collection;
import id.simpus.wallpaper.ui.collections.CollectionsFragment;
import id.simpus.wallpaper.utils.Functions;
import id.simpus.wallpaper.web_services.ApiInterfaces;
import id.simpus.wallpaper.web_services.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionFragment extends Fragment {

    private final String TAG = CollectionFragment.class.getSimpleName();
    @BindView(R.id.fragment_collection_GridView)
    GridView gridView;
    @BindView(R.id.fragment_collection_ProgressBar)
    ProgressBar progressBar;

    private CollectionAdapter collectionAdapter;
    private List<Collection> collections = new ArrayList<>();
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        unbinder = ButterKnife.bind(this, view);
        collectionAdapter = new CollectionAdapter(getActivity(), collections);
        gridView.setAdapter(collectionAdapter);

        showProgressBar(true);
        getCollections();
        return view;
    }

    private void showProgressBar(boolean isShow) {
        if (isShow){
            progressBar.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
        }else {
            progressBar.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);
        }
    }

    @OnItemClick(R.id.fragment_collection_GridView)
    public void onItemClick(int position){
        Collection collection = collections.get(position);
        Log.d(TAG, collection.getId() +"");
        Bundle bundle = new Bundle();
        bundle.putInt("collectionId", collection.getId());
        CollectionsFragment collectionsFragment = new CollectionsFragment();
        collectionsFragment.setArguments(bundle);
        Functions.changeMainFragmentWithBack(getActivity(), collectionsFragment);
    }

    private void getCollections() {
        ApiInterfaces apiInterfaces = ServiceGenerator.createService(ApiInterfaces.class);
        Call<List<Collection>> call = apiInterfaces.getCollection();
        call.enqueue(new Callback<List<Collection>>() {
            @Override
            public void onResponse(Call<List<Collection>> call, Response<List<Collection>> response) {
                if (response.isSuccessful()){
                    collections.addAll(response.body());
                    collectionAdapter.notifyDataSetChanged();
                }else {
                    Log.e(TAG, "fail " + response.message());
                }
                showProgressBar(false);
            }

            @Override
            public void onFailure(Call<List<Collection>> call, Throwable t) {
                Log.e(TAG, "fail " + t.getMessage());
                showProgressBar(false);

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}