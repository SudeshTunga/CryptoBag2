package com.example.cryptobag;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.cryptobag.Entities.Coin;
import com.example.cryptobag.Entities.CoinDatabase;
import com.example.cryptobag.Entities.CoinLoreResponse;
import com.example.cryptobag.Entities.CoinService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements CoinListAdapter.OnCoinListener {
public static final String EXTRA_MESSAGE = "com.example.cryptobag.MESSAGE";
    private RecyclerView mRecyclerView;
    private CoinListAdapter mAdapter;
    public static CoinDatabase coinDb;
    private static List<com.example.cryptobag.Entities.Coin> coinList = new ArrayList<>();
private static final String TAG = "MainActivity";
boolean mIsDualPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.d(TAG, "OnCreate: Starting Launch");

        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerview);

        Log.d(TAG, "Get a handle to the RecyclerView done");

        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        coinDb = Room.databaseBuilder(getApplicationContext(), CoinDatabase.class, "coinDb")
                .build();

        new NetworkAssignment().execute();
        new UpdateAdapterTask().execute();

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

    public class NetworkAssignment extends AsyncTask<Void, Integer, CoinLoreResponse>{


        @Override
        protected CoinLoreResponse doInBackground(Void... voids) {

            coinDb.coinDao().deleteAllCoins();

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("https://api.coinlore.net/api/")
                    .addConverterFactory(GsonConverterFactory.create());

            Retrofit retrofit = builder.build();

            CoinService service = retrofit.create(CoinService.class);
            Call<CoinLoreResponse> call = service.get100Coins();

            CoinLoreResponse coinList = null;


            try {
                Response<CoinLoreResponse> coinResponse = call.execute();
                coinList = coinResponse.body();
            } catch (IOException e) {

                e.printStackTrace();
            }

            Coin[] coinArray = coinList.getData().toArray(new Coin[coinList.getData().size()]);
            coinDb.coinDao().insertCoins(coinArray);

            return coinList;
        }

        @Override
        protected void onPostExecute (CoinLoreResponse coinLoreResponse) {
            super.onPostExecute(coinLoreResponse);
            if(coinLoreResponse != null) {
               // setCoins(coinLoreResponse.getData());
            } else {


            }
        }
    }

  public class UpdateAdapterTask extends AsyncTask<Void, Void, List<Coin>>{


      @Override
      protected List<Coin> doInBackground(Void... voids) {

          List<Coin> allCoins = coinDb.coinDao().getCoins();


          coinList = allCoins;

          Log.d(TAG, "ALL COINS LIST:" + allCoins);
          return allCoins;
      }


      @Override
      protected void onPostExecute(List<Coin> coins) {
          super.onPostExecute(coins);

          if(coins != null) {

              setCoins(coins);
          } else {


          }

      }
  }


    @Override
    public void onCoinClick(int position) {

        Log.d(TAG, "onCoinClick: clicked");
        int symbol = position;
        String id = coinList.get(position).getId();
        Log.d(TAG, "clicked Coin id: " + id);

        if (mIsDualPane == false) {

        phoneView(id);

        }

        else {

            ipadView(id);

        }

    }

    public void phoneView (String id) {

        Intent intent = new Intent (this, DetailActivity.class);

        intent.putExtra(EXTRA_MESSAGE, id);
        startActivity(intent);

    }



    public void ipadView (String id) {

        DetailFragment fragment = new DetailFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (fragment == null) {


            transaction.add(R.id.detail_container, fragment);
        } else {

            transaction.replace(R.id.detail_container,fragment);

        }

        transaction.commit();

        String convertsymbol = id;

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
