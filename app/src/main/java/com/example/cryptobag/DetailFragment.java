package com.example.cryptobag;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.cryptobag.Entities.Coin;

import java.util.LinkedList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {



    // TODO: Rename and change types of parameters

    private TextView Name;
    private TextView Symbol;
    private TextView ValueUSD;
    private TextView Change1h;
    private TextView Change24h;
    private TextView Change7d;
    private TextView Marketcap;
    private TextView Volume24h;
    private final LinkedList<Coin> Coins = new LinkedList<>();

    private OnFragmentInteractionListener mListener;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        String message = bundle.getString(MainActivity.EXTRA_MESSAGE);


        Log.d("detail fragment", "message = " + message);
        Name = getView().findViewById(R.id.Name);
        Symbol = getView().findViewById(R.id.Symbol);
        ValueUSD = getView().findViewById(R.id.ValueUSD);
        Change1h = getView().findViewById(R.id.Change1h);
        Change24h = getView().findViewById(R.id.Change24h);
        Change7d = getView().findViewById(R.id.Change7d);
        Marketcap = getView().findViewById(R.id.Marketcap);
        Volume24h = getView().findViewById(R.id.Volume24h);
        new CoinQueryTask().execute();
        int messageint = Integer.parseInt(message);
        Log.d("detail fragment", "message  = " + messageint);


    }


public class CoinQueryTask extends AsyncTask<Void, Void, Coin> {


    @Override
    protected Coin doInBackground(Void... voids) {

        String id = getArguments().getString(MainActivity.EXTRA_MESSAGE);
        Coin targetCoin = MainActivity.coinDb.coinDao().getCoinById(id);
        return targetCoin;
    }

    @Override
    protected void onPostExecute(Coin mycoin) {
super.onPostExecute(mycoin);

if(mycoin != null) {

    Name.setText(mycoin.getName());
    Symbol.setText(mycoin.getSymbol());
    ValueUSD.setText(mycoin.getPriceUsd());
    Change1h.setText(mycoin.getPercentChange1h());
    Change24h.setText(mycoin.getPercentChange24h());
    Change7d.setText(mycoin.getPercentChange7d());
    Marketcap.setText(mycoin.getMarketCapUsd());
    Volume24h.setText(Double.toString(mycoin.getVolume24()));
} else {


}

    }
}



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
