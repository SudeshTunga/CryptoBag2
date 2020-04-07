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
import com.example.cryptobag.Entities.CoinService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements CoinListAdapter.OnCoinListener {
public static final String EXTRA_MESSAGE = "com.example.cryptobag.MESSAGE";
    private RecyclerView mRecyclerView;
    private CoinListAdapter mAdapter;
///

   // static CoinLoreResponse mycoinlist =  new Gson().fromJson(CoinLoreResponse.queryResult, CoinLoreResponse.class);
   // static List<com.example.cryptobag.Entities.Coin> mycoins = mycoinlist.getData();
    private static List<com.example.cryptobag.Entities.Coin> coinList;
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

        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        // prepare Retrofit
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.coinlore.net/api/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        CoinService service = retrofit.create(CoinService.class);
        Call<CoinLoreResponse> call = service.get100Coins();
        Log.d(TAG, "yeetness everdeen"+ call);

        Log.d(TAG, "Give the RecyclerView a default layout manager done");

        mAdapter = new CoinListAdapter(this, coinList, this);

        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);

        Log.d(TAG, "Connect the adapter with the RecyclerView done");


        //execute call asynchronously using enqueue

        call.enqueue(new Callback<CoinLoreResponse>() {
            @Override
            public void onResponse(Call<CoinLoreResponse> call, Response<CoinLoreResponse> response) {
                // create CoinLoreResponse to capture api call response

                CoinLoreResponse coinResponse = response.body();
                List<com.example.cryptobag.Entities.Coin> myCoins = coinResponse.getData();

                setCoins(myCoins);
            }

            @Override
            public void onFailure(Call<CoinLoreResponse> call, Throwable t) {
                String failMsg = "Could not connect to CoinLore API";
            }
        });


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

        com.example.cryptobag.Entities.Coin targetCoin = coinList.get(position);
        Log.d("Main Activity", "targetcoin:" + targetCoin);

        return targetCoin;
    }


    public void setCoins(List<com.example.cryptobag.Entities.Coin> newCoins) {
        coinList = newCoins;
        mAdapter = new CoinListAdapter(this, newCoins, this);
        mRecyclerView.setAdapter(mAdapter);
        Log.d(TAG, "yeet2" + coinList);


    }


    /** Called when the user taps the Send button */

}
