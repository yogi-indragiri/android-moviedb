package com.yogiindragiri.submission3.service.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "favoritetvshowtable")
public class FavoriteTvShowEntry {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private Integer id;

    @ColumnInfo(name = "tvshowid")
    private Integer tvshowid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "originalName")
    private String originalName;

    @ColumnInfo(name = "popularity")
    private Float popularity;

    @ColumnInfo(name = "voteCount")
    private Integer voteCount;

    @ColumnInfo(name = "firstAirDate")
    private String firstAirDate;

    @ColumnInfo(name = "backdropPath")
    private String backdropPath;

    @ColumnInfo(name = "originalLanguage")
    private String originalLanguage;

    @ColumnInfo(name = "voteAverage")
    private Float voteAverage;

    @ColumnInfo(name = "overview")
    private String overview;

    @ColumnInfo(name = "posterPath")
    private String posterPath;

    public FavoriteTvShowEntry(Integer id, Integer tvshowid, String name, String originalName, Float popularity, Integer voteCount, String firstAirDate, String backdropPath, String originalLanguage, Float voteAverage, String overview, String posterPath) {
        this.id = id;
        this.tvshowid = tvshowid;
        this.name = name;
        this.originalName = originalName;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.firstAirDate = firstAirDate;
        this.backdropPath = backdropPath;
        this.originalLanguage = originalLanguage;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.posterPath = posterPath;
    }

    @Ignore
    public FavoriteTvShowEntry(Integer tvshowid, String name, String originalName, Float popularity, Integer voteCount, String firstAirDate, String backdropPath, String originalLanguage, Float voteAverage, String overview, String posterPath) {
        this.tvshowid = tvshowid;
        this.name = name;
        this.originalName = originalName;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.firstAirDate = firstAirDate;
        this.backdropPath = backdropPath;
        this.originalLanguage = originalLanguage;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.posterPath = posterPath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTvshowid() {
        return tvshowid;
    }

    public void setTvshowid(Integer tvshowid) {
        this.tvshowid = tvshowid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public Float getPopularity() {
        return popularity;
    }

    public void setPopularity(Float popularity) {
        this.popularity = popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
