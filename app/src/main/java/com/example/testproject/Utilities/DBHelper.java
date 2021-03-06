package com.example.testproject.Utilities;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.testproject.Model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static final String DB_NAME = "TASK_DATABASE";

    private static final String TABLE_CATEGORIES = "CATEGORIES";
    private static final String CAT_NAME = "NAME";
    private static final String CAT_STATUS = "STATUS";
    private static final String CAT_ID = "ID";

    private static final String TABLE_TASKS = "TASKS";
    private static final String TASK_NAME = "NAME";
    private static final String TASK_STATUS = "STATUS";
    private static final String TASK_DUEDATE = "DUE_DATE";
    private static final String TASK_DESC = "DESCRIPTION";
    private static final String TASK_CATEGORY = "CATEGORY_NAME";
    private static final String TASK_AUDIO = "AUDIO";
    private static final String TASK_ID = "ID";

    private static final String TABLE_IMAGES = "IMAGES";
    private static final String IMAGE_ID = "ID";
    private static final String IMG_TASK_ID = "TASK_ID";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORIES + "(" + CAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + CAT_NAME + " TEXT," + CAT_STATUS + " INTEGER)");
        //db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_TASKS + "(" + TASK_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," + TASK_NAME + "TEXT," + TASK_STATUS + "TEXT," + TASK_DESC + "TEXT," + TASK_CATEGORY + "TEXT," + TASK_DUEDATE + "DATETIME," + TASK_AUDIO + "BLOB)");
        //db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_IMAGES + "(" + IMAGE_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," + IMG_TASK_ID + "INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);

        onCreate(db);
    }

    public void insertCategory(CategoryModel category){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CAT_NAME, category.getCategoryName());
        values.put(CAT_STATUS, category.getStatus());
        db.insert(TABLE_CATEGORIES,null, values);
    }

    public void deleteCategory(int id){
        db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORIES,"ID=?", new String[]{String.valueOf(id)});
    }

    public void updateCategory(int id, String catName){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CAT_STATUS, catName);
        db.update(TABLE_CATEGORIES , values , "ID=?" , new String[]{String.valueOf(id)});
    }

    public void updateStatus(int id, int status){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CAT_NAME, status);
        db.update(TABLE_CATEGORIES , values , "ID=?" , new String[]{String.valueOf(id)});
    }


    public List<CategoryModel> getAllCategories() {
        db = this.getWritableDatabase();
        Cursor cursor = null;
        List<CategoryModel> categories = new ArrayList<>();

        db.beginTransaction();
        try {
            cursor = db.query(TABLE_CATEGORIES, null, null, null, null, null, null);
            if (cursor != null){
                if (cursor.moveToFirst()){
                    do {
                        CategoryModel category = new CategoryModel();
                        category.setId(cursor.getColumnIndex(CAT_ID));
                        category.setCategoryName(cursor.getString(cursor.getColumnIndexOrThrow(CAT_NAME)));
                        category.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow(CAT_STATUS)));
                        categories.add(category);
                    }while (cursor.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            cursor.close();
        }
        return categories;
    }
}
