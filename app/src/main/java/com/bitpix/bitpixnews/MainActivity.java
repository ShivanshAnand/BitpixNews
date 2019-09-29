package com.bitpix.bitpixnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.bitpix.bitpixnews.adapters.HeadlinesListAdapter;
import com.bitpix.bitpixnews.getnews.BpxScrapper;
import com.bitpix.bitpixnews.getnews.StructN18;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements HeadlinesListAdapter.OnItemClickListener {

    private TextView progressIndicator;
    private ArrayList<StructN18> arrayList;
    private RecyclerView recyclerView;
    private HeadlinesListAdapter headlinesListAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private final String INTENT_ID = "URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressIndicator = findViewById(R.id.ma_progress);
        recyclerView = findViewById(R.id.mrcyv);
        layoutManager = new LinearLayoutManager(this);
        BgTask bgTask = new BgTask();
        bgTask.execute();
    }

    public void engine() {
        progressIndicator.setText("Fetched " + arrayList.size() + " articles from News18.com");
        headlinesListAdapter = new HeadlinesListAdapter(this, arrayList, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(headlinesListAdapter);
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(this, FullNews.class);
        intent.putExtra(INTENT_ID, arrayList.get(position).getLink());
        startActivity(intent);
    }

    private class BgTask extends AsyncTask<Void, Integer, ArrayList<StructN18>> {

        @Override
        protected ArrayList<StructN18> doInBackground(Void... voids) {
            BpxScrapper bpxScrapper = new BpxScrapper();
            return bpxScrapper.getHeadlines();
        }

        @Override
        protected void onPostExecute(ArrayList<StructN18> arr) {
            super.onPostExecute(arr);
            arrayList = arr;
            engine();
        }
    }

}
