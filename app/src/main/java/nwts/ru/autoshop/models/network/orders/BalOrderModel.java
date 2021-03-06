package nwts.ru.autoshop.models.network.orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import nwts.ru.autoshop.models.DaoSession;

/**
 * Created by пользователь on 23.05.2017.
 */

@Entity(active = true)
public class BalOrderModel {

    @Id
    private Long id;

    @SerializedName("TC_ID")
    @Expose
    private int TC_ID;
    
    @SerializedName("Oper_ID")
    @Expose
    private int Oper_ID;

    @SerializedName("User_ID")
    @Expose
    private int User_ID;

    @SerializedName("Credit")
    @Expose
    private int Credit;

    @SerializedName("Summa")
    @Expose
    private double Summa;

    @SerializedName("PaySystem")
    @Expose
    private String PaySystem;

    @SerializedName("Tovar_ID")
    @Expose
    private int Tovar_ID;

    @SerializedName("StatusID")
    @Expose
    private int StatusID;

    @SerializedName("Price")
    @Expose
    private double Price;

    @SerializedName("Date_Operation")
    @Expose
    private long Date_Operation;

    @SerializedName("Order_ID")
    @Expose
    private int Order_ID;

    @SerializedName("Balance_ID")
    @Expose
    private int Balance_ID;

    @SerializedName("SummaTovar")
    @Expose
    private double SummaTovar;

    @SerializedName("Tovar_Count")
    @Expose
    private int Tovar_Count;

    @SerializedName("Status_Name")
    @Expose
    private String Status_Name;

    @SerializedName("Menu_name")
    @Expose
    private String Menu_name;

    @SerializedName("Menu_image")
    @Expose
    private String Menu_image;

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
    @Generated(hash = 1221286629)
    private transient BalOrderModelDao myDao;

    public BalOrderModel(int TC_ID, int oper_ID, int user_ID, int credit, double summa, String paySystem, int tovar_ID,
                         int statusID, double price, long date_Operation, int order_ID, int balance_ID, double summaTovar,
                         int tovar_Count, String status_Name, String menu_name, String menu_image, Boolean error, String message) {
        this.TC_ID = TC_ID;
        Oper_ID = oper_ID;
        User_ID = user_ID;
        Credit = credit;
        Summa = summa;
        PaySystem = paySystem;
        Tovar_ID = tovar_ID;
        StatusID = statusID;
        Price = price;
        Date_Operation = date_Operation;
        Order_ID = order_ID;
        Balance_ID = balance_ID;
        SummaTovar = summaTovar;
        Tovar_Count = tovar_Count;
        Status_Name = status_Name;
        Menu_name = menu_name;
        Menu_image = menu_image;
        this.error = error;
        this.message = message;
    }

    @Generated(hash = 1165117043)
    public BalOrderModel(Long id, int TC_ID, int Oper_ID, int User_ID, int Credit, double Summa, String PaySystem, int Tovar_ID,
            int StatusID, double Price, long Date_Operation, int Order_ID, int Balance_ID, double SummaTovar, int Tovar_Count,
            String Status_Name, String Menu_name, String Menu_image, Boolean error, String message) {
        this.id = id;
        this.TC_ID = TC_ID;
        this.Oper_ID = Oper_ID;
        this.User_ID = User_ID;
        this.Credit = Credit;
        this.Summa = Summa;
        this.PaySystem = PaySystem;
        this.Tovar_ID = Tovar_ID;
        this.StatusID = StatusID;
        this.Price = Price;
        this.Date_Operation = Date_Operation;
        this.Order_ID = Order_ID;
        this.Balance_ID = Balance_ID;
        this.SummaTovar = SummaTovar;
        this.Tovar_Count = Tovar_Count;
        this.Status_Name = Status_Name;
        this.Menu_name = Menu_name;
        this.Menu_image = Menu_image;
        this.error = error;
        this.message = message;
    }

    @Generated(hash = 295241320)
    public BalOrderModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTC_ID() {
        return TC_ID;
    }

    public void setTC_ID(int TC_ID) {
        this.TC_ID = TC_ID;
    }

    public int getOper_ID() {
        return Oper_ID;
    }

    public void setOper_ID(int oper_ID) {
        Oper_ID = oper_ID;
    }

    public int getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }

    public int getCredit() {
        return Credit;
    }

    public void setCredit(int credit) {
        Credit = credit;
    }

    public double getSumma() {
        return Summa;
    }

    public void setSumma(double summa) {
        Summa = summa;
    }

    public String getPaySystem() {
        return PaySystem;
    }

    public void setPaySystem(String paySystem) {
        PaySystem = paySystem;
    }

    public int getTovar_ID() {
        return Tovar_ID;
    }

    public void setTovar_ID(int tovar_ID) {
        Tovar_ID = tovar_ID;
    }

    public int getStatusID() {
        return StatusID;
    }

    public void setStatusID(int statusID) {
        StatusID = statusID;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public long getDate_Operation() {
        return Date_Operation;
    }

    public void setDate_Operation(long date_Operation) {
        Date_Operation = date_Operation;
    }

    public int getOrder_ID() {
        return Order_ID;
    }

    public void setOrder_ID(int order_ID) {
        Order_ID = order_ID;
    }

    public int getBalance_ID() {
        return Balance_ID;
    }

    public void setBalance_ID(int balance_ID) {
        Balance_ID = balance_ID;
    }

    public double getSummaTovar() {
        return SummaTovar;
    }

    public void setSummaTovar(double summaTovar) {
        SummaTovar = summaTovar;
    }

    public int getTovar_Count() {
        return Tovar_Count;
    }

    public void setTovar_Count(int tovar_Count) {
        Tovar_Count = tovar_Count;
    }

    public String getStatus_Name() {
        return Status_Name;
    }

    public void setStatus_Name(String status_Name) {
        Status_Name = status_Name;
    }

    public String getMenu_name() {
        return Menu_name;
    }

    public void setMenu_name(String menu_name) {
        Menu_name = menu_name;
    }

    public String getMenu_image() {
        return Menu_image;
    }

    public void setMenu_image(String menu_image) {
        Menu_image = menu_image;
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
    @Generated(hash = 1215627058)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getBalOrderModelDao() : null;
    }
}
