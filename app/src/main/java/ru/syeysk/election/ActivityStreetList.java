package ru.syeysk.election;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

public class ActivityStreetList extends AppCompatActivity {

    String campaign_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Активируем кнопку возврата
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        campaign_id = getIntent().getStringExtra("campaign_id");

        RecyclerView recview = findViewById(R.id.street_list);
        recview.setLayoutManager(new LinearLayoutManager(this));
        recview.setAdapter(new AdapterListItemStreet(getApplicationContext(), campaign_id));
    }

    public void onOpenHouseListBtn(View view) {
        TextView street_id = (TextView)view.findViewById(R.id.text_street_id);

        Intent intent = new Intent(this, ActivityHouseList.class);

        intent.putExtra("street_id", street_id.getText().toString());

        startActivity(intent);
    }

    public void onOpenStreetAddBtn(View view) {
        Intent intent = new Intent(this, ActivityStreetAdd.class);
        intent.putExtra("campaign_id", campaign_id);
        startActivity(intent);
    }

}