package com.mml.movies.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Stats {
    @PrimaryKey
    public int id;

    public boolean bookmarked;
    public boolean trending;
    public boolean active;

    public Stats(int id, boolean bookmarked, boolean trending, boolean active){
        this.id = id;
        this.bookmarked = bookmarked;
        this.trending = trending;
        this.active = active;
    }

}