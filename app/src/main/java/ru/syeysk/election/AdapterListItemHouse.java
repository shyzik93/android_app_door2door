package ru.syeysk.election;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.syeysk.election.utils.IFTools;

public class AdapterListItemHouse extends RecyclerView.Adapter<AdapterListItemHouse.ListItemView> {

    private ElectionDbHelper MydbHelper;
    private SQLiteDatabase db;
    private String street_id;
    private Context context;
    private String multi_apartment_house_id;

    public static class ListItemView extends RecyclerView.ViewHolder {
        public TextView HouseNumber;
        public TextView HouseId;
        public TextView HouseType;
        public View btn;
        public ListItemView(View v) {
            super(v);
            btn = v.findViewById(R.id.layout_house_item);
            HouseNumber = v.findViewById(R.id.text_house_number);
            HouseId = v.findViewById(R.id.hidden_text_house_id);
            HouseType = v.findViewById(R.id.hidden_text_house_type);
        }
    }

    public AdapterListItemHouse(Context _context, String _street_id) {
        MydbHelper = new ElectionDbHelper(_context);
        db = MydbHelper.getReadableDatabase();
        street_id = _street_id;
        context = _context;
    }

    public void setMultiApartmentId(String _multi_apartment_house_id) {
        multi_apartment_house_id = _multi_apartment_house_id;
    }

    @NonNull
    @Override
    public AdapterListItemHouse.ListItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_house, parent, false);
        ListItemView vh =  new ListItemView(itemView);
        return vh;
    }

    @Override
    public int getItemCount() {

        // TODO: переменную street_id не конкатенировать, а передавать в аргументе функции rawQuery
        // TODO: переписать функцией select объекта db

        String group_field_sql;
        if (multi_apartment_house_id == null) {
            group_field_sql = ElectionContract.HouseEntry.COLUMN_GROUP_ID + " IS NULL";
        } else {
            group_field_sql = ElectionContract.HouseEntry.COLUMN_GROUP_ID + "=" + multi_apartment_house_id;
        }

        String query = "SELECT count("+ ElectionContract.HouseEntry._ID+")  AS 'c' FROM "
                + ElectionContract.HouseEntry.TABLE_NAME
                + " WHERE " +ElectionContract.HouseEntry.COLUMN_STREET_ID+"="+street_id
                + " AND " + group_field_sql;
        Cursor cursor = db.rawQuery(query, null);
        int count = 0;
        while (cursor.moveToNext()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListItemHouse.ListItemView holder, int position) {

        // TODO: переменную street_id не конкатенировать, а передавать в аргументе функции rawQuery
        // TODO: переписать функцией select объекта db

        String group_field_sql;
        if (multi_apartment_house_id == null) {
            group_field_sql = ElectionContract.HouseEntry.COLUMN_GROUP_ID + " IS NULL";
        } else {
            group_field_sql = ElectionContract.HouseEntry.COLUMN_GROUP_ID + "=" + multi_apartment_house_id;
        }

        String query = "SELECT "
                + ElectionContract.HouseEntry._ID + ", "
                + ElectionContract.HouseEntry.COLUMN_NUMBER + ", "
                + ElectionContract.HouseEntry.COLUMN_TYPE + ", "
                + ElectionContract.HouseEntry.COLUMN_IS_OPENNED
                + " FROM " + ElectionContract.HouseEntry.TABLE_NAME
                + " WHERE " + ElectionContract.HouseEntry.COLUMN_STREET_ID + "=" + street_id
                + " AND " + group_field_sql
                + " ORDER BY " + ElectionContract.HouseEntry.COLUMN_NUMBER
                + " LIMIT "+position+",1";
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(ElectionContract.HouseEntry._ID));
            String house_number = cursor.getString(cursor.getColumnIndex(ElectionContract.HouseEntry.COLUMN_NUMBER));
            String house_type = cursor.getString(cursor.getColumnIndex(ElectionContract.HouseEntry.COLUMN_TYPE));
            String is_openned = cursor.getString(cursor.getColumnIndex(ElectionContract.HouseEntry.COLUMN_IS_OPENNED));

            holder.HouseNumber.setText(house_number);
            holder.HouseId.setText(String.valueOf(id));
            holder.HouseType.setText(String.valueOf(house_type));
            if (house_type.equals("0") || house_type.equals("2")) {
                IFTools.setBtnCondition(context, holder.btn, is_openned);
            }
        }
        cursor.close();
    }
}

