package com.example.cryptobag.Entities;

import retrofit2.Call;
import retrofit2.http.GET;
//here is the api convert it into a list of coins, feed that list of coins into adapter.
public interface CoinService {
    @GET("tickers/")
    Call<CoinLoreResponse> get100Coins();


}
