package com.example.cryptobag.Entities;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface CoinDao {
    @Query("SELECT * FROM coin")
    List<Coin> getCoins();

    @Insert()
    void insertCoins(Coin... coins);

    @Query("SELECT * FROM coin WHERE id = :id")
    Coin getCoinById(String id);

    @Query("DELETE FROM coin")
    void deleteAllCoins();

}

