package ru.syeysk.election;

import android.content.ContentValues;
import android.database.Cursor;
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
import android.widget.Button;
import android.widget.EditText;

import ru.syeysk.election.utils.IFTools;

public class ActivityReaction extends AppCompatActivity {

    private ElectionDbHelper MydbHelper;
    private SQLiteDatabase db;
    String house_id;

    String is_openned;
    String is_interesed;
    String is_recieved;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reaction);
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

        house_id = getIntent().getStringExtra("house_id");

        MydbHelper = new ElectionDbHelper(getApplicationContext());
        db = MydbHelper.getReadableDatabase();

        String query = "SELECT "
                + ElectionContract.HouseEntry._ID + ", "
                + ElectionContract.HouseEntry.COLUMN_NUMBER + ", "
                + ElectionContract.HouseEntry.COLUMN_TYPE + ", "
                + ElectionContract.HouseEntry.COLUMN_IS_OPENNED + ", "
                + ElectionContract.HouseEntry.COLUMN_IS_INTERESED + ", "
                + ElectionContract.HouseEntry.COLUMN_IS_RECIEVED + ", "
                + ElectionContract.HouseEntry.COLUMN_PROBLEM_DESCR + ", "
                + ElectionContract.HouseEntry.COLUMN_COMMENT
                + " FROM " + ElectionContract.HouseEntry.TABLE_NAME
                + " WHERE " + ElectionContract.HouseEntry._ID + "=" + house_id;
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(ElectionContract.HouseEntry._ID));
            String number = cursor.getString(cursor.getColumnIndex(ElectionContract.HouseEntry.COLUMN_NUMBER));
            String type = cursor.getString(cursor.getColumnIndex(ElectionContract.HouseEntry.COLUMN_TYPE));
            is_openned = cursor.getString(cursor.getColumnIndex(ElectionContract.HouseEntry.COLUMN_IS_OPENNED));
            is_interesed = cursor.getString(cursor.getColumnIndex(ElectionContract.HouseEntry.COLUMN_IS_INTERESED));
            is_recieved = cursor.getString(cursor.getColumnIndex(ElectionContract.HouseEntry.COLUMN_IS_RECIEVED));
            String problem_descr = cursor.getString(cursor.getColumnIndex(ElectionContract.HouseEntry.COLUMN_PROBLEM_DESCR));
            String comment = cursor.getString(cursor.getColumnIndex(ElectionContract.HouseEntry.COLUMN_COMMENT));

            Button is_openned_btn = findViewById(R.id.btn_toggle_is_openned);
            Button is_interesed_btn = findViewById(R.id.btn_toggle_is_interesed);
            Button is_recieved_btn = findViewById(R.id.btn_toggle_is_recieved);
            EditText problem_descr_edittext = findViewById(R.id.edittext_problem_descr);
            EditText comment_edittext = findViewById(R.id.edittext_comment);

            IFTools.setBtnCondition(getApplicationContext(), is_openned_btn, is_openned);
            IFTools.setBtnCondition(getApplicationContext(), is_interesed_btn, is_interesed);
            IFTools.setBtnCondition(getApplicationContext(), is_recieved_btn, is_recieved);
            problem_descr_edittext.setText(problem_descr);
            comment_edittext.setText(comment);
        }
        cursor.close();

        //

        EditText text_comment = (EditText)findViewById(R.id.edittext_problem_descr);

        text_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ContentValues values = new ContentValues();
                values.put("problem_descr", s.toString());
                db.update(ElectionContract.HouseEntry.TABLE_NAME,
                        values,
                        ElectionContract.HouseEntry._ID + "=?",
                        new String[]{house_id});
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //

        EditText comment = (EditText)findViewById(R.id.edittext_comment);

        comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ContentValues values = new ContentValues();
                values.put("comment", s.toString());
                db.update(ElectionContract.HouseEntry.TABLE_NAME,
                        values,
                        ElectionContract.HouseEntry._ID + "=?",
                        new String[]{house_id});
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    /*public void setBtnCondition(Button btn, String condition) {
        if (condition == null) {
            btn.setBackgroundColor(getResources().getColor(R.color.colorNull, getTheme()));
        } else if (condition.equals("1")) {
            btn.setBackgroundColor(getResources().getColor(R.color.colorPositive, getTheme()));
        } else if (condition.equals("0")) {
            btn.setBackgroundColor(getResources().getColor(R.color.colorNegative, getTheme()));
        }
    }*/

    public void toggleBtnCondition(View btn) {
        String value = null;
        String field_name;
        if (btn.getId() == R.id.btn_toggle_is_openned) {
            value = is_openned;
            field_name = ElectionContract.HouseEntry.COLUMN_IS_OPENNED;
        } else if (btn.getId() == R.id.btn_toggle_is_interesed) {
            value = is_interesed;
            field_name = ElectionContract.HouseEntry.COLUMN_IS_INTERESED;
        } else if (btn.getId() == R.id.btn_toggle_is_recieved) {
            value = is_recieved;
            field_name = ElectionContract.HouseEntry.COLUMN_IS_RECIEVED;
        } else {
            return;
        }

        if (value == null) value = "1";
        else if (value.equals("1")) value = "0";
        else if (value.equals("0")) value = null;

        IFTools.setBtnCondition(getApplicationContext(), (Button)btn, value);

        ContentValues values = new ContentValues();
        values.put(field_name, value);
        db.update(ElectionContract.HouseEntry.TABLE_NAME,
                values,
                ElectionContract.HouseEntry._ID + "=?",
                new String[]{house_id});

        if (btn.getId() == R.id.btn_toggle_is_openned) is_openned = value;
        else if (btn.getId() == R.id.btn_toggle_is_interesed) is_interesed = value;
        else if (btn.getId() == R.id.btn_toggle_is_recieved) is_recieved = value;

    }

}