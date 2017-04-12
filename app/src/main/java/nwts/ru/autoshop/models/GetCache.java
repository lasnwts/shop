package nwts.ru.autoshop.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 *  Модель для хранения запросов к серверу.
 *  Если запрос сделан менее определнного значения назад, то информация сразу берется из кэща.
 * Created by пользователь on 11.04.2017.
 */

@Entity(active = true)
public class GetCache {

    @Id
    private Long id;

    private String fieldGet;
    private long dateTime;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 693705827)
    private transient GetCacheDao myDao;

    public GetCache() {
    }

    public GetCache(String fieldGet, long dateTime) {
        this.fieldGet = fieldGet;
        this.dateTime = dateTime;
    }

    @Generated(hash = 49229419)
    public GetCache(Long id, String fieldGet, long dateTime) {
        this.id = id;
        this.fieldGet = fieldGet;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldGet() {
        return fieldGet;
    }

    public void setFieldGet(String fieldGet) {
        this.fieldGet = fieldGet;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
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
    @Generated(hash = 1789747213)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getGetCacheDao() : null;
    }
}
