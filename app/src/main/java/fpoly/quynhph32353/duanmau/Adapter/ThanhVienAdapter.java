package fpoly.quynhph32353.duanmau.Adapter;

import android.content.Context;
import android.content.DialogInterface;
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

import fpoly.quynhph32353.duanmau.Dao.ThanhVienDao;
import fpoly.quynhph32353.duanmau.Interface.ItemClickListener;
import fpoly.quynhph32353.duanmau.Model.ThanhVien;
import fpoly.quynhph32353.duanmau.R;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ThanhVienViewHolder> {
    Context context;
    ArrayList<ThanhVien> list;

    ThanhVienDao thanhVienDao;

    public ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public ThanhVienAdapter(Context context, ArrayList<ThanhVien> list) {
        thanhVienDao = new ThanhVienDao(context);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ThanhVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thanhvien, parent, false);
        return new ThanhVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhVienViewHolder holder, int position) {
        ThanhVien thanhVien = list.get(position);
        if (thanhVien != null) {
            holder.txt_hoTen.setText("Họ tên :" + thanhVien.getHoTen());
            holder.txt_maTV.setText("Mã TV :" + thanhVien.getMaTV());
            holder.txt_namSinh.setText("Năm sinh :" + thanhVien.getNamSinh());
        }
        holder.img_Delete_ThanhVien.setOnClickListener(v -> {
            showDeleteDialog(position);
        });
        holder.itemView.setOnLongClickListener(v -> {
            if(itemClickListener != null){
                itemClickListener.UpdateItem(position);
            }
            return false;
        });
    }

    public void showDeleteDialog(int position) {
        ThanhVien thanhVien = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.baseline_warning_amber_24);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa thành viên " + thanhVien.getHoTen() + " không ?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (thanhVienDao.Delete(thanhVien)) {
                        Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                        list.remove(thanhVien);
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

    class ThanhVienViewHolder extends RecyclerView.ViewHolder {

        TextView txt_hoTen, txt_maTV, txt_namSinh;

        ImageView img_Delete_ThanhVien;

        public ThanhVienViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_hoTen = itemView.findViewById(R.id.txt_hoTen_item_thanhvien);
            txt_namSinh = itemView.findViewById(R.id.txt_namsinh_item_thanhvien);
            txt_maTV = itemView.findViewById(R.id.txt_maTV_item_thanhvien);
            img_Delete_ThanhVien = itemView.findViewById(R.id.imgDelete_thanhVien);
        }
    }
}