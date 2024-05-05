package ru.syeysk.election;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ActivityHouseList extends AppCompatActivity {

    String street_id;
    String multi_apartment_house_id;
    RecyclerView recview_houses;
    AdapterListItemHouse adapter_recview_houses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_list);
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

        adapter_recview_houses = new AdapterListItemHouse(getApplicationContext(), street_id);

        recview_houses = findViewById(R.id.house_list);
        recview_houses.setLayoutManager(new GridLayoutManager(this, 4));
        recview_houses.setAdapter(adapter_recview_houses);

        multi_apartment_house_id = null;
    }

    @Override
    public void onBackPressed() {

        if (multi_apartment_house_id != null) {
            multi_apartment_house_id = null;

            // отображаем список квартир

            adapter_recview_houses.setMultiApartmentId(null);
            recview_houses.setAdapter(adapter_recview_houses);

            // Устнавливаем текст на кнопке в зависимости от типа дома

            String add_what = getString(R.string.dative_case_flat);

            Button btn_add = (Button) findViewById(R.id.button_select_house_add);
            btn_add.setText(getString(R.string.fmt_add_something, add_what));

            setTitle(getString(R.string.fmt_select_something, add_what));
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {

        //adapter_recview_houses;

        super.onResume();
    }

    // вызывается при нажатии на кнопку дома/квартиры
    public void onOpenReactionBtn(View view) {
        TextView view_house_type = (TextView)view.findViewById(R.id.hidden_text_house_type);
        TextView view_house_id = (TextView)view.findViewById(R.id.hidden_text_house_id);

        String house_type = view_house_type.getText().toString();
        String house_id = view_house_id.getText().toString();

        Log.w("myinfoooo", house_type);

        if (house_type.equals("1")) {
            multi_apartment_house_id = house_id;

            // отображаем список квартир

            adapter_recview_houses.setMultiApartmentId(multi_apartment_house_id);
            recview_houses.setAdapter(adapter_recview_houses);

            // Устнавливаем текст на кнопке в зависимости от типа дома

            String add_what = getString(R.string.dative_case_flat);

            Button btn_add = (Button)findViewById(R.id.button_select_house_add);
            btn_add.setText(getString(R.string.fmt_add_something, add_what));

            setTitle(getString(R.string.fmt_select_something, add_what));

        } else if (house_type.equals("0") || house_type.equals("2")) {
            Intent intent = new Intent(this, ActivityReaction.class);
            intent.putExtra("house_id", house_id);
            intent.putExtra("multi_apartment_house_id", multi_apartment_house_id);
            startActivity(intent);
        }
    }

    public void onOpenHouseAddBtn(View view) {
        Intent intent = new Intent(this, ActivityHouseAdd.class);
        intent.putExtra("street_id", street_id);
        intent.putExtra("multi_apartment_house_id", multi_apartment_house_id);
        startActivity(intent);
    }
}