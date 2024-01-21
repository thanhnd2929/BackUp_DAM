package fpoly.quynhph32353.duanmau.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import fpoly.quynhph32353.duanmau.Adapter.TheLoai_Adapter;
import fpoly.quynhph32353.duanmau.Dao.TheLoaiDao;
import fpoly.quynhph32353.duanmau.Interface.ItemClickListener;
import fpoly.quynhph32353.duanmau.Model.TheLoai;
import fpoly.quynhph32353.duanmau.R;

public class QlTheLoaiFragment extends Fragment {

    View view;

    RecyclerView recyclerView;

    TheLoaiDao theLoaiDao;

    EditText edt_maLoai, edt_tenloai;


    ArrayList<TheLoai> list = new ArrayList<>();

    public static final String TAG = "QlTheLoai";
    TheLoai_Adapter theLoaiAdapter;

    public boolean isChuoi(String str) {
        return str.matches("[a-zA-Z0-9]+");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_the_loai, container, false);
        recyclerView = view.findViewById(R.id.RecyclerView_TheLoai);
        theLoaiDao = new TheLoaiDao(getContext());
        list = theLoaiDao.selectAll();
        theLoaiAdapter = new TheLoai_Adapter(getContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(theLoaiAdapter);
        view.findViewById(R.id.fab_TheLoai).setOnClickListener(v -> {
            showAddOrEditDialog_Tl(getContext(), 0, null);
        });
        theLoaiAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void UpdateItem(int position) {
                TheLoai theLoai = list.get(position);
                showAddOrEditDialog_Tl(getContext(), 1, theLoai);
            }
        });
        return view;
    }

    protected void showAddOrEditDialog_Tl(Context context, int type, TheLoai theLoai) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        View mView = LayoutInflater.from(context).inflate(R.layout.dialog_theloai, null);
        builder.setView(mView);
        AlertDialog alertDialog = builder.create();
        edt_maLoai = mView.findViewById(R.id.edt_maLoai);
        edt_tenloai = mView.findViewById(R.id.edt_tenLoai);
        edt_maLoai.setEnabled(false);//vô hiêu hóa tương tác với người dùng

        if (type != 0) {//Update đổ dữ liệu người dùng lên dialog
            edt_maLoai.setText(String.valueOf(theLoai.getMaLoai()));
            edt_tenloai.setText(String.valueOf(theLoai.getTenLoai()));
        }
        mView.findViewById(R.id.btnCancle_ql_TheLoai).setOnClickListener(v -> {
            alertDialog.dismiss();
        });
        mView.findViewById(R.id.btnSave_ql_TheLoai).setOnClickListener(v -> {
            String tenLoai = edt_tenloai.getText().toString();//Lấy input từ người dùng
            if (Validate(tenLoai)) {
                if (type == 0) {
                    TheLoai theLoainew = new TheLoai();
                    theLoainew.setTenLoai(tenLoai);
                    try {
                        if (theLoaiDao.insertData(theLoainew)) {
                            Toast.makeText(context, R.string.add_success, Toast.LENGTH_SHORT).show();
                            list.add(theLoainew);
                            theLoaiAdapter.notifyDataSetChanged();
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(context, R.string.add_not_success, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Lỗi khi thao tác với cơ sở dữ liệu", e);
                        Toast.makeText(context, R.string.edit_not_success, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    theLoai.setTenLoai(tenLoai);
                    try {
                        if (theLoaiDao.Update(theLoai)) {
                            Toast.makeText(context, R.string.edit_success, Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                            update();
                        } else {
                            Toast.makeText(context, R.string.edit_not_success, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Lỗi khi thao tác với cơ sở dữ liệu", e);
                        Toast.makeText(context, R.string.edit_not_success, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    private void update() {
        list.clear();
        list.addAll(theLoaiDao.selectAll());
        theLoaiAdapter.notifyDataSetChanged();
    }

    private boolean Validate(String username) {
        boolean isCheck = true;
        if (username.isEmpty()) { //kiểm tra xem người dùng có bỏ trống thông tin không
            Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            isCheck = false;
        } else if (!isChuoi(username)) { //Check regex chuỗi
            Toast.makeText(getContext(), "Vui lòng nhập đúng định dạng", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }
        return isCheck;
    }
}