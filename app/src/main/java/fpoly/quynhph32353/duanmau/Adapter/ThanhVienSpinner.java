package fpoly.quynhph32353.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.quynhph32353.duanmau.Model.ThanhVien;
import fpoly.quynhph32353.duanmau.R;

public class ThanhVienSpinner extends BaseAdapter {
    Context context;
    ArrayList<ThanhVien> list;

    public ThanhVienSpinner(Context context, ArrayList<ThanhVien> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ThanhVienViewHolder {
        TextView txt_maTV, txt_hoTen;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ThanhVienViewHolder thanhVienViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_thanhvien_spinner, parent, false);
            thanhVienViewHolder = new ThanhVienViewHolder();
            thanhVienViewHolder.txt_maTV = convertView.findViewById(R.id.txt_MaTV_Spinner);
            thanhVienViewHolder.txt_hoTen = convertView.findViewById(R.id.txt_hoTen_Spinner);
            convertView.setTag(thanhVienViewHolder);
        } else {
            thanhVienViewHolder = (ThanhVienViewHolder) convertView.getTag();
        }
        ThanhVien thanhVien = list.get(position);
        if (thanhVien != null) {
            thanhVienViewHolder.txt_hoTen.setText(thanhVien.getHoTen());
            thanhVienViewHolder.txt_maTV.setText(String.valueOf(thanhVien.getMaTV()));
        }
        return convertView;
    }
}
