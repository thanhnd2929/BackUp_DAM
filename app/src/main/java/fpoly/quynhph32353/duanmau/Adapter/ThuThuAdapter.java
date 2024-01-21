package fpoly.quynhph32353.duanmau.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.quynhph32353.duanmau.Dao.ThuThuDao;
import fpoly.quynhph32353.duanmau.Model.ThanhVien;
import fpoly.quynhph32353.duanmau.Model.ThuThu;
import fpoly.quynhph32353.duanmau.R;

public class ThuThuAdapter extends RecyclerView.Adapter<ThuThuAdapter.ThuThuViewHolder>{
    Context context;
    ArrayList<ThuThu> list;

    ThuThuDao thuThuDao;

    EditText edt_maTT,edt_hoTen,edt_passWord;

    public ThuThuAdapter(Context context, ArrayList<ThuThu> list) {
        this.context = context;
        this.list = list;
        thuThuDao = new ThuThuDao(context);
    }

    @NonNull
    @Override
    public ThuThuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thuthu,parent,false);
        return new ThuThuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThuThuViewHolder holder, int position) {
        holder.txt_maTT.setText("Mã TT :"+list.get(position).getMaTT());
        holder.txt_hoTen.setText("Họ tên :"+list.get(position).getHoTen());
        holder.txt_passWord.setText("PassWord :"+list.get(position).getMatKhau());
        holder.img_Delete.setOnClickListener(v -> {
            showDeleteDialog(position);
        });
        holder.imgUpdate.setOnClickListener(v -> {
            showUpdateDialog(position);
        });
    }
    private void showUpdateDialog(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View DialogView = LayoutInflater.from(context).inflate(R.layout.dialog_thuthu,null);
        builder.setView(DialogView);
        AlertDialog alertDialog = builder.create();
        edt_maTT = DialogView.findViewById(R.id.edt_maTT_thuthu_dialog);
        edt_hoTen = DialogView.findViewById(R.id.edt_hoTen_thuthu_dialog);
        edt_passWord = DialogView.findViewById(R.id.edt_password_thuthu_dialog);

        edt_maTT.setEnabled(false);

        edt_maTT.setText(list.get(position).getMaTT());
        edt_passWord.setText(list.get(position).getMatKhau());
        edt_hoTen.setText(list.get(position).getHoTen());

        DialogView.findViewById(R.id.btnSave_ql_Thuthu).setOnClickListener(v -> {
            String hoTen = edt_hoTen.getText().toString().trim();
            String passWord = edt_passWord.getText().toString().trim();
            if(hoTen.isEmpty() || passWord.isEmpty()){
                Toast.makeText(context, "Vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
            }else{
                ThuThu thuThu = list.get(position);
                thuThu.setHoTen(hoTen);
                thuThu.setMatKhau(passWord);
                try {
                    if(thuThuDao.update(thuThu)){
                        Toast.makeText(context, R.string.edit_success, Toast.LENGTH_SHORT).show();
                        list.set(position,thuThu);
                        notifyDataSetChanged();
                        alertDialog.dismiss();
                    }else{
                        Toast.makeText(context, R.string.edit_not_success, Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(context, R.string.edit_not_success, Toast.LENGTH_SHORT).show();
                }
            }
        });
        DialogView.findViewById(R.id.btnCancle_ql_ThuThu).setOnClickListener(v -> {
            alertDialog.dismiss();
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }
    public void showDeleteDialog(int position) {
        ThuThu thuThu = list.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.baseline_warning_amber_24);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc chắn muốn xóa thủ thư " + thuThu.getHoTen() + " không ?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (thuThuDao.delete(thuThu)) {
                        Toast.makeText(context, R.string.delete_success, Toast.LENGTH_SHORT).show();
                        list.remove(thuThu);
                        notifyItemChanged(position);
                        notifyItemRemoved(position);
                    } else {
                        Toast.makeText(context, R.string.delete_not_success, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(context, R.string.delete_not_success, Toast.LENGTH_SHORT).show();
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
    class ThuThuViewHolder extends RecyclerView.ViewHolder{
        TextView txt_maTT,txt_hoTen,txt_passWord;
        ImageView imgUpdate,img_Delete;
        public ThuThuViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_maTT = itemView.findViewById(R.id.txt_maTT_item_thuThu);
            txt_hoTen = itemView.findViewById(R.id.txt_hoTen_item_thuThu);
            txt_passWord = itemView.findViewById(R.id.txt_password_item_thuthu);
            imgUpdate = itemView.findViewById(R.id.imgUpdate_thuthu);
            img_Delete = itemView.findViewById(R.id.imgDelete_thuthu);
        }
    }
}
