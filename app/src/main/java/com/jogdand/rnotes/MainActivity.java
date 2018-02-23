package com.jogdand.rnotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private FloatingActionButton newNoteFab;
    private RecyclerView noteListRv;
    private RAdapter adapter;
    private Switch archiveSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        addListeners();
        populateViews();
    }

    private void bindViews() {
        newNoteFab = findViewById(R.id.fab_main);
        noteListRv = findViewById(R.id.rv_main);
        noteListRv.setLayoutManager(new LinearLayoutManager(this));
    }

    private void addListeners() {
        newNoteFab.setOnClickListener(this);
    }

    private void populateViews() {
        adapter = new RAdapter();
        noteListRv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        archiveSwitch = menu.getItem(0).getActionView().findViewById(R.id.sw_archived);
        archiveSwitch.setOnCheckedChangeListener(this);
        return true;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab_main) {
            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            intent.setAction("createNote");
            startActivity(intent);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        adapter.setShowArchived(isChecked);
        adapter.loadData();
    }
}
