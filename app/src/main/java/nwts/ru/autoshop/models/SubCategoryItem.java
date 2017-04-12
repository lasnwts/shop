package nwts.ru.autoshop.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by пользователь on 29.03.2017.
 */
@Entity(active = true)
public class SubCategoryItem {

    @Id
    private Long id;
    
    private int SubCategory_ID;
    private String SubCategory_name;
    private String SubCategory_image;
    private int Category_ID;
    private long SubCategory_Refresh_Time;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 235633575)
    private transient SubCategoryItemDao myDao;


    public SubCategoryItem() {
    }

    public SubCategoryItem(int subCategory_ID, String subCategory_name, String subCategory_image, int category_ID) {
        SubCategory_ID = subCategory_ID;
        SubCategory_name = subCategory_name;
        SubCategory_image = subCategory_image;
        Category_ID = category_ID;
    }

    public SubCategoryItem(int subCategory_ID, String subCategory_name, String subCategory_image) {
        SubCategory_ID = subCategory_ID;
        SubCategory_name = subCategory_name;
        SubCategory_image = subCategory_image;
    }

    public SubCategoryItem(int subCategory_ID, String subCategory_name,
                           String subCategory_image, long subCategory_Refresh_Time) {
        SubCategory_ID = subCategory_ID;
        SubCategory_name = subCategory_name;
        SubCategory_image = subCategory_image;
        SubCategory_Refresh_Time = subCategory_Refresh_Time;
    }

    @Generated(hash = 1023852971)
    public SubCategoryItem(Long id, int SubCategory_ID, String SubCategory_name,
            String SubCategory_image, int Category_ID, long SubCategory_Refresh_Time) {
        this.id = id;
        this.SubCategory_ID = SubCategory_ID;
        this.SubCategory_name = SubCategory_name;
        this.SubCategory_image = SubCategory_image;
        this.Category_ID = Category_ID;
        this.SubCategory_Refresh_Time = SubCategory_Refresh_Time;
    }


    public int getSubCategory_ID() {
        return SubCategory_ID;
    }

    public String getSubCategory_name() {
        return SubCategory_name;
    }

    public String getSubCategory_image() {
        return SubCategory_image;
    }

    public long getSubCategory_Refresh_Time() {
        return SubCategory_Refresh_Time;
    }

    public void setSubCategory_ID(int SubCategory_ID) {
        this.SubCategory_ID = SubCategory_ID;
    }

    public void setSubCategory_name(String SubCategory_name) {
        this.SubCategory_name = SubCategory_name;
    }

    public void setSubCategory_image(String SubCategory_image) {
        this.SubCategory_image = SubCategory_image;
    }

    public void setSubCategory_Refresh_Time(long SubCategory_Refresh_Time) {
        this.SubCategory_Refresh_Time = SubCategory_Refresh_Time;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCategory_ID() {
        return this.Category_ID;
    }

    public void setCategory_ID(int Category_ID) {
        this.Category_ID = Category_ID;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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
    @Generated(hash = 1782636518)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getSubCategoryItemDao() : null;
    }

}
