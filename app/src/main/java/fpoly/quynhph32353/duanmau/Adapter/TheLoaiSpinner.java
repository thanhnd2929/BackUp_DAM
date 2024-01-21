package fpoly.quynhph32353.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.quynhph32353.duanmau.Model.TheLoai;
import fpoly.quynhph32353.duanmau.R;

public class TheLoaiSpinner extends BaseAdapter {
    Context context;
    ArrayList<TheLoai> list;

    public TheLoaiSpinner(Context context, ArrayList<TheLoai> list) {
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

    private static class TheLoaiViewHolder {
        TextView txt_maLoai;
        TextView txt_tenSach;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TheLoaiViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_theloai_spinner, parent, false);
            viewHolder = new TheLoaiViewHolder();
            viewHolder.txt_maLoai = convertView.findViewById(R.id.txt_MaLoai_Spinner);
            viewHolder.txt_tenSach = convertView.findViewById(R.id.txt_tenLoai_Spinner);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TheLoaiViewHolder) convertView.getTag();
        }
        TheLoai theLoai = list.get(position);
        viewHolder.txt_maLoai.setText(String.valueOf(theLoai.getMaLoai()));
        viewHolder.txt_tenSach.setText(theLoai.getTenLoai());
        return convertView;
    }
}
