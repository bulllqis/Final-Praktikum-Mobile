package com.example.final_praktikum_mobile.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.final_praktikum_mobile.Model.MovieModel;
import com.example.final_praktikum_mobile.Model.TvShowsModel;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createMoviesTableQuery = "CREATE TABLE movies (" +
                "id INTEGER PRIMARY KEY," +
                "title TEXT," +
                "backdrop_image TEXT," +
                "poster_image TEXT," +
                "release_date TEXT," +
                "rating REAL," +
                "synopsis TEXT," +
                "language TEXT," +
                "popularity REAL" +
                ")";
        db.execSQL(createMoviesTableQuery);

        String createTvShowsTableQuery = "CREATE TABLE tvShows (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT," +
                "backdrop_image TEXT," +
                "poster_image TEXT," +
                "first_air_date TEXT," +
                "rating REAL," +
                "synopsis TEXT," +
                "language TEXT," +
                "popularity REAL" +
                ")";
        db.execSQL(createTvShowsTableQuery);

        String createFavoritesTable = "CREATE TABLE favorites (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "movie_tv_id INTEGER," +
                "type TEXT," +
                "title TEXT," +
                "backdrop_image TEXT," +
                "poster_image TEXT," +
                "release_date TEXT," +
                "rating REAL," +
                "synopsis TEXT," +
                "language TEXT," +
                "popularity TEXT" +
                ")";
        db.execSQL(createFavoritesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS movies");
        db.execSQL("DROP TABLE IF EXISTS tvShows");
        db.execSQL("DROP TABLE IF EXISTS favorites");
        onCreate(db);
    }

    private ContentValues getContentValuesForMovie(MovieModel movie){
        ContentValues values = new ContentValues();
        values.put("id", movie.getId());
        values.put("title", movie.getTitle());
        values.put("backdrop_image", movie.getBackdrop_image());
        values.put("poster_image", movie.getPoster_image());
        values.put("release_date", movie.getRelease_date());
        values.put("rating", movie.getRating());
        values.put("synopsis", movie.getSynopsis());
        values.put("language", movie.getLanguage());
        values.put("popularity", movie.getPopularity().doubleValue());
        return values;

    }

    private ContentValues getContentValuesForTvShow(TvShowsModel tvShow){
        ContentValues values = new ContentValues();
        values.put("id", tvShow.getId());
        values.put("name", tvShow.getName());
        values.put("backdrop_image", tvShow.getBackdrop_image());
        values.put("poster_image", tvShow.getPoster_image());
        values.put("first_air_date", tvShow.getFirst_air_date());
        values.put("rating", tvShow.getRating());
        values.put("synopsis", tvShow.getSynopsis());
        values.put("language", tvShow.getLanguage());
        values.put("popularity", tvShow.getPopularity().doubleValue());
        return  values;
    }

    private ContentValues getContentValuesForFavoritesMovie(MovieModel movie){
        ContentValues values = new ContentValues();

        values.put("movie_tv_id", movie.getId());
        values.put("type", "movie");
        values.put("title", movie.getTitle());
        values.put("backdrop_image", movie.getBackdrop_image());
        values.put("poster_image", movie.getPoster_image());
        values.put("release_date", movie.getRelease_date());
        values.put("rating", movie.getRating());
        values.put("synopsis", movie.getSynopsis());
        values.put("language", movie.getLanguage());
        values.put("popularity", movie.getPopularity().doubleValue());

        return  values;
    }

    private ContentValues getContentValuesForFavoritesTvShow(TvShowsModel tvShow){
        ContentValues values = new ContentValues();

        values.put("movie_tv_id", tvShow.getId());
        values.put("type", "tvshow");
        values.put("title", tvShow.getName());
        values.put("backdrop_image", tvShow.getBackdrop_image());
        values.put("poster_image", tvShow.getPoster_image());
        values.put("release_date", tvShow.getFirst_air_date());
        values.put("rating", tvShow.getRating());
        values.put("synopsis", tvShow.getSynopsis());
        values.put("language", tvShow.getLanguage());
        values.put("popularity", tvShow.getPopularity().doubleValue());

        return  values;
    }

    private MovieModel parseMovieData(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        String backdropImage = cursor.getString(cursor.getColumnIndexOrThrow("backdrop_image"));
        String posterImage = cursor.getString(cursor.getColumnIndexOrThrow("poster_image"));
        String releaseDate = cursor.getString(cursor.getColumnIndexOrThrow("release_date"));
        float rating = cursor.getFloat(cursor.getColumnIndexOrThrow("rating"));
        String synopsis = cursor.getString(cursor.getColumnIndexOrThrow("synopsis"));
        String language = cursor.getString(cursor.getColumnIndexOrThrow("language"));
        double popularity = cursor.getDouble(cursor.getColumnIndexOrThrow("popularity"));

        MovieModel movie = new MovieModel(id, title, backdropImage, posterImage, releaseDate, rating, synopsis, language, popularity);

        return movie;
    }

    private TvShowsModel parseTvShowData(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String backdropImage = cursor.getString(cursor.getColumnIndexOrThrow("backdrop_image"));
        String posterImage = cursor.getString(cursor.getColumnIndexOrThrow("poster_image"));
        String firstAirDate = cursor.getString(cursor.getColumnIndexOrThrow("first_air_date"));
        float rating = cursor.getFloat(cursor.getColumnIndexOrThrow("rating"));
        String synopsis = cursor.getString(cursor.getColumnIndexOrThrow("synopsis"));
        String language = cursor.getString(cursor.getColumnIndexOrThrow("language"));
        double popularity = cursor.getDouble(cursor.getColumnIndexOrThrow("popularity"));

        TvShowsModel tvShow = new TvShowsModel(id, name, backdropImage, posterImage, firstAirDate, rating, synopsis, language, popularity);
        return  tvShow;
    }

    private Object createModelFromCursor(Cursor cursor) {
        String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("movie_tv_id"));
        String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
        String backdropImage = cursor.getString(cursor.getColumnIndexOrThrow("backdrop_image"));
        String posterImage = cursor.getString(cursor.getColumnIndexOrThrow("poster_image"));
        String releaseDate = cursor.getString(cursor.getColumnIndexOrThrow("release_date"));
        float rating = cursor.getFloat(cursor.getColumnIndexOrThrow("rating"));
        String synopsis = cursor.getString(cursor.getColumnIndexOrThrow("synopsis"));
        String language = cursor.getString(cursor.getColumnIndexOrThrow("language"));
        double popularity = cursor.getDouble(cursor.getColumnIndexOrThrow("popularity"));

        if (type.equals("movie")) {
            return new MovieModel(id, title, backdropImage, posterImage, releaseDate, rating, synopsis, language, popularity);
        } else if (type.equals("tvshow")) {
            return new TvShowsModel(id, title, backdropImage, posterImage, releaseDate, rating, synopsis, language, popularity);
        }

        return null;
    }


    public void insertMovie(MovieModel movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM movies WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(movie.getId())});
        if (cursor.getCount() == 0) {

            ContentValues values = getContentValuesForMovie(movie);
            db.insert("movies", null, values);
        }
        cursor.close();
        db.close();
    }

    public void insertTvShow(TvShowsModel tvShow) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT id FROM tvShows WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(tvShow.getId())});
        if (cursor.getCount() == 0) {
            ContentValues values = getContentValuesForTvShow(tvShow);
            db.insert("tvShows", null, values);
        }
        cursor.close();
        db.close();
    }

    public void addMovieToFavorites(MovieModel movie) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = getContentValuesForFavoritesMovie(movie);

        db.insert("favorites", null, values);
        db.close();
    }

    public void addTvShowToFavorites(TvShowsModel tvShow) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = getContentValuesForFavoritesTvShow(tvShow);

        db.insert("favorites", null, values);
        db.close();
    }

    public MovieModel getMovie(int movieId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM movies WHERE id = ?", new String[]{String.valueOf(movieId)});
        if (cursor != null && cursor.moveToFirst()) {
            MovieModel movie = parseMovieData(cursor);
            cursor.close();
            return movie;
        }
        return null;
    }
    public TvShowsModel getTvShow(int tvShowId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tvShows WHERE id = ?", new String[]{String.valueOf(tvShowId)});
        if (cursor != null && cursor.moveToFirst()) {
            TvShowsModel tvShow = parseTvShowData(cursor);

            cursor.close();
            return tvShow;
        }
        return null;
    }

    public List<Object> getFavorites() {
        List<Object> favoritesList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("favorites", null, null, null, null, null, "id ASC");

        while (cursor.moveToNext()) {
            Object model = createModelFromCursor(cursor);
            if (model != null) {
                favoritesList.add(model);
            }
        }

        cursor.close();
        db.close();
        return favoritesList;
    }

    public void removeFromFavoritesById(int favoriteId) {
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {String.valueOf(favoriteId)};
        db.delete("favorites", "movie_tv_id = ?", args);
        db.close();
    }

    public boolean isFavorite(int movieId, int tvShowId) {
        SQLiteDatabase db = getReadableDatabase();
        String[] args = {String.valueOf(movieId), String.valueOf(tvShowId)};
        String query = "SELECT * FROM favorites WHERE movie_tv_id IN (?, ?)";
        Cursor cursor = db.rawQuery(query, args);
        boolean isFavorite = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isFavorite;
    }

    public List<MovieModel> searchMoviesByTitle(String title) {
        List<MovieModel> moviesList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM movies WHERE title LIKE ?", new String[]{title + "%"});
        while (cursor.moveToNext()) {
            MovieModel movie = parseMovieData(cursor);
            moviesList.add(movie);
        }

        cursor.close();
        db.close();
        return moviesList;
    }

    public List<TvShowsModel> searchTvShowByName(String name) {
        List<TvShowsModel> tvShowList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tvShows WHERE name LIKE ?", new String[]{name + "%"});
        while (cursor.moveToNext()) {
            TvShowsModel tvShow = parseTvShowData(cursor);
            tvShowList.add(tvShow);
        }
        cursor.close();
        db.close();
        return tvShowList;
    }

    public List<Object> searchFavoritesByTitle(String title) {
        List<Object> favoritesList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM favorites WHERE title LIKE ?";
        Cursor cursor = db.rawQuery(query, new String[]{title + "%"});

        while (cursor.moveToNext()) {
            Object model = createModelFromCursor(cursor);
            if (model != null) {
                favoritesList.add(model);
            }
        }

        cursor.close();
        db.close();
        return favoritesList;
    }

}



