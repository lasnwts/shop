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
 * DAO for table "PRODUCT_CATEGORY".
*/
public class ProductCategoryDao extends AbstractDao<ProductCategory, Long> {

    public static final String TABLENAME = "PRODUCT_CATEGORY";

    /**
     * Properties of entity ProductCategory.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Menu_ID = new Property(1, int.class, "Menu_ID", false, "MENU__ID");
        public final static Property Menu_name = new Property(2, String.class, "Menu_name", false, "MENU_NAME");
        public final static Property Price = new Property(3, double.class, "Price", false, "PRICE");
        public final static Property Description = new Property(4, String.class, "Description", false, "DESCRIPTION");
        public final static Property Menu_image = new Property(5, String.class, "Menu_image", false, "MENU_IMAGE");
        public final static Property Category_ID = new Property(6, int.class, "Category_ID", false, "CATEGORY__ID");
        public final static Property Product_ID = new Property(7, int.class, "Product_ID", false, "PRODUCT__ID");
        public final static Property SubCategory_ID = new Property(8, int.class, "SubCategory_ID", false, "SUB_CATEGORY__ID");
        public final static Property Quantity = new Property(9, int.class, "Quantity", false, "QUANTITY");
        public final static Property Rating = new Property(10, int.class, "Rating", false, "RATING");
        public final static Property MenuNameLowercase = new Property(11, String.class, "menuNameLowercase", false, "MENU_NAME_LOWERCASE");
    }

    private DaoSession daoSession;


    public ProductCategoryDao(DaoConfig config) {
        super(config);
    }
    
    public ProductCategoryDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PRODUCT_CATEGORY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"MENU__ID\" INTEGER NOT NULL ," + // 1: Menu_ID
                "\"MENU_NAME\" TEXT," + // 2: Menu_name
                "\"PRICE\" REAL NOT NULL ," + // 3: Price
                "\"DESCRIPTION\" TEXT," + // 4: Description
                "\"MENU_IMAGE\" TEXT," + // 5: Menu_image
                "\"CATEGORY__ID\" INTEGER NOT NULL ," + // 6: Category_ID
                "\"PRODUCT__ID\" INTEGER NOT NULL ," + // 7: Product_ID
                "\"SUB_CATEGORY__ID\" INTEGER NOT NULL ," + // 8: SubCategory_ID
                "\"QUANTITY\" INTEGER NOT NULL ," + // 9: Quantity
                "\"RATING\" INTEGER NOT NULL ," + // 10: Rating
                "\"MENU_NAME_LOWERCASE\" TEXT);"); // 11: menuNameLowercase
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PRODUCT_CATEGORY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ProductCategory entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getMenu_ID());
 
        String Menu_name = entity.getMenu_name();
        if (Menu_name != null) {
            stmt.bindString(3, Menu_name);
        }
        stmt.bindDouble(4, entity.getPrice());
 
        String Description = entity.getDescription();
        if (Description != null) {
            stmt.bindString(5, Description);
        }
 
        String Menu_image = entity.getMenu_image();
        if (Menu_image != null) {
            stmt.bindString(6, Menu_image);
        }
        stmt.bindLong(7, entity.getCategory_ID());
        stmt.bindLong(8, entity.getProduct_ID());
        stmt.bindLong(9, entity.getSubCategory_ID());
        stmt.bindLong(10, entity.getQuantity());
        stmt.bindLong(11, entity.getRating());
 
        String menuNameLowercase = entity.getMenuNameLowercase();
        if (menuNameLowercase != null) {
            stmt.bindString(12, menuNameLowercase);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ProductCategory entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getMenu_ID());
 
        String Menu_name = entity.getMenu_name();
        if (Menu_name != null) {
            stmt.bindString(3, Menu_name);
        }
        stmt.bindDouble(4, entity.getPrice());
 
        String Description = entity.getDescription();
        if (Description != null) {
            stmt.bindString(5, Description);
        }
 
        String Menu_image = entity.getMenu_image();
        if (Menu_image != null) {
            stmt.bindString(6, Menu_image);
        }
        stmt.bindLong(7, entity.getCategory_ID());
        stmt.bindLong(8, entity.getProduct_ID());
        stmt.bindLong(9, entity.getSubCategory_ID());
        stmt.bindLong(10, entity.getQuantity());
        stmt.bindLong(11, entity.getRating());
 
        String menuNameLowercase = entity.getMenuNameLowercase();
        if (menuNameLowercase != null) {
            stmt.bindString(12, menuNameLowercase);
        }
    }

    @Override
    protected final void attachEntity(ProductCategory entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ProductCategory readEntity(Cursor cursor, int offset) {
        ProductCategory entity = new ProductCategory( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // Menu_ID
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // Menu_name
            cursor.getDouble(offset + 3), // Price
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // Description
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // Menu_image
            cursor.getInt(offset + 6), // Category_ID
            cursor.getInt(offset + 7), // Product_ID
            cursor.getInt(offset + 8), // SubCategory_ID
            cursor.getInt(offset + 9), // Quantity
            cursor.getInt(offset + 10), // Rating
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11) // menuNameLowercase
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ProductCategory entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMenu_ID(cursor.getInt(offset + 1));
        entity.setMenu_name(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPrice(cursor.getDouble(offset + 3));
        entity.setDescription(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setMenu_image(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCategory_ID(cursor.getInt(offset + 6));
        entity.setProduct_ID(cursor.getInt(offset + 7));
        entity.setSubCategory_ID(cursor.getInt(offset + 8));
        entity.setQuantity(cursor.getInt(offset + 9));
        entity.setRating(cursor.getInt(offset + 10));
        entity.setMenuNameLowercase(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ProductCategory entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ProductCategory entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ProductCategory entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
