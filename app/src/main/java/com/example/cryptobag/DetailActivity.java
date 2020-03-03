package com.example.cryptobag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    private TextView Name;
    private TextView Symbol;
    private TextView ValueUSD;
    private TextView Change1h;
    private TextView Change24h;
    private TextView Change7d;
    private TextView Marketcap;
    private TextView Volume24h;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Name = findViewById(R.id.Name);
        Symbol = findViewById(R.id.Symbol);
        ValueUSD = findViewById(R.id.ValueUSD);
        Change1h = findViewById(R.id.Change1h);
        Change24h = findViewById(R.id.Change24h);
        Change7d = findViewById(R.id.Change7d);
        Marketcap = findViewById(R.id.Marketcap);
        Volume24h = findViewById(R.id.Volume24h);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String coinSymbol = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);


        final Coin coin = Coin.searchCoin(coinSymbol);

    Name.setText(coin.getName());
        Symbol.setText(coin.getSymbol());
        ValueUSD.setText(Double.toString(coin.getValue()));
        Change1h.setText(Double.toString(coin.getChange1h()));
        Change24h.setText(Double.toString(coin.getChange24h()));
        Change7d.setText(Double.toString(coin.getChange7d()));
        Marketcap.setText(Double.toString(coin.getMarketcap()));
        Volume24h.setText(Double.toString(coin.getVolume()));



    }


}
