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
 * DAO for table "SUB_CATEGORY_ITEM".
*/
public class SubCategoryItemDao extends AbstractDao<SubCategoryItem, Long> {

    public static final String TABLENAME = "SUB_CATEGORY_ITEM";

    /**
     * Properties of entity SubCategoryItem.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property SubCategory_ID = new Property(1, int.class, "SubCategory_ID", false, "SUB_CATEGORY__ID");
        public final static Property SubCategory_name = new Property(2, String.class, "SubCategory_name", false, "SUB_CATEGORY_NAME");
        public final static Property SubCategory_image = new Property(3, String.class, "SubCategory_image", false, "SUB_CATEGORY_IMAGE");
        public final static Property Category_ID = new Property(4, int.class, "Category_ID", false, "CATEGORY__ID");
        public final static Property SubCategory_Refresh_Time = new Property(5, long.class, "SubCategory_Refresh_Time", false, "SUB_CATEGORY__REFRESH__TIME");
    }

    private DaoSession daoSession;


    public SubCategoryItemDao(DaoConfig config) {
        super(config);
    }
    
    public SubCategoryItemDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SUB_CATEGORY_ITEM\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"SUB_CATEGORY__ID\" INTEGER NOT NULL ," + // 1: SubCategory_ID
                "\"SUB_CATEGORY_NAME\" TEXT," + // 2: SubCategory_name
                "\"SUB_CATEGORY_IMAGE\" TEXT," + // 3: SubCategory_image
                "\"CATEGORY__ID\" INTEGER NOT NULL ," + // 4: Category_ID
                "\"SUB_CATEGORY__REFRESH__TIME\" INTEGER NOT NULL );"); // 5: SubCategory_Refresh_Time
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SUB_CATEGORY_ITEM\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, SubCategoryItem entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getSubCategory_ID());
 
        String SubCategory_name = entity.getSubCategory_name();
        if (SubCategory_name != null) {
            stmt.bindString(3, SubCategory_name);
        }
 
        String SubCategory_image = entity.getSubCategory_image();
        if (SubCategory_image != null) {
            stmt.bindString(4, SubCategory_image);
        }
        stmt.bindLong(5, entity.getCategory_ID());
        stmt.bindLong(6, entity.getSubCategory_Refresh_Time());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, SubCategoryItem entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getSubCategory_ID());
 
        String SubCategory_name = entity.getSubCategory_name();
        if (SubCategory_name != null) {
            stmt.bindString(3, SubCategory_name);
        }
 
        String SubCategory_image = entity.getSubCategory_image();
        if (SubCategory_image != null) {
            stmt.bindString(4, SubCategory_image);
        }
        stmt.bindLong(5, entity.getCategory_ID());
        stmt.bindLong(6, entity.getSubCategory_Refresh_Time());
    }

    @Override
    protected final void attachEntity(SubCategoryItem entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public SubCategoryItem readEntity(Cursor cursor, int offset) {
        SubCategoryItem entity = new SubCategoryItem( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // SubCategory_ID
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // SubCategory_name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // SubCategory_image
            cursor.getInt(offset + 4), // Category_ID
            cursor.getLong(offset + 5) // SubCategory_Refresh_Time
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, SubCategoryItem entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setSubCategory_ID(cursor.getInt(offset + 1));
        entity.setSubCategory_name(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setSubCategory_image(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCategory_ID(cursor.getInt(offset + 4));
        entity.setSubCategory_Refresh_Time(cursor.getLong(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(SubCategoryItem entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(SubCategoryItem entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(SubCategoryItem entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
