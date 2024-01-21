package fpoly.quynhph32353.duanmau.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.quynhph32353.duanmau.Model.Top;
import fpoly.quynhph32353.duanmau.R;

public class Top10Adapter extends BaseAdapter {

    Context context;
    ArrayList<Top> list;

    public Top10Adapter(Context context, ArrayList<Top> list) {
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

    private class Top10ViewHolder {
        TextView txt_tenSach, txt_soLuong;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Top10ViewHolder top10ViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_top10,parent,false);
            top10ViewHolder = new Top10ViewHolder();
            top10ViewHolder.txt_tenSach = convertView.findViewById(R.id.txt_tenSach_Top10);
            top10ViewHolder.txt_soLuong = convertView.findViewById(R.id.txt_soLuong_Top10);
            convertView.setTag(top10ViewHolder);
        } else {
            top10ViewHolder = (Top10ViewHolder) convertView.getTag();
        }
        Top top = list.get(position);
        if (top != null) {
            top10ViewHolder.txt_tenSach.setText("Tên Sách :" + list.get(position).tenSach);
            top10ViewHolder.txt_soLuong.setText("Số lượng :" + list.get(position).soLuong);
        }
        return convertView;
    }
}
