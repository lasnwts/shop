package nwts.ru.autoshop.models.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import java.util.List;

import nwts.ru.autoshop.models.DaoSession;

/**
 * Created by пользователь on 16.05.2017.
 */
@Entity(active = true)
public class CabinetModel {


    @Id
    private Long id;
    @SerializedName("Balance_ID")
    @Expose
    private String balanceID;
    @SerializedName("Date_Operation")
    @Expose
    private String dateOperation;
    @SerializedName("Balance")
    @Expose
    private String balance;
    @SerializedName("Cart_ID")
    @Expose
    private String cartID;
    @SerializedName("Tovar_Count")
    @Expose
    private String tovarCount;
    @SerializedName("CartSumma")
    @Expose
    private String cartSumma;
    @SerializedName("orderStatus0")
    @Expose
    private String orderStatus0;
    @SerializedName("orderStatus1")
    @Expose
    private String orderStatus1;
    @SerializedName("orderStatus2")
    @Expose
    private String orderStatus2;

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
    @Generated(hash = 1792680461)
    private transient CabinetModelDao myDao;

    @Generated(hash = 890766024)
    public CabinetModel(Long id, String balanceID, String dateOperation,
            String balance, String cartID, String tovarCount, String cartSumma,
            String orderStatus0, String orderStatus1, String orderStatus2,
            Boolean error, String message) {
        this.id = id;
        this.balanceID = balanceID;
        this.dateOperation = dateOperation;
        this.balance = balance;
        this.cartID = cartID;
        this.tovarCount = tovarCount;
        this.cartSumma = cartSumma;
        this.orderStatus0 = orderStatus0;
        this.orderStatus1 = orderStatus1;
        this.orderStatus2 = orderStatus2;
        this.error = error;
        this.message = message;
    }


    @Generated(hash = 1022222618)
    public CabinetModel() {
    }
    

    public String getBalanceID() {
        return balanceID;
    }

    public void setBalanceID(String balanceID) {
        this.balanceID = balanceID;
    }

    public String getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(String dateOperation) {
        this.dateOperation = dateOperation;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCartID() {
        return cartID;
    }

    public void setCartID(String cartID) {
        this.cartID = cartID;
    }

    public String getTovarCount() {
        return tovarCount;
    }

    public void setTovarCount(String tovarCount) {
        this.tovarCount = tovarCount;
    }

    public String getCartSumma() {
        return cartSumma;
    }

    public void setCartSumma(String cartSumma) {
        this.cartSumma = cartSumma;
    }

    public String getOrderStatus0() {
        return orderStatus0;
    }

    public void setOrderStatus0(String orderStatus0) {
        this.orderStatus0 = orderStatus0;
    }

    public String getOrderStatus1() {
        return orderStatus1;
    }

    public void setOrderStatus1(String orderStatus1) {
        this.orderStatus1 = orderStatus1;
    }

    public String getOrderStatus2() {
        return orderStatus2;
    }

    public void setOrderStatus2(String orderStatus2) {
        this.orderStatus2 = orderStatus2;
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
    @Generated(hash = 711888135)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCabinetModelDao() : null;
    }
}
