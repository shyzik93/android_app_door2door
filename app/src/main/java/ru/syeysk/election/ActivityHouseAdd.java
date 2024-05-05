package ru.syeysk.election;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import ru.syeysk.election.utils.DataBaseUtils;
import ru.syeysk.election.utils.DataBaseUtils;

public class ActivityHouseAdd extends AppCompatActivity {

    String street_id;
    String multi_apartment_house_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_add);
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

        street_id = getIntent().getStringExtra("street_id");
        multi_apartment_house_id = getIntent().getStringExtra("multi_apartment_house_id");

        // Устнавливаем заголовок в зависимости от типа дома

        if (multi_apartment_house_id == null) {
            setTitle(getString(R.string.fmt_new_something_male, getString(R.string.nominative_case_house)));
        } else {
            setTitle(getString(R.string.fmt_new_something_female, getString(R.string.nominative_case_flat)));
        }

        // Запускаем ниспадающий список

        Spinner spinner_house_type = (Spinner)findViewById(R.id.list_house_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getApplicationContext(),
                R.array.house_types,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_house_type.setAdapter(adapter);

    }

    public void onSaveBtn(View view) {
        ElectionDbHelper MydbHelper = new ElectionDbHelper(getApplicationContext());
        SQLiteDatabase db = MydbHelper.getReadableDatabase();

        EditText house_number = (EditText)findViewById(R.id.house_number);

        Spinner view_house_type = (Spinner)findViewById(R.id.list_house_type);
        String house_type = String.valueOf(view_house_type.getSelectedItemPosition());
        if (multi_apartment_house_id != null) {
            house_type = "2";
        }

        ContentValues values = new ContentValues();
        values.put(ElectionContract.HouseEntry.COLUMN_UUID, DataBaseUtils.getFieldUUID_value());
        values.put(ElectionContract.HouseEntry.COLUMN_NUMBER, house_number.getText().toString().replace("\n", ""));
        values.put(ElectionContract.HouseEntry.COLUMN_TYPE, house_type);
        values.put(ElectionContract.HouseEntry.COLUMN_STREET_ID, street_id);
        values.put(ElectionContract.HouseEntry.COLUMN_GROUP_ID, multi_apartment_house_id);
        values.put(ElectionContract.HouseEntry.COLUMN_CREATED_ON_DEVICE_ID, "1");
        long newRowId = db.insert(ElectionContract.HouseEntry.TABLE_NAME, null, values);

        onBackPressed();
    }
}