package ru.syeysk.election;

import android.content.ContentValues;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;

import ru.syeysk.election.ElectionContract.CampaignEntry;
import ru.syeysk.election.utils.DataBaseUtils;

public class ActivityCampaignAdd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_add);
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
    }

    public void onSaveBtn(View view) {
        ElectionDbHelper MydbHelper = new ElectionDbHelper(getApplicationContext());
        SQLiteDatabase db = MydbHelper.getReadableDatabase();

        EditText name = (EditText)findViewById(R.id.campaign_name);
        EditText fio_candidate = (EditText)findViewById(R.id.campaign_fio);
        EditText region = (EditText)findViewById(R.id.campaign_region);
        EditText rayon = (EditText)findViewById(R.id.campaign_rayon);
        EditText settlement = (EditText)findViewById(R.id.campaign_settlement);

        ContentValues values = new ContentValues();
        values.put(CampaignEntry.COLUMN_UUID, DataBaseUtils.getFieldUUID_value());
        values.put(CampaignEntry.COLUMN_NAME, name.getText().toString().replace("\n", ""));
        values.put(CampaignEntry.COLUMN_FIO_CANDIDATE, fio_candidate.getText().toString().replace("\n", ""));
        values.put(CampaignEntry.COLUMN_REGION, region.getText().toString().replace("\n", ""));
        values.put(CampaignEntry.COLUMN_RAYON, rayon.getText().toString().replace("\n", ""));
        values.put(CampaignEntry.COLUMN_SETTLEMENT, settlement.getText().toString().replace("\n", ""));
        values.put(CampaignEntry.COLUMN_CREATED_ON_DEVICE_ID, "1");
        long newRowId = db.insert(CampaignEntry.TABLE_NAME, null, values);

        onBackPressed();
    }
}