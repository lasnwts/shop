package nwts.ru.autoshop.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by пользователь on 17.03.2017.
 */
@Entity(active = true)
public class CategoryItem {

    @Id
    private Long id;
    private int Category_ID;
    private String Category_name;
    private String Category_image;
    private long Category_Refresh_Time;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 2047680619)
    private transient CategoryItemDao myDao;


    public CategoryItem() {
    }

    public CategoryItem(int category_ID, String category_name, String category_image) {
        Category_ID = category_ID;
        Category_name = category_name;
        Category_image = category_image;
    }

    public CategoryItem( long id, int category_ID, String category_name, String category_image, long category_Refresh_Time) {
        Category_ID = category_ID;
        Category_name = category_name;
        Category_image = category_image;
        Category_Refresh_Time = category_Refresh_Time;
        this.id = id;
    }

    @Generated(hash = 908720122)
    public CategoryItem(Long id, int Category_ID, String Category_name, String Category_image, long Category_Refresh_Time) {
        this.id = id;
        this.Category_ID = Category_ID;
        this.Category_name = Category_name;
        this.Category_image = Category_image;
        this.Category_Refresh_Time = Category_Refresh_Time;
    }

    public int getCategory_ID() {
        return Category_ID;
    }

    public String getCategory_name() {
        return Category_name;
    }

    public String getCategory_image() {
        return Category_image;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategory_ID(int Category_ID) {
        this.Category_ID = Category_ID;
    }

    public void setCategory_name(String Category_name) {
        this.Category_name = Category_name;
    }

    public void setCategory_image(String Category_image) {
        this.Category_image = Category_image;
    }

    public long getCategory_Refresh_Time() {
        return this.Category_Refresh_Time;
    }

    public void setCategory_Refresh_Time(long Category_Refresh_Time) {
        this.Category_Refresh_Time = Category_Refresh_Time;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1664184486)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCategoryItemDao() : null;
    }
}
