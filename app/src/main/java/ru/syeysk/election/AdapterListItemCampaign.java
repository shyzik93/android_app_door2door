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

import ru.syeysk.election.ElectionContract.CampaignEntry;

public class AdapterListItemCampaign extends RecyclerView.Adapter<AdapterListItemCampaign.ListItemView> {

    private ElectionDbHelper MydbHelper;
    private SQLiteDatabase db;
    Context context;

    public static class ListItemView extends RecyclerView.ViewHolder {
        public TextView CampaignName;
        public TextView SettlementName;
        public TextView CampaignId;
        public ListItemView(View v) {
            super(v);
            CampaignName = v.findViewById(R.id.text_campaign_name);
            SettlementName = v.findViewById(R.id.text_settlement);
            CampaignId = v.findViewById(R.id.text_campaign_id);
        }
    }

    public AdapterListItemCampaign(Context _context) {
        MydbHelper = new ElectionDbHelper(_context);
        db = MydbHelper.getReadableDatabase();
        context = _context;
    }

    @NonNull
    @Override
    public AdapterListItemCampaign.ListItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_campaign, parent, false);
        ListItemView vh =  new ListItemView(itemView);
        return vh;
    }

    @Override
    public int getItemCount() {
        String query = "SELECT count("+CampaignEntry._ID+")  AS 'c' FROM " + CampaignEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        int count = 0;
        while (cursor.moveToNext()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListItemCampaign.ListItemView holder, int position) {
        String query = "SELECT "
                +CampaignEntry._ID + ", "
                +CampaignEntry.COLUMN_NAME + ", "
                +CampaignEntry.COLUMN_FIO_CANDIDATE + ", "
                +CampaignEntry.COLUMN_REGION + ", "
                +CampaignEntry.COLUMN_RAYON + ", "
                +CampaignEntry.COLUMN_SETTLEMENT
                +" FROM " + CampaignEntry.TABLE_NAME + " LIMIT "+position+",1";
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(CampaignEntry._ID));
            String fio_candidate = cursor.getString(cursor.getColumnIndex(CampaignEntry.COLUMN_FIO_CANDIDATE)).replace("\n", "");
            String name = cursor.getString(cursor.getColumnIndex(CampaignEntry.COLUMN_NAME)).replace("\n", "");
            String region = cursor.getString(cursor.getColumnIndex(CampaignEntry.COLUMN_REGION)).replace("\n", "");
            String rayon = cursor.getString(cursor.getColumnIndex(CampaignEntry.COLUMN_RAYON)).replace("\n", "");
            String settlement = cursor.getString(cursor.getColumnIndex(CampaignEntry.COLUMN_SETTLEMENT)).replace("\n", "");

            holder.CampaignName.setText(context.getString(R.string.fmt_campaign_name, fio_candidate, name));
            holder.SettlementName.setText(context.getString(R.string.fmt_settl_full_name, region, rayon, settlement));
            holder.CampaignId.setText(String.valueOf(id));
        }
        cursor.close();
    }
}
