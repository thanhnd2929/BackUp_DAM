package fpoly.quynhph32353.duanmau.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import fpoly.quynhph32353.duanmau.Dao.ThuThuDao;
import fpoly.quynhph32353.duanmau.Model.ThuThu;
import fpoly.quynhph32353.duanmau.R;

public class ChangePassWordFragment extends Fragment {
    View view;
    EditText edt_passWordOld, edt_passWordNew, edt_confirmPassWord;
    private boolean isChuoi(String str) {
        return str.matches("[a-zA-Z0-9]+");
    }
    SharedPreferences sharedPreferences;
    ThuThuDao thuThuDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_re_pass_word, container, false);
        edt_passWordOld = view.findViewById(R.id.edtOldPassword);
        edt_passWordNew = view.findViewById(R.id.edtnewPassword);
        edt_confirmPassWord = view.findViewById(R.id.edtRePassword);
        thuThuDao = new ThuThuDao(getContext());
        sharedPreferences = getContext().getSharedPreferences("LIST_USER", Context.MODE_PRIVATE);
        view.findViewById(R.id.btnSave_changePass).setOnClickListener(v -> {
            String passWordOld = edt_passWordOld.getText().toString().trim();
            String passWordNew = edt_passWordNew.getText().toString().trim();
            String RePassWord = edt_confirmPassWord.getText().toString().trim();
            if (validate(passWordOld, passWordNew, RePassWord)) {
                String userName = sharedPreferences.getString("USERNAME", "");
                ThuThu thuThu = thuThuDao.SelectID(userName);
                thuThu.setMatKhau(passWordNew);
                if (thuThuDao.updatePass(thuThu)) {
                    Toast.makeText(getContext(), "Thay đổi thành công ", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("PASSWORD", passWordNew);
                    editor.apply();
                    clearFrom();
                } else {
                    Toast.makeText(getContext(), "Thay đổi thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.findViewById(R.id.btnCancle_changePass).setOnClickListener(v -> {
            clearFrom();
        });
        return view;
    }

    private void clearFrom() {
        edt_passWordNew.setText("");
        edt_passWordOld.setText("");
        edt_confirmPassWord.setText("");
    }

    private boolean validate(String passWordOld, String passWordNew, String rePassWord) {
        if (passWordOld.isEmpty() || passWordNew.isEmpty() || rePassWord.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isChuoi(passWordOld) || !isChuoi(passWordNew) || !isChuoi(rePassWord)) {
            Toast.makeText(getContext(), "Nhập sai định dạng", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            String pass_Old = sharedPreferences.getString("PASSWORD", "");
            if (!passWordOld.equals(pass_Old)) {
                Toast.makeText(getContext(), "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!passWordNew.equals(rePassWord)) {
                Toast.makeText(getContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}