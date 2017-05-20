package nwts.ru.autoshop.models.network.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import nwts.ru.autoshop.models.DaoSession;

/**
 * Created by пользователь on 20.05.2017.
 */

@Entity(active = true)
public class CartModelBay {

    @Id
    private Long id;
    @SerializedName("Cart_ID")
    @Expose
    public int Cart_ID;
    @SerializedName("User_ID")
    @Expose
    public int User_ID;
    @SerializedName("CartTovarCount")
    @Expose
    public int CartTovarCount;
    @SerializedName("CartSumma")
    @Expose
    public double CartSumma;
    @SerializedName("TC_ID")
    @Expose
    public int TC_ID;
    @SerializedName("Tovar_ID")
    @Expose
    public int Tovar_ID;
    @SerializedName("Tovar_Count")
    @Expose
    public int Tovar_Count;
    @SerializedName("Price")
    @Expose
    public double Price;
    @SerializedName("Summa")
    @Expose
    public double Summa;
    @SerializedName("Menu_name")
    @Expose
    public String Menu_name;
    @SerializedName("Category_ID")
    @Expose
    public int Category_ID;
    @SerializedName("Menu_image")
    @Expose
    public String Menu_image;
    @SerializedName("SubCategory_ID")
    @Expose
    public int SubCategory_ID;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1019833877)
    private transient CartModelBayDao myDao;

    public CartModelBay(int cart_ID, int user_ID, int cartTovarCount, double cartSumma,
                        int TC_ID, int tovar_ID, int tovar_Count, double price,
                        double summa, String menu_name, int category_ID,
                        String menu_image, int subCategory_ID,
                        Boolean error, String message) {
        Cart_ID = cart_ID;
        User_ID = user_ID;
        CartTovarCount = cartTovarCount;
        CartSumma = cartSumma;
        this.TC_ID = TC_ID;
        Tovar_ID = tovar_ID;
        Tovar_Count = tovar_Count;
        Price = price;
        Summa = summa;
        Menu_name = menu_name;
        Category_ID = category_ID;
        Menu_image = menu_image;
        SubCategory_ID = subCategory_ID;
        this.error = error;
        this.message = message;
    }

    @Generated(hash = 57828623)
    public CartModelBay(Long id, int Cart_ID, int User_ID, int CartTovarCount,
            double CartSumma, int TC_ID, int Tovar_ID, int Tovar_Count, double Price,
            double Summa, String Menu_name, int Category_ID, String Menu_image,
            int SubCategory_ID, Boolean error, String message) {
        this.id = id;
        this.Cart_ID = Cart_ID;
        this.User_ID = User_ID;
        this.CartTovarCount = CartTovarCount;
        this.CartSumma = CartSumma;
        this.TC_ID = TC_ID;
        this.Tovar_ID = Tovar_ID;
        this.Tovar_Count = Tovar_Count;
        this.Price = Price;
        this.Summa = Summa;
        this.Menu_name = Menu_name;
        this.Category_ID = Category_ID;
        this.Menu_image = Menu_image;
        this.SubCategory_ID = SubCategory_ID;
        this.error = error;
        this.message = message;
    }

    @Generated(hash = 928176355)
    public CartModelBay() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCart_ID() {
        return Cart_ID;
    }

    public void setCart_ID(int cart_ID) {
        Cart_ID = cart_ID;
    }

    public int getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }

    public int getCartTovarCount() {
        return CartTovarCount;
    }

    public void setCartTovarCount(int cartTovarCount) {
        CartTovarCount = cartTovarCount;
    }

    public double getCartSumma() {
        return CartSumma;
    }

    public void setCartSumma(double cartSumma) {
        CartSumma = cartSumma;
    }

    public int getTC_ID() {
        return TC_ID;
    }

    public void setTC_ID(int TC_ID) {
        this.TC_ID = TC_ID;
    }

    public int getTovar_ID() {
        return Tovar_ID;
    }

    public void setTovar_ID(int tovar_ID) {
        Tovar_ID = tovar_ID;
    }

    public int getTovar_Count() {
        return Tovar_Count;
    }

    public void setTovar_Count(int tovar_Count) {
        Tovar_Count = tovar_Count;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public double getSumma() {
        return Summa;
    }

    public void setSumma(double summa) {
        Summa = summa;
    }

    public String getMenu_name() {
        return Menu_name;
    }

    public void setMenu_name(String menu_name) {
        Menu_name = menu_name;
    }

    public int getCategory_ID() {
        return Category_ID;
    }

    public void setCategory_ID(int category_ID) {
        Category_ID = category_ID;
    }

    public String getMenu_image() {
        return Menu_image;
    }

    public void setMenu_image(String menu_image) {
        Menu_image = menu_image;
    }

    public int getSubCategory_ID() {
        return SubCategory_ID;
    }

    public void setSubCategory_ID(int subCategory_ID) {
        SubCategory_ID = subCategory_ID;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
    @Generated(hash = 288912898)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCartModelBayDao() : null;
    }
}
