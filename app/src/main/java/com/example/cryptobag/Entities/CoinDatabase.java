package com.example.cryptobag.Entities;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = Coin.class, version = 1, exportSchema = false)
public abstract class CoinDatabase extends RoomDatabase {
    public abstract CoinDao coinDao();
}