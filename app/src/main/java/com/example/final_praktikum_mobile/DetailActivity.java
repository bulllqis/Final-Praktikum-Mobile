package com.example.final_praktikum_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.final_praktikum_mobile.Database.DBHelper;
import com.example.final_praktikum_mobile.Model.MovieModel;
import com.example.final_praktikum_mobile.Model.TvShowsModel;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_TVSHOWS = "extra_tvshows";
    private TextView tv_title, tv_releaseDate, tv_rating, tv_synopsis, tv_popularity, tv_language;
    private ImageView iv_poster, iv_backdrop ,iv_movieOrTv;
    private ImageButton btn_back, btn_fav;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tv_title = findViewById(R.id.tv_title);
        tv_releaseDate = findViewById(R.id.tv_releaseDate);
        tv_rating = findViewById(R.id.tv_rating);
        tv_synopsis = findViewById(R.id.tv_synopsis);
        iv_poster = findViewById(R.id.iv_poster);
        iv_backdrop = findViewById(R.id.iv_backdrop);
        iv_movieOrTv = findViewById(R.id.iv_movieOrTv);
        tv_popularity = findViewById(R.id.tv_popularity);
        tv_language = findViewById(R.id.tv_language);
        btn_back = findViewById(R.id.btn_back);
        btn_fav =findViewById(R.id.btn_fav);

        btn_back.setOnClickListener(view -> finish());



        DBHelper dbHelper = new DBHelper(getApplicationContext());
        int movie_id = getIntent().getIntExtra(EXTRA_MOVIE, 0);
        int tvShows_id = getIntent().getIntExtra(EXTRA_TVSHOWS, 0);

        if (movie_id != 0){
            MovieModel singleMovie = dbHelper.getMovie(movie_id);
            if (singleMovie != null) {
                tv_title.setText(singleMovie.getTitle());
                tv_releaseDate.setText(singleMovie.getRelease_date());
                tv_rating.setText(String.format("%.1f",singleMovie.getRating()));
                tv_synopsis.setText(singleMovie.getSynopsis());
                tv_popularity.setText("Popularity : " + singleMovie.getPopularity());
                tv_language.setText("Language: " + singleMovie.getLanguage());
                iv_movieOrTv.setImageResource(R.drawable.baseline_movie_24);
                if (singleMovie.getBackdrop_image() != null){
                    Glide.with(DetailActivity.this)
                            .load("https://image.tmdb.org/t/p/w500/" + singleMovie.getBackdrop_image())
                            .apply(new RequestOptions().override(350,
                                    350))
                            .into(iv_backdrop);
                }

                Glide.with(DetailActivity.this)
                        .load("https://image.tmdb.org/t/p/w500/" + singleMovie.getPoster_image())
                        .apply(new RequestOptions().override(350,
                                350))
                        .into(iv_poster);
            }
        }

        if (tvShows_id != 0){
            TvShowsModel singleTvShows = dbHelper.getTvShow(tvShows_id);
            if (singleTvShows != null){
                tv_title.setText(singleTvShows.getName());
                tv_releaseDate.setText(singleTvShows.getFirst_air_date());
                tv_rating.setText(String.format("%.1f",singleTvShows.getRating()));
                tv_synopsis.setText(singleTvShows.getSynopsis());
                tv_popularity.setText("Popularity : " + singleTvShows.getPopularity());
                tv_language.setText("Language: " + singleTvShows.getLanguage());
                iv_movieOrTv.setImageResource(R.drawable.baseline_tv_24);
                if (singleTvShows.getBackdrop_image() != null){
                    Glide.with(DetailActivity.this)
                            .load("https://image.tmdb.org/t/p/w500/" + singleTvShows.getBackdrop_image())
                            .apply(new RequestOptions().override(350,
                                    350))
                            .into(iv_backdrop);
                }
                Glide.with(DetailActivity.this)
                        .load("https://image.tmdb.org/t/p/w500/" + singleTvShows.getPoster_image())
                        .apply(new RequestOptions().override(350,
                                350))
                        .into(iv_poster);

            }

        }


    }
}