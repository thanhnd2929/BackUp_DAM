package fpoly.quynhph32353.duanmau.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.quynhph32353.duanmau.Database.Db_Helper;
import fpoly.quynhph32353.duanmau.Model.ThanhVien;

public class ThanhVienDao {
    Db_Helper dbHelper;
    public static final String TABLE_NAME = "ThanhVien";
    public static final String COLUMN_MA_TV = "maTV";
    public static final String COLUMN_TEN_TV = "hoTen";
    public static final String COLUMN_NAMSINH_TV = "namSinh";

    public ThanhVienDao(Context context) {
        dbHelper = new Db_Helper(context);
    }

    public boolean insertData(ThanhVien obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TEN_TV, obj.getHoTen());
        contentValues.put(COLUMN_NAMSINH_TV, obj.getNamSinh());
        long check = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        obj.setMaTV((int) check);
        return check != -1;
    }

    public boolean Delete(ThanhVien obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(obj.getMaTV())};
        long check = sqLiteDatabase.delete(TABLE_NAME, COLUMN_MA_TV + "=?", dk);
        return check != -1;
    }

    public boolean Update(ThanhVien obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(obj.getMaTV())};
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TEN_TV, obj.getHoTen());
        contentValues.put(COLUMN_NAMSINH_TV, obj.getNamSinh());
        long check = sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_MA_TV + "= ?", dk);
        return check != -1;
    }

    private ArrayList<ThanhVien> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<ThanhVien> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
//            cursor.moveToFirst();
//            do {
//                int maTT = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MA_TV));
//                String hoTen = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN_TV));
//                String namSinh = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMSINH_TV));
//                list.add(new ThanhVien(maTT, hoTen, namSinh));
//            } while (cursor.moveToNext());
            while ((cursor.moveToNext())) {
                int maTT = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MA_TV));
                String hoTen = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN_TV));
                String namSinh = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMSINH_TV));
                list.add(new ThanhVien(maTT, hoTen, namSinh));
            }
        }
        return list;
    }

    public ArrayList<ThanhVien> selectAll() {
        String sql = "SELECT * FROM ThanhVien";
        return getAll(sql);
    }

    //    public ThanhVien selectID(String id) {
//        String sql = "SELECT * FROM ThanhVien WHERE maTV = ?";
//        ArrayList<ThanhVien> list = getAll(sql, id);
//        return list.get(0);
//    }
    public ThanhVien selectID(String id) {
        String sql = "SELECT * FROM ThanhVien WHERE maTV = ?";
        ArrayList<ThanhVien> list = getAll(sql, id);

        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
