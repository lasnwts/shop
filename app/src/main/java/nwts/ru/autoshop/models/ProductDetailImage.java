package nwts.ru.autoshop.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by пользователь on 30.03.2017.
 */
@Entity(active = true)
public class ProductDetailImage {

    @Id
    private Long id;

    private int Menu_ID;
    private String Menu_image;
    private String Description;
    private int Product_ID;

    public int getProduct_ID() {
        return Product_ID;
    }

    public void setProduct_ID(int product_ID) {
        Product_ID = product_ID;
    }

    /** Used to resolve relations */
    @Generated(hash = 2040040024)

    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 477230214)
    private transient ProductDetailImageDao myDao;

    public ProductDetailImage() {
    }

    public ProductDetailImage(int menu_ID, String menu_image, String description, int product_ID) {
        Menu_ID = menu_ID;
        Menu_image = menu_image;
        Description = description;
        Product_ID = product_ID;
    }

    public ProductDetailImage(int menu_ID, String menu_image, String description) {
        Menu_ID = menu_ID;
        Menu_image = menu_image;
        Description = description;
    }

    @Generated(hash = 1839022297)
    public ProductDetailImage(Long id, int Menu_ID, String Menu_image, String Description,
            int Product_ID) {
        this.id = id;
        this.Menu_ID = Menu_ID;
        this.Menu_image = Menu_image;
        this.Description = Description;
        this.Product_ID = Product_ID;
    }

    public int getMenu_ID() {
        return Menu_ID;
    }

    public String getMenu_image() {
        return Menu_image;
    }

    public String getDescription() {
        return Description;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMenu_ID(int Menu_ID) {
        this.Menu_ID = Menu_ID;
    }

    public void setMenu_image(String Menu_image) {
        this.Menu_image = Menu_image;
    }

    public void setDescription(String Description) {
        this.Description = Description;
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
    @Generated(hash = 2015947481)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getProductDetailImageDao() : null;
    }
}
