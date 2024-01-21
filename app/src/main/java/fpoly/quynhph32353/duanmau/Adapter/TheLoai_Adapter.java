package fpoly.quynhph32353.duanmau.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import fpoly.quynhph32353.duanmau.Dao.TheLoaiDao;
import fpoly.quynhph32353.duanmau.Interface.ItemClickListener;
import fpoly.quynhph32353.duanmau.Model.TheLoai;
import fpoly.quynhph32353.duanmau.R;

public class TheLoai_Adapter extends RecyclerView.Adapter<TheLoai_Adapter.TheLoaiViewHolder> {
    Context context;
    ArrayList<TheLoai> list;

    private ItemClickListener itemClickListener;

    TheLoaiDao theLoaiDao;

    private static final String TAG = "TheLoai_Adapter";

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }


    public TheLoai_Adapter(Context context, ArrayList<TheLoai> list) {
        this.context = context;
        this.list = list;
        theLoaiDao = new TheLoaiDao(context);
    }

    @NonNull
    @Override
    public TheLoaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_theloai, parent, false);
        return new TheLoaiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheLoaiViewHolder holder, int position) {
        holder.itemView.setOnLongClickListener(v -> {
            try {
                if (itemClickListener != null) {
                    itemClickListener.UpdateItem(position);
                }
            } catch (Exception e) {
                Log.e(TAG, "onBindViewHolder: " + e);
            }
            return false;
        });
        holder.txt_maLoai.setText("Mã loại :" + list.get(position).getMaLoai());
        holder.txt_ten_loai.setText("Tên loại :" + list.get(position).getTenLoai());
        holder.imgDelete.setOnClickListener(v -> {
            showDeleteDialog(position);
        });
    }


    public void showDeleteDialog(int position) {
        TheLoai theLoai = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.baseline_warning_amber_24);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa " + theLoai.getTenLoai() + " không ?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (theLoaiDao.Delete(theLoai)) {
                        Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                        list.remove(theLoai);
                        notifyItemChanged(position);
                        notifyItemRemoved(position);
                    } else {
                        Toast.makeText(context, R.string.delete_not_success, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(context, R.string.delete_not_success_for, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TheLoaiViewHolder extends RecyclerView.ViewHolder {

        TextView txt_maLoai, txt_ten_loai;
        ImageView imgDelete;

        public TheLoaiViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_maLoai = itemView.findViewById(R.id.txt_maLoai_item);
            txt_ten_loai = itemView.findViewById(R.id.txt_tenLoai_item);
            imgDelete = itemView.findViewById(R.id.imgDelete_theLoai);
        }
    }
}
