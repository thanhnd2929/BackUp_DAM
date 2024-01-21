package fpoly.quynhph32353.duanmau.Model;

public class ThuThu {
    private String maTT;
    private String hoTen;
    private String matKhau;

    private int role;

    public ThuThu(String maTT, String hoTen, String matKhau,int role) {
        this.maTT = maTT;
        this.hoTen = hoTen;
        this.matKhau = matKhau;
        this.role = role;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
