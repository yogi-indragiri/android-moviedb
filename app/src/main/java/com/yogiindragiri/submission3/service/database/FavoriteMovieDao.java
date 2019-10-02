package com.yogiindragiri.submission3.service.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FavoriteMovieDao {
    @Query("SELECT * FROM favoritemovietable")
    LiveData<List<FavoriteMovieEntry>> loadAllFavorite();

    @Query("SELECT * FROM favoritemovietable WHERE title = :title")
    List<FavoriteMovieEntry> loadAll(String title);

    @Insert
    void insertMovieFavorite(FavoriteMovieEntry favoriteEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovieFavorite(FavoriteMovieEntry favoriteEntry);

    @Delete
    void deleteMovieFavorite(FavoriteMovieEntry favoriteEntry);

    @Query("DELETE FROM favoritemovietable WHERE movieid = :movie_id")
    void deleteMovieFavoriteWithId(int movie_id);

    @Query("SELECT * FROM favoritemovietable WHERE id = :id")
    LiveData<FavoriteMovieEntry> loadFavoriteById(int id);
}
