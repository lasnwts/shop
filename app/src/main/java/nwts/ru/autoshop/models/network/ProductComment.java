package nwts.ru.autoshop.models.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import nwts.ru.autoshop.models.DaoSession;

/**
 * Created by пользователь on 31.05.2017.
 */
@Entity(active = true)
public class ProductComment {

    @Id
    private Long id;

    @SerializedName("User_ID")
    @Expose
    public int User_ID;

    @SerializedName("Product_ID")
    @Expose
    public int Product_ID;

    @SerializedName("Rating")
    @Expose
    public int Rating;

    @SerializedName("cDate")
    @Expose
    public long cDate;

    @SerializedName("Message")
    @Expose
    public String Message;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 88299449)
    private transient ProductCommentDao myDao;

    public ProductComment(int user_ID, int product_ID, int rating, long cDate, String message) {
        User_ID = user_ID;
        Product_ID = product_ID;
        Rating = rating;
        this.cDate = cDate;
        Message = message;
    }

    @Generated(hash = 1773922484)
    public ProductComment(Long id, int User_ID, int Product_ID, int Rating, long cDate,
            String Message) {
        this.id = id;
        this.User_ID = User_ID;
        this.Product_ID = Product_ID;
        this.Rating = Rating;
        this.cDate = cDate;
        this.Message = Message;
    }

    @Generated(hash = 1306219854)
    public ProductComment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }

    public int getProduct_ID() {
        return Product_ID;
    }

    public void setProduct_ID(int product_ID) {
        Product_ID = product_ID;
    }

    public int getRating() {
        return Rating;
    }

    public void setRating(int rating) {
        Rating = rating;
    }

    public long getcDate() {
        return cDate;
    }

    public void setcDate(long cDate) {
        this.cDate = cDate;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public long getCDate() {
        return this.cDate;
    }

    public void setCDate(long cDate) {
        this.cDate = cDate;
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
    @Generated(hash = 1731161790)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getProductCommentDao() : null;
    }
}
