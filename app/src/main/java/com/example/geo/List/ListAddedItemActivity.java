package com.example.geo.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.example.geo.R;

import java.util.ArrayList;

public class ListAddedItemActivity extends AppCompatActivity {

    private AppCompatActivity activity = ListAddedItemActivity.this;
    Context context = ListAddedItemActivity.this;
    private RecyclerView recyclerViewTrack;
    private ArrayList<Trackable> listTrackables;
    private RecyclerAdapter recyclerAdapter;
    private DBHelper dbHelper;
    SearchView searchBox;
    private ArrayList<Trackable> filteredList;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_added_item);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
        initObjects();

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("NAME")) {

            //get all needed extras intent

            int id = getIntent().getExtras().getInt("ID");
            String email = getIntent().getExtras().getString("Description");
            String address = getIntent().getExtras().getString("Website Url");
            String country = getIntent().getExtras().getString("Category");



        }else{

            Toast.makeText(this, "No API Data", Toast.LENGTH_SHORT).show();

        }

    }



    /**
     * This method is to initialize views
     */
    private void initViews() {
        recyclerViewTrack = (RecyclerView) findViewById(R.id.recyclerViewTrack);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listTrackables = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter(listTrackables, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewTrack.setLayoutManager(mLayoutManager);
        recyclerViewTrack.setItemAnimator(new DefaultItemAnimator());
        recyclerViewTrack.setHasFixedSize(true);
        recyclerViewTrack.setAdapter(recyclerAdapter);
        dbHelper = new DBHelper(activity);

        getDataFromSQLite();

    }





    /**
     * This method is to fetch all user records from SQLite
     */
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listTrackables.clear();
                listTrackables.addAll(dbHelper. getAllItem());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                recyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}
