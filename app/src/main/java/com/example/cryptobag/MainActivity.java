package com.example.cryptobag;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
public static final String EXTRA_MESSAGE = "com.example.cryptobag.MESSAGE";
    private RecyclerView mRecyclerView;
    private CoinListAdapter mAdapter;
private final LinkedList<Coin> mWordList = new LinkedList<>();
private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       LinkedList<Coin> mycoin = Coin.CreateCoins(mWordList);
        //I could use a Linked List but make each value equal to an object.get() method.
        //Could then set the components in the wordlist_item.xml view with the object.set() method.


        Log.d(TAG, "OnCreate: Starting Launch");

        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerview);

        Log.d(TAG, "Get a handle to the RecyclerView done");

        mAdapter = new CoinListAdapter(this, mWordList);

        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);

        Log.d(TAG, "Connect the adapter with the RecyclerView done");

        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Log.d(TAG, "Give the RecyclerView a default layout manager done");

     //   mRecyclerView.setOnClickListener(new View.OnClickListener() {

      //  public void onClick(View view) {
      //      Log.d(TAG, "Clicked mate2");
      //          sendMessage("BTC");
     //   }

       //     public void sendMessage(String message) {


     //           Intent intent = new Intent(MainActivity.this, DetailActivity.class);
     //           intent.putExtra(EXTRA_MESSAGE, message);
     //           startActivity(intent);

                // Do something in response to button
       //     }

      //  });
    }



    /** Called when the user taps the Send button */

}
