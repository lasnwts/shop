package nwts.ru.autoshop.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;
import org.greenrobot.greendao.identityscope.IdentityScopeType;

import nwts.ru.autoshop.models.network.BalanceModelDao;
import nwts.ru.autoshop.models.network.CabinetModelDao;
import nwts.ru.autoshop.models.network.cart.CartModelDao;
import nwts.ru.autoshop.models.network.cart.CartModelBayDao;
import nwts.ru.autoshop.models.network.cart.ErrorModelDao;
import nwts.ru.autoshop.models.network.OrderModelDao;
import nwts.ru.autoshop.models.network.orders.BalOrderModelDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * Master of DAO (schema version 1): knows all DAOs.
 */
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 1;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(Database db, boolean ifNotExists) {
        CategoryItemDao.createTable(db, ifNotExists);
        GetCacheDao.createTable(db, ifNotExists);
        BalanceModelDao.createTable(db, ifNotExists);
        CabinetModelDao.createTable(db, ifNotExists);
        CartModelDao.createTable(db, ifNotExists);
        CartModelBayDao.createTable(db, ifNotExists);
        ErrorModelDao.createTable(db, ifNotExists);
        OrderModelDao.createTable(db, ifNotExists);
        BalOrderModelDao.createTable(db, ifNotExists);
        ProductCategoryDao.createTable(db, ifNotExists);
        ProductDetailImageDao.createTable(db, ifNotExists);
        SubCategoryItemDao.createTable(db, ifNotExists);
    }

    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(Database db, boolean ifExists) {
        CategoryItemDao.dropTable(db, ifExists);
        GetCacheDao.dropTable(db, ifExists);
        BalanceModelDao.dropTable(db, ifExists);
        CabinetModelDao.dropTable(db, ifExists);
        CartModelDao.dropTable(db, ifExists);
        CartModelBayDao.dropTable(db, ifExists);
        ErrorModelDao.dropTable(db, ifExists);
        OrderModelDao.dropTable(db, ifExists);
        BalOrderModelDao.dropTable(db, ifExists);
        ProductCategoryDao.dropTable(db, ifExists);
        ProductDetailImageDao.dropTable(db, ifExists);
        SubCategoryItemDao.dropTable(db, ifExists);
    }

    /**
     * WARNING: Drops all table on Upgrade! Use only during development.
     * Convenience method using a {@link DevOpenHelper}.
     */
    public static DaoSession newDevSession(Context context, String name) {
        Database db = new DevOpenHelper(context, name).getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        return daoMaster.newSession();
    }

    public DaoMaster(SQLiteDatabase db) {
        this(new StandardDatabase(db));
    }

    public DaoMaster(Database db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(CategoryItemDao.class);
        registerDaoClass(GetCacheDao.class);
        registerDaoClass(BalanceModelDao.class);
        registerDaoClass(CabinetModelDao.class);
        registerDaoClass(CartModelDao.class);
        registerDaoClass(CartModelBayDao.class);
        registerDaoClass(ErrorModelDao.class);
        registerDaoClass(OrderModelDao.class);
        registerDaoClass(BalOrderModelDao.class);
        registerDaoClass(ProductCategoryDao.class);
        registerDaoClass(ProductDetailImageDao.class);
        registerDaoClass(SubCategoryItemDao.class);
    }

    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }

    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }

    /**
     * Calls {@link #createAllTables(Database, boolean)} in {@link #onCreate(Database)} -
     */
    public static abstract class OpenHelper extends DatabaseOpenHelper {
        public OpenHelper(Context context, String name) {
            super(context, name, SCHEMA_VERSION);
        }

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(Database db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }

    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name) {
            super(context, name);
        }

        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

}
