package fpoly.quynhph32353.duanmau.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import fpoly.quynhph32353.duanmau.Dao.ThuThuDao;
import fpoly.quynhph32353.duanmau.Model.ThuThu;
import fpoly.quynhph32353.duanmau.R;

public class ThemNguoiDungFragment extends Fragment {
    View view;

    EditText edt_username, edt_hoTen, edt_password;

    ThuThuDao thuThuDao;

    Spinner spinner_role;

    ArrayList<String> list = new ArrayList<>();

    String value_role;

    int role_position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_them_nguoi_dung, container, false);
        thuThuDao = new ThuThuDao(getContext());
        edt_hoTen = view.findViewById(R.id.edt_hoTen_add);
        edt_username = view.findViewById(R.id.edt_username_add);
        edt_password = view.findViewById(R.id.edtPassword_Add);
        spinner_role = view.findViewById(R.id.spinner_role_ThemNguoiDung);
        list.add("Admin");
        list.add("Thủ Thư");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, list);
        spinner_role.setAdapter(adapter);

        spinner_role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                role_position = position;
                value_role = list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        view.findViewById(R.id.btnSave_addTT).setOnClickListener(v -> {
            String username = edt_username.getText().toString().trim();
            String hoTen = edt_hoTen.getText().toString().trim();
            String password = edt_password.getText().toString().trim();

            if (validate(username, password, hoTen)) {
                ThuThu thuThu = new ThuThu(username, hoTen, password,role_position);
                if (thuThuDao.insertData(thuThu)) {
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    resetEditText();
                } else {
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.findViewById(R.id.btnCancle_addTT).setOnClickListener(v -> {
            resetEditText();
        });
        return view;
    }

    private boolean validate(String username, String password, String hoTen) {
        if (username.isEmpty() || password.isEmpty() || hoTen.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng cung cấp đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void resetEditText() {
        edt_username.setText("");
        edt_hoTen.setText("");
        edt_password.setText("");
    }
}