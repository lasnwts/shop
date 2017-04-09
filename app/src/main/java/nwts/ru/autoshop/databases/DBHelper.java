package nwts.ru.autoshop.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import nwts.ru.autoshop.models.CategoryItem;
import nwts.ru.autoshop.setting.BaseConstant;
import nwts.ru.autoshop.setting.PreferenceHelper;

import static android.content.ContentValues.TAG;

/**
 * Created by пользователь on 13.03.2017.
 */


public class DBHelper extends SQLiteOpenHelper implements DBTables {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ecomexample";
    // public static final int TABLE = 1;

    // создаем объект для данных
    ContentValues cv;
    PreferenceHelper preferenceHelper;


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_FLOWERS);
        db.execSQL(SQL_CREATE_ENTRIES_USERS);
        db.execSQL(SQL_CREATE_ENTRIES_CATEGORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public List<CategoryItem> getListCategoryItems() {
        List<CategoryItem> categoryItems = new ArrayList<>();
        Cursor cursor = getReadableDatabase().query(TABLE_CATEGORY, null, null, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
//            int idColIndex = cursor.getColumnIndex(SQL_ID);
            int categoryIdColIndex = cursor.getColumnIndex(SQL_CATEGORY_ID);
            int categoryNameColIndex = cursor.getColumnIndex(SQL_CATEGORY_NAME);
            int categoryImageColIndex = cursor.getColumnIndex(SQL_CATEGORY_IMAGE);
//            int refreshTimeColIndex = cursor.getColumnIndex(SQL_REFRESH_TIME);
            int i = 0; //number objects
            do {
                categoryItems.add(i, new CategoryItem(cursor.getInt(categoryIdColIndex),
                        cursor.getString(categoryNameColIndex), cursor.getString(categoryImageColIndex)));
                i++;
            } while (cursor.moveToNext());
        } else {
            Log.d(TAG, "0 rows");
            //int productId, String name, String category, String instructions, double price, String photo
            categoryItems.add(0, new CategoryItem(0, 0, "data not loaded", "none category data", 0));
        }
        cursor.close();
        return categoryItems;
    }

    //Delete all record to Users
    public void dbDeleteCategory() {
        SQLiteDatabase database = getWritableDatabase();
        int deleteCount = database.delete(TABLE_CATEGORY, null, null);
        database.close();
        Log.d(TAG, "Delete count =" + " delete:" + deleteCount);
    }


    public boolean putCategory(int categoryId, String categoryName, String categoryImage, long refreshTime){
        if (categoryName == null && categoryImage == null){
            return false;
        }
        cv = new ContentValues();
        cv.put(SQL_CATEGORY_ID,categoryId);
        cv.put(SQL_CATEGORY_NAME,categoryName);
        cv.put(SQL_CATEGORY_IMAGE,categoryImage);
        cv.put(SQL_REFRESH_TIME,refreshTime);
        SQLiteDatabase database = getWritableDatabase();
        long rowID = database.insert(TABLE_CATEGORY, null, cv);
        database.close();
        Log.d(TAG, "row inserted, ID =" + rowID);
        if (rowID > 0) {
            return true;
        } else {
            return false;
        }
    }

    /*
    Далее идет старый код
     */

    public int dbReadInLog() {
        Log.d(BaseConstant.TAG, "--- Rows in mytable: ---");
        // делаем запрос всех данных из таблицы mytable, получаем Cursor
        Cursor c = getReadableDatabase().query(TABLE_CATEGORY, null, null, null, null, null, null);
        int countRet;
        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex(SQL_ID);
            int categoryIdColIndex = c.getColumnIndex(SQL_CATEGORY_ID);
            int categoryNameColIndex = c.getColumnIndex(SQL_CATEGORY_NAME);
            int categoryImageColIndex = c.getColumnIndex(SQL_CATEGORY_IMAGE);

            do {
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d(BaseConstant.TAG,
                        "ID = " + c.getInt(idColIndex) +
                                ", Category_ID = " + c.getString(categoryIdColIndex) +
                                ", Category_Name = " + c.getString(categoryNameColIndex) +
                                ", Category_Image = " + c.getString(categoryImageColIndex));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
            Log.d(BaseConstant.TAG, "Column names = 1 = " + c.getColumnName(0));
            countRet = c.getCount();
        } else {
            Log.d(BaseConstant.TAG, "0 rows");
            countRet = 0;
        }
        c.close();
        return countRet;
    }

    public long dbGetCategoryTimeRefresh(){
        Cursor c = getReadableDatabase().query(TABLE_CATEGORY, null, null, null, null, null, null);
        long timeRet;

        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex(SQL_ID);
            int categoryRefreshTimeColIndex = c.getColumnIndex(SQL_REFRESH_TIME);
            timeRet = c.getLong(categoryRefreshTimeColIndex);
        } else {
            Log.d(BaseConstant.TAG, "0 rows");
            timeRet = 0;
        }
        c.close();
        return timeRet;
    }


    public int dbReadInLogFlowers() {
        Log.d(BaseConstant.TAG, "--- Rows in table: ---" + TABLE_FLOWERS);
        // делаем запрос всех данных из таблицы mytable, получаем Cursor
        Cursor c = getReadableDatabase().query(TABLE_FLOWERS, null, null, null, null, null, null);
        int countRet;
        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex(KEY_ID);
            int productIdColIndex = c.getColumnIndex(FLOWER_PRODUCT_ID);
            int nameColIndex = c.getColumnIndex(FLOWER_NAME);
            int categoryColIndex = c.getColumnIndex(FLOWER_CATEGORY);
            int instructColIndex = c.getColumnIndex(FLOWER_INSTRUCTIONS);
            int priceColIndex = c.getColumnIndex(FLOWER_PRICE);
            int photoColIndex = c.getColumnIndex(FLOWER_PHOTO);

            do {
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d(BaseConstant.TAG,
                        "ID = " + c.getInt(idColIndex) +
                                ", productId = " + c.getString(productIdColIndex) +
                                ", name = " + c.getString(nameColIndex) +
                                ", category = " + c.getString(categoryColIndex) +
                                ", instructions = " + c.getString(instructColIndex) +
                                ", price = " + c.getString(priceColIndex) +
                                ", photo = " + c.getString(photoColIndex));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
            Log.d(BaseConstant.TAG, "Column names = 1 = " + c.getColumnName(0));
            countRet = c.getCount();
        } else {
            Log.d(BaseConstant.TAG, "0 rows");
            countRet = 0;
        }
        c.close();
        return countRet;
    }

    //конвертируем Bitmap на Base64
    public String convertToBase64(Bitmap bitmap) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        byte[] byteArray = os.toByteArray();
        return Base64.encodeToString(byteArray, 0);
    }

    //и наоборот
    public Bitmap convertToBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap bitmapResult = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return bitmapResult;
    }

}
