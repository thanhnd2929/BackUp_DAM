package fpoly.quynhph32353.duanmau.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.quynhph32353.duanmau.Database.Db_Helper;
import fpoly.quynhph32353.duanmau.Model.ThuThu;

public class ThuThuDao {
    Db_Helper dbHelper;

    public static final String TABLE_NAME = "ThuThu";

    public static final String COLUMN_MATT = "maTT";

    public static final String COLUMN_HO_TEN = "hoTen";

    public static final String COLUMN_MATKHAU = "matKhau";

    public static final String COLUMN_ROLE = "role";

    public ThuThuDao(Context context) {
        dbHelper = new Db_Helper(context);
    }

    public boolean insertData(ThuThu obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MATT, obj.getMaTT());
        contentValues.put(COLUMN_HO_TEN, obj.getHoTen());
        contentValues.put(COLUMN_MATKHAU, obj.getMatKhau());
        contentValues.put(COLUMN_ROLE, obj.getRole());
        long check = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return check != -1;
    }

    public boolean delete(ThuThu obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {obj.getMaTT()};
        long check = sqLiteDatabase.delete(TABLE_NAME, COLUMN_MATT + "= ?", dk);
        return check != -1;
    }

    public boolean update(ThuThu obj) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {obj.getMaTT()};
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_HO_TEN, obj.getHoTen());
        contentValues.put(COLUMN_MATKHAU, obj.getMatKhau());
        contentValues.put(COLUMN_ROLE, obj.getRole());
        long check = sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_MATT + "= ?", dk);
        return check != -1;
    }

    public boolean updatePass(ThuThu obj) {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {obj.getMaTT()};
        contentValues.put(COLUMN_HO_TEN, obj.getHoTen());
        contentValues.put(COLUMN_MATKHAU, obj.getMatKhau());
        long check = sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_MATT + "= ?", dk);
        return check != -1;
    }

    public ArrayList<ThuThu> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<ThuThu> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                String maTT = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MATT));
                String hoTen = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HO_TEN));
                String matKhau = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MATKHAU));
                int role = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROLE)));
                list.add(new ThuThu(maTT, hoTen, matKhau, role));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public ArrayList<ThuThu> SelectAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        return getAll(sql);
    }

    public ThuThu SelectID(String id) {
        String sql = "SELECT * FROM ThuThu WHERE maTT = ?";
        ArrayList<ThuThu> list = getAll(sql, id);
        return list.get(0);
    }

    public boolean checkLogin(String username, String password, String role) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM ThuThu WHERE " + COLUMN_MATT + "=? AND " + COLUMN_MATKHAU + "=? AND " + COLUMN_ROLE + " = ?";
        String[] selectionArgs = new String[]{username, password, role};
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        boolean result = cursor.getCount() > 0;
        return result;
    }
}
