package ru.syeysk.election;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterListItemStreet extends RecyclerView.Adapter<AdapterListItemStreet.ListItemView> {

    private ElectionDbHelper MydbHelper;
    private SQLiteDatabase db;
    private String campaign_id;

    public static class ListItemView extends RecyclerView.ViewHolder {
        public TextView StreetName;
        public TextView StreetId;
        public TextView CampaignId;
        public ListItemView(View v) {
            super(v);
            StreetName = v.findViewById(R.id.text_street_name);
            StreetId = v.findViewById(R.id.text_street_id);
            CampaignId = v.findViewById(R.id.text_campaign_id2);
        }
    }

    public AdapterListItemStreet(Context context, String _campaign_id) {
        MydbHelper = new ElectionDbHelper(context);
        db = MydbHelper.getReadableDatabase();
        campaign_id = _campaign_id;
    }

    @NonNull
    @Override
    public AdapterListItemStreet.ListItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_street, parent, false);
        ListItemView vh =  new ListItemView(itemView);
        return vh;
    }

    @Override
    public int getItemCount() {
        // TODO: переменную campaign_id не конкатенировать, а передавать в аргументе функции rawQuery
        String query = "SELECT count("+ ElectionContract.StreetEntry._ID+")  AS 'c' FROM "
                + ElectionContract.StreetEntry.TABLE_NAME + " WHERE "+ElectionContract.StreetEntry.COLUMN_CAMPAIGN_ID+"="+campaign_id;
        Cursor cursor = db.rawQuery(query, null);
        int count = 0;
        while (cursor.moveToNext()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListItemStreet.ListItemView holder, int position) {
        // TODO: переменную campaign_id не конкатенировать, а передавать в аргументе функции rawQuery
        String query = "SELECT "
                + ElectionContract.StreetEntry._ID + ", "
                + ElectionContract.StreetEntry.COLUMN_NAME + ", "
                + ElectionContract.StreetEntry.COLUMN_TYPE
                + " FROM " + ElectionContract.StreetEntry.TABLE_NAME
                + " WHERE " + ElectionContract.StreetEntry.COLUMN_CAMPAIGN_ID + "=" + campaign_id
                + " LIMIT "+position+",1";
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(ElectionContract.StreetEntry._ID));
            String name = cursor.getString(cursor.getColumnIndex(ElectionContract.StreetEntry.COLUMN_NAME));
            String type = cursor.getString(cursor.getColumnIndex(ElectionContract.StreetEntry.COLUMN_TYPE));

            // TODO: конкатенацию сделать в ресурсах
            holder.StreetName.setText(type +" "+ name);
            holder.StreetId.setText(String.valueOf(id));
            holder.CampaignId.setText(campaign_id);
        }
        cursor.close();
    }

}
