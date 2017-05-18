package nwts.ru.autoshop.models.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import nwts.ru.autoshop.models.DaoSession;

/**
 * Created by пользователь on 17.05.2017.
 */
@Entity(active = true)
public class OrderModel {
    
    @Id
    private Long id;
    @SerializedName("Order_ID")
    @Expose
    private int Order_ID;
    @SerializedName("User_ID")
    @Expose
    private int User_ID;
    @SerializedName("StatusID")
    @Expose
    private int StatusID;
    @SerializedName("Date_Operation")
    @Expose
    private long Date_Operation;
    @SerializedName("Summa")
    @Expose
    private double Summa;
    @SerializedName("Status_Name")
    @Expose
    private String Status_Name;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;

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

    public OrderModel(int order_ID, int user_ID, int statusID, long date_Operation, double summa,
                      String status_Name, Boolean error, String message, DaoSession daoSession, OrderModelDao myDao) {
        Order_ID = order_ID;
        User_ID = user_ID;
        StatusID = statusID;
        Date_Operation = date_Operation;
        Summa = summa;
        Status_Name = status_Name;
        this.error = error;
        this.message = message;
        this.daoSession = daoSession;
        this.myDao = myDao;
    }

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 751840869)
    private transient OrderModelDao myDao;



    @Generated(hash = 1409243198)
    public OrderModel() {
    }

    @Generated(hash = 1076767349)
    public OrderModel(Long id, int Order_ID, int User_ID, int StatusID, long Date_Operation, double Summa,
            String Status_Name, Boolean error, String message) {
        this.id = id;
        this.Order_ID = Order_ID;
        this.User_ID = User_ID;
        this.StatusID = StatusID;
        this.Date_Operation = Date_Operation;
        this.Summa = Summa;
        this.Status_Name = Status_Name;
        this.error = error;
        this.message = message;
    }

    public int getOrderID() {
        return Order_ID;
    }

    public void setOrderID(int orderID) {
        this.Order_ID = orderID;
    }

    public int getUserID() {
        return User_ID;
    }

    public void setUserID(int userID) {
        this.User_ID = userID;
    }

    public int getStatusID() {
        return StatusID;
    }

    public void setStatusID(int statusID) {
        this.StatusID = statusID;
    }

    public long getDateOperation() {
        return Date_Operation;
    }

    public void setDateOperation(long dateOperation) {
        this.Date_Operation = dateOperation;
    }

    public double getSumma() {
        return Summa;
    }

    public void setSumma(double summa) {
        this.Summa = summa;
    }

    public String getStatusName() {
        return Status_Name;
    }

    public void setStatusName(String statusName) {
        this.Status_Name = statusName;
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
    @Generated(hash = 62411597)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getOrderModelDao() : null;
    }

    public int getOrder_ID() {
        return this.Order_ID;
    }

    public void setOrder_ID(int Order_ID) {
        this.Order_ID = Order_ID;
    }

    public int getUser_ID() {
        return this.User_ID;
    }

    public void setUser_ID(int User_ID) {
        this.User_ID = User_ID;
    }

    public long getDate_Operation() {
        return this.Date_Operation;
    }

    public void setDate_Operation(long Date_Operation) {
        this.Date_Operation = Date_Operation;
    }

    public String getStatus_Name() {
        return this.Status_Name;
    }

    public void setStatus_Name(String Status_Name) {
        this.Status_Name = Status_Name;
    }

}
