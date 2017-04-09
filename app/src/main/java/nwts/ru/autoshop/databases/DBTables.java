package nwts.ru.autoshop.databases;

/**
 * Created by пользователь on 13.03.2017.
 */

public interface DBTables extends DBConstant {

    public static final String SQL_CREATE_ENTRIES_USERS =
            "CREATE TABLE "                   + TABLE_USERS + " (" +
                    KEY_ID                      +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    KEY_DESC                    +" TEXT"  +
                    ")";

    public static final String SQL_CREATE_ENTRIES_FLOWERS =
            "CREATE TABLE "                   + TABLE_FLOWERS + " (" +
                    KEY_ID                      +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    FLOWER_PRODUCT_ID            +" INTEGER,"  +
                    FLOWER_NAME                    +" TEXT,"  +
                    FLOWER_CATEGORY                    +" TEXT,"  +
                    FLOWER_INSTRUCTIONS                    +" TEXT,"  +
                    FLOWER_PRICE                    +" REAL,"  +
                    FLOWER_PHOTO                    +" TEXT,"  +
                    FLOWER_BITMAP                    +" TEXT"  +
                    ")";

    public static final String SQL_CREATE_ENTRIES_CATEGORY =
            "CREATE TABLE "                     + TABLE_CATEGORY + " (" +
                    SQL_ID                      +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    SQL_CATEGORY_ID             +" INTEGER,"  +
                    SQL_CATEGORY_NAME           +" TEXT,"  +
                    SQL_CATEGORY_IMAGE          +" TEXT,"  +
                    SQL_REFRESH_TIME            +" NUMERIC"  +
                    ")";

}

