package ru.syeysk.election;
// TODO: вынести в пакет ru.syeysk.election.data

import android.provider.BaseColumns;
import ru.syeysk.election.utils.DataBaseUtils;

public final class ElectionContract {

    private ElectionContract() {

    }

    /* TODO: Вынести поля _ID, COLUMN_CREATE_DATE, COLUMN_MODIFY_DATE, COLUMN_UUID в родительский класс (публичными) */

    public static final class CampaignEntry implements BaseColumns {
        public final static String TABLE_NAME = "campaign";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_CREATE_DATE = DataBaseUtils.getFieldCreateDate_name();
        public final static String COLUMN_MODIFY_DATE = DataBaseUtils.getFieldModifyDate_name();
        public final static String COLUMN_UUID = DataBaseUtils.getFieldUUID_name();
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_FIO_CANDIDATE = "fio_candidate";
        public final static String COLUMN_REGION = "region";
        public final static String COLUMN_RAYON = "rayon";
        public final static String COLUMN_SETTLEMENT = "settlement";
        public final static String COLUMN_CREATED_ON_DEVICE_ID = "created_on_device_id";
    }

    public static final class StreetEntry implements BaseColumns {
        public final static String TABLE_NAME = "street";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_CREATE_DATE = DataBaseUtils.getFieldCreateDate_name();
        public final static String COLUMN_MODIFY_DATE = DataBaseUtils.getFieldModifyDate_name();
        public final static String COLUMN_UUID = DataBaseUtils.getFieldUUID_name();
        public final static String COLUMN_CAMPAIGN_ID = "campaign_id";
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_TYPE = "type";
        public final static String COLUMN_CREATED_ON_DEVICE_ID = "created_on_device_id";
    }

    public static final class HouseEntry implements BaseColumns {
        public final static String TABLE_NAME = "house";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_CREATE_DATE = DataBaseUtils.getFieldCreateDate_name();
        public final static String COLUMN_MODIFY_DATE = DataBaseUtils.getFieldModifyDate_name();
        public final static String COLUMN_UUID = DataBaseUtils.getFieldUUID_name();
        public final static String COLUMN_STREET_ID = "street_id";
        // номер дома либо квартиры
        public final static String COLUMN_NUMBER = "number";
        // тип здания: 0 - частный дом, 1 - многоквартирный, 2 - квартира
        public final static String COLUMN_TYPE = "type";
        // id многоквартирного дома
        public final static String COLUMN_GROUP_ID = "group_id";
        public final static String COLUMN_IS_OPENNED = "is_openned";
        public final static String COLUMN_IS_INTERESED = "is_interesed";
        public final static String COLUMN_IS_RECIEVED = "is_recieved";
        public final static String COLUMN_PROBLEM_DESCR = "problem_descr";
        public final static String COLUMN_COMMENT = "comment";
        public final static String COLUMN_CREATED_ON_DEVICE_ID = "created_on_device_id";
    }

}
