package nwts.ru.autoshop.models;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import nwts.ru.autoshop.models.CategoryItem;
import nwts.ru.autoshop.models.GetCache;
import nwts.ru.autoshop.models.network.BalanceModel;
import nwts.ru.autoshop.models.network.CabinetModel;
import nwts.ru.autoshop.models.network.cart.CartModel;
import nwts.ru.autoshop.models.network.cart.CartModelBay;
import nwts.ru.autoshop.models.network.cart.ErrorModel;
import nwts.ru.autoshop.models.network.OrderModel;
import nwts.ru.autoshop.models.ProductCategory;
import nwts.ru.autoshop.models.ProductDetailImage;
import nwts.ru.autoshop.models.SubCategoryItem;

import nwts.ru.autoshop.models.CategoryItemDao;
import nwts.ru.autoshop.models.GetCacheDao;
import nwts.ru.autoshop.models.network.BalanceModelDao;
import nwts.ru.autoshop.models.network.CabinetModelDao;
import nwts.ru.autoshop.models.network.cart.CartModelDao;
import nwts.ru.autoshop.models.network.cart.CartModelBayDao;
import nwts.ru.autoshop.models.network.cart.ErrorModelDao;
import nwts.ru.autoshop.models.network.OrderModelDao;
import nwts.ru.autoshop.models.ProductCategoryDao;
import nwts.ru.autoshop.models.ProductDetailImageDao;
import nwts.ru.autoshop.models.SubCategoryItemDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig categoryItemDaoConfig;
    private final DaoConfig getCacheDaoConfig;
    private final DaoConfig balanceModelDaoConfig;
    private final DaoConfig cabinetModelDaoConfig;
    private final DaoConfig cartModelDaoConfig;
    private final DaoConfig cartModelBayDaoConfig;
    private final DaoConfig errorModelDaoConfig;
    private final DaoConfig orderModelDaoConfig;
    private final DaoConfig productCategoryDaoConfig;
    private final DaoConfig productDetailImageDaoConfig;
    private final DaoConfig subCategoryItemDaoConfig;

    private final CategoryItemDao categoryItemDao;
    private final GetCacheDao getCacheDao;
    private final BalanceModelDao balanceModelDao;
    private final CabinetModelDao cabinetModelDao;
    private final CartModelDao cartModelDao;
    private final CartModelBayDao cartModelBayDao;
    private final ErrorModelDao errorModelDao;
    private final OrderModelDao orderModelDao;
    private final ProductCategoryDao productCategoryDao;
    private final ProductDetailImageDao productDetailImageDao;
    private final SubCategoryItemDao subCategoryItemDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        categoryItemDaoConfig = daoConfigMap.get(CategoryItemDao.class).clone();
        categoryItemDaoConfig.initIdentityScope(type);

        getCacheDaoConfig = daoConfigMap.get(GetCacheDao.class).clone();
        getCacheDaoConfig.initIdentityScope(type);

        balanceModelDaoConfig = daoConfigMap.get(BalanceModelDao.class).clone();
        balanceModelDaoConfig.initIdentityScope(type);

        cabinetModelDaoConfig = daoConfigMap.get(CabinetModelDao.class).clone();
        cabinetModelDaoConfig.initIdentityScope(type);

        cartModelDaoConfig = daoConfigMap.get(CartModelDao.class).clone();
        cartModelDaoConfig.initIdentityScope(type);

        cartModelBayDaoConfig = daoConfigMap.get(CartModelBayDao.class).clone();
        cartModelBayDaoConfig.initIdentityScope(type);

        errorModelDaoConfig = daoConfigMap.get(ErrorModelDao.class).clone();
        errorModelDaoConfig.initIdentityScope(type);

        orderModelDaoConfig = daoConfigMap.get(OrderModelDao.class).clone();
        orderModelDaoConfig.initIdentityScope(type);

        productCategoryDaoConfig = daoConfigMap.get(ProductCategoryDao.class).clone();
        productCategoryDaoConfig.initIdentityScope(type);

        productDetailImageDaoConfig = daoConfigMap.get(ProductDetailImageDao.class).clone();
        productDetailImageDaoConfig.initIdentityScope(type);

        subCategoryItemDaoConfig = daoConfigMap.get(SubCategoryItemDao.class).clone();
        subCategoryItemDaoConfig.initIdentityScope(type);

        categoryItemDao = new CategoryItemDao(categoryItemDaoConfig, this);
        getCacheDao = new GetCacheDao(getCacheDaoConfig, this);
        balanceModelDao = new BalanceModelDao(balanceModelDaoConfig, this);
        cabinetModelDao = new CabinetModelDao(cabinetModelDaoConfig, this);
        cartModelDao = new CartModelDao(cartModelDaoConfig, this);
        cartModelBayDao = new CartModelBayDao(cartModelBayDaoConfig, this);
        errorModelDao = new ErrorModelDao(errorModelDaoConfig, this);
        orderModelDao = new OrderModelDao(orderModelDaoConfig, this);
        productCategoryDao = new ProductCategoryDao(productCategoryDaoConfig, this);
        productDetailImageDao = new ProductDetailImageDao(productDetailImageDaoConfig, this);
        subCategoryItemDao = new SubCategoryItemDao(subCategoryItemDaoConfig, this);

        registerDao(CategoryItem.class, categoryItemDao);
        registerDao(GetCache.class, getCacheDao);
        registerDao(BalanceModel.class, balanceModelDao);
        registerDao(CabinetModel.class, cabinetModelDao);
        registerDao(CartModel.class, cartModelDao);
        registerDao(CartModelBay.class, cartModelBayDao);
        registerDao(ErrorModel.class, errorModelDao);
        registerDao(OrderModel.class, orderModelDao);
        registerDao(ProductCategory.class, productCategoryDao);
        registerDao(ProductDetailImage.class, productDetailImageDao);
        registerDao(SubCategoryItem.class, subCategoryItemDao);
    }
    
    public void clear() {
        categoryItemDaoConfig.clearIdentityScope();
        getCacheDaoConfig.clearIdentityScope();
        balanceModelDaoConfig.clearIdentityScope();
        cabinetModelDaoConfig.clearIdentityScope();
        cartModelDaoConfig.clearIdentityScope();
        cartModelBayDaoConfig.clearIdentityScope();
        errorModelDaoConfig.clearIdentityScope();
        orderModelDaoConfig.clearIdentityScope();
        productCategoryDaoConfig.clearIdentityScope();
        productDetailImageDaoConfig.clearIdentityScope();
        subCategoryItemDaoConfig.clearIdentityScope();
    }

    public CategoryItemDao getCategoryItemDao() {
        return categoryItemDao;
    }

    public GetCacheDao getGetCacheDao() {
        return getCacheDao;
    }

    public BalanceModelDao getBalanceModelDao() {
        return balanceModelDao;
    }

    public CabinetModelDao getCabinetModelDao() {
        return cabinetModelDao;
    }

    public CartModelDao getCartModelDao() {
        return cartModelDao;
    }

    public CartModelBayDao getCartModelBayDao() {
        return cartModelBayDao;
    }

    public ErrorModelDao getErrorModelDao() {
        return errorModelDao;
    }

    public OrderModelDao getOrderModelDao() {
        return orderModelDao;
    }

    public ProductCategoryDao getProductCategoryDao() {
        return productCategoryDao;
    }

    public ProductDetailImageDao getProductDetailImageDao() {
        return productDetailImageDao;
    }

    public SubCategoryItemDao getSubCategoryItemDao() {
        return subCategoryItemDao;
    }

}
