package ru.syeysk.election;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import ru.syeysk.election.utils.DataBaseUtils;

public class ActivityStreetAdd extends AppCompatActivity {

    String campaign_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_add);
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

        // Запускаем ниспадающий список

        Spinner spinner_street_type = (Spinner)findViewById(R.id.list_street_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getApplicationContext(),
                R.array.street_types,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_street_type.setAdapter(adapter);

    }

    public void onSaveBtn(View view) {
        ElectionDbHelper MydbHelper = new ElectionDbHelper(getApplicationContext());
        SQLiteDatabase db = MydbHelper.getReadableDatabase();

        EditText street_name = (EditText)findViewById(R.id.street_name);
        Spinner street_type = (Spinner)findViewById(R.id.list_street_type);

        ContentValues values = new ContentValues();
        values.put(ElectionContract.StreetEntry.COLUMN_UUID, DataBaseUtils.getFieldUUID_value());
        values.put(ElectionContract.StreetEntry.COLUMN_NAME, street_name.getText().toString().replace("\n", ""));
        values.put(ElectionContract.StreetEntry.COLUMN_TYPE, street_type.getSelectedItem().toString().replace("\n", ""));
        values.put(ElectionContract.StreetEntry.COLUMN_CAMPAIGN_ID, campaign_id);
        values.put(ElectionContract.StreetEntry.COLUMN_CREATED_ON_DEVICE_ID, "1");
        long newRowId = db.insert(ElectionContract.StreetEntry.TABLE_NAME, null, values);

        onBackPressed();
    }
}