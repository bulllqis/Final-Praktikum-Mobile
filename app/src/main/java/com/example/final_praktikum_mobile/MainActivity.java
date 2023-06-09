package com.example.final_praktikum_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.final_praktikum_mobile.Fragment.FavoritesFragment;
import com.example.final_praktikum_mobile.Fragment.MoviesFragment;
import com.example.final_praktikum_mobile.Fragment.TvShowsFragment;

public class MainActivity extends AppCompatActivity {
    private ImageButton btn_movies, btn_tvShows, btn_favorites;
    private TextView tv_movies, tv_shows, tv_favorites;
    private MoviesFragment moviesFragment;
    private TvShowsFragment tvShowsFragment;
    private FavoritesFragment favoritesFragment;
    private int defaultTextColor;
    private int selectedTextColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_movies = findViewById(R.id.btn_movies);
        btn_tvShows = findViewById(R.id.btn_tvShows);
        btn_favorites = findViewById(R.id.btn_favorites);

        tv_movies = findViewById(R.id.tv_movies);
        tv_shows = findViewById(R.id.tv_shows);
        tv_favorites = findViewById(R.id.tv_favorites);


        moviesFragment = new MoviesFragment();
        tvShowsFragment = new TvShowsFragment();
        favoritesFragment = new FavoritesFragment();

        defaultTextColor = Color.BLACK;
        selectedTextColor = Color.parseColor("#B99DBFE3");

        setButtonState(btn_movies, tv_movies, true);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, moviesFragment, MoviesFragment.class.getSimpleName())
                .commit();

        btn_movies.setOnClickListener(view -> {
            resetButtonStates();
            setButtonState(btn_movies, tv_movies, true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, moviesFragment).commit();
        });

        btn_tvShows.setOnClickListener(view -> {
            resetButtonStates();
            setButtonState(btn_tvShows, tv_shows, true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, tvShowsFragment).commit();
        });

        btn_favorites.setOnClickListener(view -> {
            resetButtonStates();
            setButtonState(btn_favorites, tv_favorites, true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, favoritesFragment).commit();
        });
    }

    private void setButtonState(ImageButton button, TextView textView, boolean isSelected) {
        int backgroundDrawable = isSelected ? R.drawable.bg_button_click : R.drawable.bg_button_default;
        int textColor = isSelected ? selectedTextColor : defaultTextColor;

        button.setBackgroundResource(backgroundDrawable);
        textView.setTextColor(textColor);
    }

    private void resetButtonStates() {
        setButtonState(btn_movies, tv_movies, false);
        setButtonState(btn_tvShows, tv_shows, false);
        setButtonState(btn_favorites, tv_favorites, false);
    }
}
