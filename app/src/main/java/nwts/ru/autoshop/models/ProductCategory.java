package nwts.ru.autoshop.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by пользователь on 21.03.2017.
 */
@Entity(active = true)
public class ProductCategory {

    @Id
    private Long id;

    private int Menu_ID;
    private String Menu_name;
    private double Price;
    private String Description;
    private String Menu_image;
    private int Category_ID;
    private int Product_ID;
    private int SubCategory_ID;
    private int Quantity;

    /** Used for active entity operations. */
    @Generated(hash = 2047590669)
    private transient ProductCategoryDao myDao;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

//    // create price format
//    DecimalFormat formatData = new DecimalFormat("#.##");

    public ProductCategory() {
    }

    public ProductCategory(int menu_ID, String menu_name, double price, String menu_image) {
        Menu_ID = menu_ID;
        Menu_name = menu_name;
        Price = price;
        Menu_image = menu_image;
    }

    public ProductCategory(int menu_ID, String menu_name, double price, String description,
                           String menu_image, int category_ID, int product_ID, int subCategory_ID, int quantity) {
        Menu_ID = menu_ID;
        Menu_name = menu_name;
        Price = price;
        Description = description;
        Menu_image = menu_image;
        Category_ID = category_ID;
        Product_ID = product_ID;
        SubCategory_ID = subCategory_ID;
        Quantity = quantity;
    }

    @Generated(hash = 1957459494)
    public ProductCategory(Long id, int Menu_ID, String Menu_name, double Price, String Description,
            String Menu_image, int Category_ID, int Product_ID, int SubCategory_ID, int Quantity) {
        this.id = id;
        this.Menu_ID = Menu_ID;
        this.Menu_name = Menu_name;
        this.Price = Price;
        this.Description = Description;
        this.Menu_image = Menu_image;
        this.Category_ID = Category_ID;
        this.Product_ID = Product_ID;
        this.SubCategory_ID = SubCategory_ID;
        this.Quantity = Quantity;
    }

    public String getDescription() {
        return Description;
    }

    public int getCategory_ID() {
        return Category_ID;
    }

    public int getProduct_ID() {
        return Product_ID;
    }

    public int getSubCategory_ID() {
        return SubCategory_ID;
    }

    public int getQuantity() {
        return Quantity;
    }

    public int getMenu_ID() {
        return Menu_ID;
    }

    public String getMenu_name() {
        return Menu_name;
    }

    public double getPrice() {
        return Price;
    }

    public String getMenu_image() {
        return Menu_image;
    }

    @Override
    public String toString() {
        return super.toString();
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

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 306233638)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getProductCategoryDao() : null;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public void setSubCategory_ID(int SubCategory_ID) {
        this.SubCategory_ID = SubCategory_ID;
    }

    public void setProduct_ID(int Product_ID) {
        this.Product_ID = Product_ID;
    }

    public void setCategory_ID(int Category_ID) {
        this.Category_ID = Category_ID;
    }

    public void setMenu_image(String Menu_image) {
        this.Menu_image = Menu_image;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public void setMenu_name(String Menu_name) {
        this.Menu_name = Menu_name;
    }

    public void setMenu_ID(int Menu_ID) {
        this.Menu_ID = Menu_ID;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
