package com.example.cryptobag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptobag.Entities.Coin;

import java.util.List;

public class CoinListAdapter extends RecyclerView.Adapter<CoinListAdapter.CoinViewHolder> {

    private final List<com.example.cryptobag.Entities.Coin> mWordList;
    private OnCoinListener mOnCoinListener;
    private LayoutInflater mInflater;
    private static final String TAG = "CoinListAdapter";
    public static final String EXTRA_MESSAGE = "com.example.cryptobag.MESSAGE";


    static class CoinViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView coinName;
        public final TextView coinPrice;
        public final TextView coinChange1h;
        public final TextView coinSymbol;
        final CoinListAdapter mAdapter;
        OnCoinListener onCoinListener;


        public CoinViewHolder(View itemView, CoinListAdapter adapter, OnCoinListener onCoinListener) {
            super(itemView);
            coinName = itemView.findViewById(R.id.word);
            coinPrice = itemView.findViewById(R.id.Price);
            coinChange1h = itemView.findViewById(R.id.Percentage);
            coinSymbol = itemView.findViewById(R.id.Symbol);
            this.mAdapter = adapter;
            this.onCoinListener = onCoinListener;
            itemView.setOnClickListener(this);

        }



        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();

            onCoinListener.onCoinClick(position);




        }





    }


    public interface OnCoinListener {

        void onCoinClick(int position);
    }

    public CoinListAdapter(Context context,
                           List<com.example.cryptobag.Entities.Coin> wordList, OnCoinListener OnCoinListener ) {
        mInflater = LayoutInflater.from(context);
        this.mWordList = wordList;
        this.mOnCoinListener = OnCoinListener;
    }
    @NonNull
    @Override
    public CoinListAdapter.CoinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mItemView = mInflater.inflate(R.layout.wordlist_item,
                parent, false);
        return new CoinViewHolder(mItemView, this, mOnCoinListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinListAdapter.CoinViewHolder holder, int position) {

        Coin mCurrent = mWordList.get(position);
        holder.coinName.setText(mCurrent.getName());
        holder.coinChange1h.setText(mCurrent.getPercentChange1h() + "%");
        holder.coinPrice.setText( "$" + mCurrent.getPriceUsd());
        holder.coinSymbol.setText(mCurrent.getSymbol());
        holder.coinSymbol.setVisibility(View.INVISIBLE);

    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }


}
