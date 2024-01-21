package fpoly.quynhph32353.duanmau.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Db_Helper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "LibMana.db";
    private static final int DATABASE_VERSION = 4;

    public Db_Helper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng thủ thư
        String createTableThuThu = "CREATE TABLE ThuThu (" +
                "maTT TEXT PRIMARY KEY, " +
                "hoTen TEXT NOT NULL, " +
                "matKhau TEXT NOT NULL," +
                "role INTEGER NOT NULL)";
        db.execSQL(createTableThuThu);

        String insertDefaultThuThu = "INSERT INTO ThuThu (maTT, hoTen, matKhau,role) VALUES ('admin', 'admin', '1',0)";
        db.execSQL(insertDefaultThuThu);

        // Tạo bảng thành viên
        String createTableThanhVien = "CREATE TABLE ThanhVien (" +
                "maTV INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "hoTen TEXT NOT NULL, " +
                "namSinh TEXT NOT NULL)";
        db.execSQL(createTableThanhVien);

        // Tạo bảng thể loại sách
        String createTableTheLoai = "CREATE TABLE TheLoai(" +
                "maLoai INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenLoai TEXT NOT NULL)";
        db.execSQL(createTableTheLoai);

        // Tạo bảng sách
        String createTableSach = "CREATE TABLE Sach (" +
                "maSach INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenSach TEXT NOT NULL, " +
                "giaSach INTEGER NOT NULL, " +
                "maLoai INTEGER NOT NULL, " +
                "FOREIGN KEY(maLoai) REFERENCES TheLoai(maLoai))";
        db.execSQL(createTableSach);

        // Tạo bảng phiếu mượn
        String createTablePhieuMuon = "CREATE TABLE PhieuMuon(" +
                "maPM INTEGER PRIMARY KEY AUTOINCREMENT," +
                "maTT TEXT NOT NULL, " +
                "maTV INTEGER NOT NULL, " +
                "maSach INTEGER NOT NULL, " +
                "tienThue INTEGER NOT NULL," +
                "ngay DATE NOT NULL," +
                "traSach INTEGER NOT NULL, " +
                "FOREIGN KEY(maTT) REFERENCES ThuThu(maTT), " +
                "FOREIGN KEY(maTV) REFERENCES ThanhVien(maTV), " +
                "FOREIGN KEY(maSach) REFERENCES Sach(maSach))";
        db.execSQL(createTablePhieuMuon);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS ThuThu");
            db.execSQL("DROP TABLE IF EXISTS ThanhVien");
            db.execSQL("DROP TABLE IF EXISTS TheLoai");
            db.execSQL("DROP TABLE IF EXISTS Sach");
            db.execSQL("DROP TABLE IF EXISTS PhieuMuon");
            onCreate(db);
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
}
