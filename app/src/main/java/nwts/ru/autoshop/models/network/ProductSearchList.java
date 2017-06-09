package nwts.ru.autoshop.models.network;

import java.util.List;

/**
 * Created by пользователь on 09.06.2017.
 */

public class ProductSearchList {
    List<ProductSearch> mProductSearchList;

    public ProductSearchList(List<ProductSearch> productSearchList) {
        mProductSearchList = productSearchList;
    }

    public List<ProductSearch> getProductSearchList() {
        return mProductSearchList;
    }

    public void setProductSearchList(List<ProductSearch> productSearchList) {
        mProductSearchList = productSearchList;
    }
}
