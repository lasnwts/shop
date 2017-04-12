package nwts.ru.autoshop.models;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "GET_CACHE".
*/
public class GetCacheDao extends AbstractDao<GetCache, Long> {

    public static final String TABLENAME = "GET_CACHE";

    /**
     * Properties of entity GetCache.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property FieldGet = new Property(1, String.class, "fieldGet", false, "FIELD_GET");
        public final static Property DateTime = new Property(2, long.class, "dateTime", false, "DATE_TIME");
    }

    private DaoSession daoSession;


    public GetCacheDao(DaoConfig config) {
        super(config);
    }
    
    public GetCacheDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"GET_CACHE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"FIELD_GET\" TEXT," + // 1: fieldGet
                "\"DATE_TIME\" INTEGER NOT NULL );"); // 2: dateTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"GET_CACHE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, GetCache entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String fieldGet = entity.getFieldGet();
        if (fieldGet != null) {
            stmt.bindString(2, fieldGet);
        }
        stmt.bindLong(3, entity.getDateTime());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, GetCache entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String fieldGet = entity.getFieldGet();
        if (fieldGet != null) {
            stmt.bindString(2, fieldGet);
        }
        stmt.bindLong(3, entity.getDateTime());
    }

    @Override
    protected final void attachEntity(GetCache entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public GetCache readEntity(Cursor cursor, int offset) {
        GetCache entity = new GetCache( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // fieldGet
            cursor.getLong(offset + 2) // dateTime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, GetCache entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setFieldGet(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDateTime(cursor.getLong(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(GetCache entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(GetCache entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(GetCache entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}