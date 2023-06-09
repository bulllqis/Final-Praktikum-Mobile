package com.example.final_praktikum_mobile.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.final_praktikum_mobile.Adapter.FavoritesAdapter;
import com.example.final_praktikum_mobile.Database.DBHelper;
import com.example.final_praktikum_mobile.DetailActivity;
import com.example.final_praktikum_mobile.MainActivity;
import com.example.final_praktikum_mobile.R;

import java.util.List;

public class FavoritesFragment extends Fragment {
    private RecyclerView rv_fav;
    private TextView tv_noFav;
    private LinearLayoutManager layoutManager;
    private SearchView searchView;
    FavoritesAdapter favoritesAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv_fav = view.findViewById(R.id.rv_favorites);
        tv_noFav = view.findViewById(R.id.tv_noFav);
        searchView = view.findViewById(R.id.searchView);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Favorites");

        rv_fav.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());

        showFavorite();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                DBHelper dbHelper = new DBHelper(getContext());
                if (query.isEmpty()) {
                    showFavorite();

                } else {
                    List<Object> searchFav = dbHelper.searchFavoritesByTitle(query);

                    favoritesAdapter = new FavoritesAdapter(getActivity(), searchFav, resultLauncher);
                    rv_fav.setLayoutManager(layoutManager);
                    rv_fav.setAdapter(favoritesAdapter);

                }
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        showFavorite();
    }

    private void showFavorite() {
        DBHelper dbHelper = new DBHelper(getContext());

        List<Object> favoritesList = dbHelper.getFavorites();

        if (!favoritesList.isEmpty()){
            favoritesAdapter = new FavoritesAdapter(getContext(), favoritesList, resultLauncher);
            rv_fav.setLayoutManager(layoutManager);
            rv_fav.setAdapter(favoritesAdapter);
            tv_noFav.setVisibility(View.GONE);
        }else {
            searchView.setVisibility(View.GONE);
            rv_fav.setVisibility(View.GONE);
            tv_noFav.setVisibility(View.VISIBLE);
        }
    }

    private final ActivityResultLauncher<Intent> resultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getData() != null) {
                            if (result.getResultCode() == DetailActivity.RESULT_OK){
                                showFavorite();

                            }
                        }
                    });
}