package ru.syeysk.election;
// TODO: вынести в пакет ru.syeysk.election.data


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import ru.syeysk.election.utils.DataBaseUtils;

public class ElectionDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = ElectionDbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "hotel.db";
    private static final int DATABASE_VERSION = 1;

    public ElectionDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_GUESTS_TABLE = "CREATE TABLE IF NOT EXISTS " + ElectionContract.CampaignEntry.TABLE_NAME + " ("
                + ElectionContract.CampaignEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DataBaseUtils.getFieldCreateDate_Create() + ", "
                + DataBaseUtils.getFieldModifyDate_Create() + ", "
                + DataBaseUtils.getFieldUUID_Create() + ", "
                + ElectionContract.CampaignEntry.COLUMN_NAME + " VARCHAR(255) NOT NULL, "
                + ElectionContract.CampaignEntry.COLUMN_FIO_CANDIDATE + " VARCHAR(255) NOT NULL, "
                + ElectionContract.CampaignEntry.COLUMN_REGION + " VARCHAR(255) NOT NULL, "
                + ElectionContract.CampaignEntry.COLUMN_RAYON + " VARCHAR(255) NOT NULL, "
                + ElectionContract.CampaignEntry.COLUMN_SETTLEMENT + " VARCHAR(255) NOT NULL, "
                + ElectionContract.CampaignEntry.COLUMN_CREATED_ON_DEVICE_ID + " VARCHAR(255) NOT NULL);";

        db.execSQL(SQL_CREATE_GUESTS_TABLE);

        // TODO: поле COLUMN_CAMPAIGN_ID должно быть внешним ключом
        String SQL_CREATE_STREET_TABLE = "CREATE TABLE IF NOT EXISTS " + ElectionContract.StreetEntry.TABLE_NAME + " ("
                + ElectionContract.StreetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DataBaseUtils.getFieldCreateDate_Create() + ", "
                + DataBaseUtils.getFieldModifyDate_Create() + ", "
                + DataBaseUtils.getFieldUUID_Create() + ", "
                + ElectionContract.StreetEntry.COLUMN_CAMPAIGN_ID + " INT NOT NULL, "
                + ElectionContract.StreetEntry.COLUMN_NAME + " VARCHAR(255) NOT NULL, "
                + ElectionContract.StreetEntry.COLUMN_TYPE + " VARCHAR(15) NOT NULL, "
                + ElectionContract.StreetEntry.COLUMN_CREATED_ON_DEVICE_ID + " VARCHAR(255) NOT NULL);";

        db.execSQL(SQL_CREATE_STREET_TABLE);

        String SQL_CREATE_HOUSE_TABLE = "CREATE TABLE IF NOT EXISTS " + ElectionContract.HouseEntry.TABLE_NAME + " ("
                + ElectionContract.HouseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DataBaseUtils.getFieldCreateDate_Create() + ", "
                + DataBaseUtils.getFieldModifyDate_Create() + ", "
                + DataBaseUtils.getFieldUUID_Create() + ", "
                // TODO: поле COLUMN_STREET_ID должно быть внешним ключом
                + ElectionContract.HouseEntry.COLUMN_STREET_ID + " INT NOT NULL, "
                + ElectionContract.HouseEntry.COLUMN_NUMBER + " VARCHAR(10) NOT NULL, "
                + ElectionContract.HouseEntry.COLUMN_TYPE + " INT NOT NULL, "
                // TODO: поле COLUMN_GROUP_ID должно быть foreign key, индекс (не уникальный)
                + ElectionContract.HouseEntry.COLUMN_GROUP_ID + " INT, "
                + ElectionContract.HouseEntry.COLUMN_IS_OPENNED + " INT, "
                + ElectionContract.HouseEntry.COLUMN_IS_INTERESED + " INT, "
                + ElectionContract.HouseEntry.COLUMN_IS_RECIEVED + " INT, "
                + ElectionContract.HouseEntry.COLUMN_PROBLEM_DESCR + " TEXT NOT NULL DEFAULT \"\", "
                + ElectionContract.HouseEntry.COLUMN_COMMENT + " VARCHAR(255) NOT NULL DEFAULT \"\", "
                + ElectionContract.HouseEntry.COLUMN_CREATED_ON_DEVICE_ID + " VARCHAR(255) NOT NULL);";

        db.execSQL(SQL_CREATE_HOUSE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Запишем в журнал
        //Log.w("SQLite", "Обновляемся с версии " + oldVersion + " на версию " + newVersion);

        // Удаляем старую таблицу и создаём новую
        //db.execSQL("DROP TABLE IF EXISTS " + ElectionContract.CampaignEntry.TABLE_NAME);
        //db.execSQL("DROP TABLE IF EXISTS " + ElectionContract.StreetEntry.TABLE_NAME);
        //db.execSQL("DROP TABLE IF EXISTS " + ElectionContract.HouseEntry.TABLE_NAME);
        // Создаём новую таблицу
        onCreate(db);
    }
}
