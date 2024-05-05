package ru.syeysk.election;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ActivityCampaignList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_activity_select_campaign);
        setContentView(R.layout.activity_campaign_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Активируем кнопку возврата
        //ActionBar ab = getSupportActionBar();
        //assert ab != null;
        //ab.setDisplayHomeAsUpEnabled(true);

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        RecyclerView recview = findViewById(R.id.campaign_list);
        recview.setLayoutManager(new LinearLayoutManager(this));
        recview.setAdapter(new AdapterListItemCampaign(getApplicationContext()));
    }

    public void onOpenStreetListBtn(View view) {
        TextView campaign_id = (TextView)view.findViewById(R.id.text_campaign_id);

        Intent intent = new Intent(this, ActivityStreetList.class);
        intent.putExtra("campaign_id", campaign_id.getText().toString());
        startActivity(intent);
    }

    public void onOpenCampaignAddBtn(View view) {
        Intent intent = new Intent(this, ActivityCampaignAdd.class);
        startActivity(intent);
    }

}