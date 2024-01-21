package fpoly.quynhph32353.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.quynhph32353.duanmau.Model.Sach;
import fpoly.quynhph32353.duanmau.R;

public class SachSpinner extends BaseAdapter {
    Context context;
    ArrayList<Sach> list;

    public SachSpinner(Context context, ArrayList<Sach> list) {
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

    private class SachViewHolder {
        TextView txt_maSach, txt_tenSach;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SachViewHolder sachViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_sach_spinner, parent, false);
            sachViewHolder = new SachViewHolder();
            sachViewHolder.txt_maSach = convertView.findViewById(R.id.txt_maSach_Spinner);
            sachViewHolder.txt_tenSach = convertView.findViewById(R.id.txt_tenSach_Spinner);
            convertView.setTag(sachViewHolder);
        } else {
            sachViewHolder = (SachViewHolder) convertView.getTag();
        }
        Sach sach = list.get(position);
        if (sach != null) {
            sachViewHolder.txt_maSach.setText(String.valueOf(sach.getMaSach()));
            sachViewHolder.txt_tenSach.setText(String.valueOf(sach.getTenSach()));
        }
        return convertView;
    }
}
