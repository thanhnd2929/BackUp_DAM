package fpoly.quynhph32353.duanmau.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import fpoly.quynhph32353.duanmau.Database.Db_Helper;
import fpoly.quynhph32353.duanmau.Model.Sach;
import fpoly.quynhph32353.duanmau.Model.Top;

public class ThongKeDao {
    Db_Helper dbHelper;
    Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public ThongKeDao(Context context) {
        this.context = context;
        dbHelper = new Db_Helper(context);
    }

    //Thong ke Top 10
    public ArrayList<Top> getTop() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String sql = "SELECT maSach ,Count(maSach) as soLuong FROM PhieuMuon GROUP BY maSach ORDER BY soLuong DESC LIMIT 10";
        ArrayList<Top> list = new ArrayList<>();
        SachDao sachDao = new SachDao(context);
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Top top = new Top();
                Sach sach = sachDao.selectID(cursor.getString(cursor.getColumnIndexOrThrow("maSach")));
                top.tenSach = sach.getTenSach();
                top.soLuong = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("soLuong")));
                list.add(top);
            } while (cursor.moveToNext());

        }
        return list;
    }

    //    public int DoanhThu(String tuNgay, String denNgay) {
//        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
//        String sql = "SELECT SUM(tienThue) as doanhThu FROM PhieuMuon WHERE ngay BETWEEN ? AND ?";
//        String dk[] = {tuNgay, denNgay};
//        ArrayList<Integer> list = new ArrayList<>();
//        Cursor cursor = sqLiteDatabase.rawQuery(sql, dk);
//        while (cursor.moveToNext()) {
//            try {
//                list.add(Integer.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("doanhThu"))));
//            } catch (Exception e) {
//                list.add(0);
//            }
//        }
//        return list.get(0);
//    }
    public int DoanhThu(String tuNgay, String denNgay) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String sql = "SELECT SUM(tienThue) as doanhThu FROM PhieuMuon WHERE ngay BETWEEN ? AND ?";
        String dk[] = {tuNgay, denNgay};
        int doanhThu = 0;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, dk);
        if (cursor.moveToFirst()) {
            try {
                doanhThu = cursor.getInt(cursor.getColumnIndexOrThrow("doanhThu"));
            } catch (Exception e) {
                doanhThu = 0;
            }
        }
        cursor.close();
        return doanhThu;
    }
}
