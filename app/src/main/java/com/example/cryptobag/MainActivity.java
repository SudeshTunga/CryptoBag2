package com.example.cryptobag;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptobag.Entities.CoinLoreResponse;
import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CoinListAdapter.OnCoinListener {
public static final String EXTRA_MESSAGE = "com.example.cryptobag.MESSAGE";
    private RecyclerView mRecyclerView;
    private CoinListAdapter mAdapter;


    static CoinLoreResponse mycoinlist =  new Gson().fromJson(CoinLoreResponse.queryResult, CoinLoreResponse.class);
    static List<com.example.cryptobag.Entities.Coin> mycoins = mycoinlist.getData();
private static final String TAG = "MainActivity";
boolean mIsDualPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      // LinkedList<Coin> mycoin = Coin.CreateCoins(mWordList);
        //I could use a Linked List but make each value equal to an object.get() method.
        //Could then set the components in the wordlist_item.xml view with the object.set() method.


        Log.d(TAG, "OnCreate: Starting Launch");

        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerview);

        Log.d(TAG, "Get a handle to the RecyclerView done");

        mAdapter = new CoinListAdapter(this, mycoins, this);

        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);

        Log.d(TAG, "Connect the adapter with the RecyclerView done");

        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Log.d(TAG, "Give the RecyclerView a default layout manager done");

        View detail_scrollview = findViewById(R.id.detail_container);


        Log.d(TAG, "detail scrollview:" + detail_scrollview);

     //   Context context = itemView.getContext();
    //    Intent intent = new Intent(context, DetailActivity.class);



        String Symbol = null;


        if (detail_scrollview != null && detail_scrollview.getVisibility() == View.VISIBLE) {

            mIsDualPane = true;

        }

        Log.d("Main Activity", "Detail scrollview:" + mIsDualPane);

        if (mIsDualPane == true) {

            DetailFragment fragment = new DetailFragment();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if(fragment == null) {

                transaction.add(R.id.detail_container, fragment);
            }

            else {

                transaction.replace(R.id.detail_container, fragment);

            }

         //   transaction.commit();
         //   Bundle intentBundle = new Bundle();
        //    intentBundle.putString(CoinListAdapter.EXTRA_MESSAGE, Symbol);
         //   fragment.setArguments(intentBundle);



        }


    }

    @Override
    public void onCoinClick(int position) {

        Log.d(TAG, "onCoinClick: clicked");
        int symbol = position;

        if (mIsDualPane == false) {

        phoneView(symbol);

        }

        else {

            ipadView(symbol);

        }

    }

    public void phoneView (int symbol) {

        Intent intent = new Intent (this, DetailActivity.class);
        String message = Integer.toString(symbol);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }



    public void ipadView (int symbol) {

        DetailFragment fragment = new DetailFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (fragment == null) {


            transaction.add(R.id.detail_container, fragment);
        } else {

            transaction.replace(R.id.detail_container,fragment);

        }

        transaction.commit();

        String convertsymbol = Integer.toString(symbol);

        Bundle intentBundle = new Bundle();
        intentBundle.putString(EXTRA_MESSAGE, convertsymbol);
        fragment.setArguments(intentBundle);


    }

  //  public String getSymbol (int position) {
   //     String mysymbol = mycoinlist.get(position).getSymbol();
  //      return mysymbol;
  //  }


    public static com.example.cryptobag.Entities.Coin coinSearch(int position) {

        com.example.cryptobag.Entities.Coin targetCoin = mycoins.get(position);
        Log.d("Main Activity", "targetcoin:" + targetCoin);

        return targetCoin;
    }


    /** Called when the user taps the Send button */

}
