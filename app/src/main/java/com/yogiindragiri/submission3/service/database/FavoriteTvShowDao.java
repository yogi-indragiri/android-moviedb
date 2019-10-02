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
public interface FavoriteTvShowDao {
    @Query("SELECT * FROM favoritetvshowtable")
    LiveData<List<FavoriteTvShowEntry>> loadAllFavorite();

    @Query("SELECT * FROM favoritetvshowtable WHERE name = :name")
    List<FavoriteTvShowEntry> loadAll(String name);

    @Insert
    void insertTvShowFavorite(FavoriteTvShowEntry favoriteEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTvShowFavorite(FavoriteTvShowEntry favoriteEntry);

    @Delete
    void deleteTvShowFavorite(FavoriteTvShowEntry favoriteEntry);

    @Query("DELETE FROM favoritetvshowtable WHERE tvshowid = :movie_id")
    void deleteTvShowFavoriteWithId(int movie_id);

    @Query("SELECT * FROM favoritetvshowtable WHERE id = :id")
    LiveData<FavoriteTvShowEntry> loadFavoriteById(int id);
}
