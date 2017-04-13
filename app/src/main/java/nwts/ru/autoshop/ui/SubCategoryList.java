package nwts.ru.autoshop.ui;

import android.app.Application;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import nwts.ru.autoshop.R;
import nwts.ru.autoshop.TODOApplication;
import nwts.ru.autoshop.fragment.Category;
import nwts.ru.autoshop.fragment.SubCatalog;
import nwts.ru.autoshop.setting.PreferenceHelper;

public class SubCategoryList extends AppCompatActivity {

    FragmentManager fragmentManager;
    PreferenceHelper preferenceHelper;
    private Application application;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category_list);
        PreferenceHelper.getInstance().init(getApplicationContext());
        preferenceHelper = PreferenceHelper.getInstance();
        fragmentManager = getFragmentManager();
        application = getApplication();

        getFragmentSubCategory();
    }

    private void getFragmentSubCategory(){
        SubCatalog catalog = new SubCatalog();
        fragmentManager.beginTransaction().replace(R.id.content_frame_SubCategory, catalog).commit();
    }
}
