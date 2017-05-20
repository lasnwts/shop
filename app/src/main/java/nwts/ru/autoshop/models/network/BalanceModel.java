package nwts.ru.autoshop.models.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import nwts.ru.autoshop.models.DaoSession;

/**
 * Created by пользователь on 19.05.2017.
 */

@Entity(active = true)
public class BalanceModel {

    @Id
    private Long id;
    @SerializedName("Balance_ID")
    @Expose
    public int Balance_ID;
    @SerializedName("User_ID")
    @Expose
    public int User_ID;
    @SerializedName("Date_Operation")
    @Expose
    public long Date_Operation;
    @SerializedName("SummaOperation")
    @Expose
    public double SummaOperation;
    @SerializedName("Summa")
    @Expose
    public double Summa;
    @SerializedName("Credit")
    @Expose
    public int Credit;
    @SerializedName("TypeOperation")
    @Expose
    public String TypeOperation;
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
    @Generated(hash = 1185920071)
    private transient BalanceModelDao myDao;

    public BalanceModel(int balanceID, int userID, long dateOperation,
                        double summaOperation, double summa, int credit,
                        String typeOperation, Boolean error, String message) {
        this.Balance_ID = balanceID;
        this.User_ID = userID;
        this.Date_Operation = dateOperation;
        this.SummaOperation = summaOperation;
        this.Summa = summa;
        this.Credit = credit;
        this.TypeOperation = typeOperation;
        this.error = error;
        this.message = message;
    }


    @Generated(hash = 2135541164)
    public BalanceModel() {
    }


    @Generated(hash = 2023101982)
    public BalanceModel(Long id, int Balance_ID, int User_ID, long Date_Operation,
            double SummaOperation, double Summa, int Credit, String TypeOperation,
            Boolean error, String message) {
        this.id = id;
        this.Balance_ID = Balance_ID;
        this.User_ID = User_ID;
        this.Date_Operation = Date_Operation;
        this.SummaOperation = SummaOperation;
        this.Summa = Summa;
        this.Credit = Credit;
        this.TypeOperation = TypeOperation;
        this.error = error;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getBalanceID() {
        return Balance_ID;
    }

    public void setBalanceID(int balanceID) {
        this.Balance_ID = balanceID;
    }

    public int getUserID() {
        return User_ID;
    }

    public void setUserID(int userID) {
        this.User_ID = userID;
    }

    public long getDateOperation() {
        return Date_Operation;
    }

    public void setDateOperation(long dateOperation) {
        this.Date_Operation = dateOperation;
    }

    public double getSummaOperation() {
        return SummaOperation;
    }

    public void setSummaOperation(double summaOperation) {
        this.SummaOperation = summaOperation;
    }

    public double getSumma() {
        return Summa;
    }

    public void setSumma(double summa) {
        this.Summa = summa;
    }

    public int getCredit() {
        return Credit;
    }

    public void setCredit(int credit) {
        this.Credit = credit;
    }

    public String getTypeOperation() {
        return TypeOperation;
    }

    public void setTypeOperation(String typeOperation) {
        this.TypeOperation = typeOperation;
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
    @Generated(hash = 1699623004)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getBalanceModelDao() : null;
    }


    public int getBalance_ID() {
        return this.Balance_ID;
    }


    public void setBalance_ID(int Balance_ID) {
        this.Balance_ID = Balance_ID;
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
}

