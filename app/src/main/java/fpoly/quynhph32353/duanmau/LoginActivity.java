package fpoly.quynhph32353.duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import fpoly.quynhph32353.duanmau.Dao.ThuThuDao;
import fpoly.quynhph32353.duanmau.Model.ThuThu;

public class LoginActivity extends AppCompatActivity {
    EditText edtUserName, edtPassWord;
    CheckBox chkRememberPass;
    ThuThuDao thuThuDao;
    String strUserName, strPassWord;
    Spinner spinner_role;
    String value_role;
    int role_position;


    //Kiem tra do dai username
    private boolean isLength(String str){
        return str.matches("[a-z0-9_-]{6,12}$");
    }

    //Kiem tra ky tu dac biet
    public boolean isChuoi(String str) {
        return str.matches("[a-z A-Z 0-9]+");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtUserName = findViewById(R.id.edtUsername);
        edtPassWord = findViewById(R.id.edtPassword);
        chkRememberPass = findViewById(R.id.chkRememberPass);
        spinner_role = findViewById(R.id.spinner_role);
        thuThuDao = new ThuThuDao(this);
        ArrayList<String> list = new ArrayList<>();
        list.add("Admin");
        list.add("Thủ Thư");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spinner_role.setAdapter(adapter);
        ReadFile();
        spinner_role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                role_position = position;
                value_role = list.get(position);
                Log.e("Err", "onItemSelected: "+value_role);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        findViewById(R.id.btnLogin).setOnClickListener(v -> {
            CheckLogin();
        });
        findViewById(R.id.btnCancel).setOnClickListener(v -> {
            edtUserName.setText("");
            edtPassWord.setText("");
            chkRememberPass.setChecked(false);
        });
    }

    private void ReadFile() {
        SharedPreferences sharedPreferences = getSharedPreferences("LIST_USER", MODE_PRIVATE);
        edtUserName.setText(sharedPreferences.getString("USERNAME", ""));
        edtPassWord.setText(sharedPreferences.getString("PASSWORD", ""));
        chkRememberPass.setChecked(sharedPreferences.getBoolean("REMEMBER", false));
        spinner_role.setSelection(sharedPreferences.getInt("ROLE", 0));
    }

    private void CheckLogin() {
        strUserName = edtUserName.getText().toString().trim();
        strPassWord = edtPassWord.getText().toString().trim();
        if (Validate(strUserName,strUserName)) {
            if (thuThuDao.checkLogin(strUserName, strPassWord, String.valueOf(role_position))){
                ThuThu thuThu = thuThuDao.SelectID(strUserName);
                if (thuThu.getRole() == 0) {
                    value_role = "admin";
                } else {
                    value_role = "thuthu";
                }
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                rememberUser(strUserName, strPassWord, chkRememberPass.isChecked(), role_position);
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("role", value_role);
                intent.putExtra("username",strUserName);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void rememberUser(String strUserName, String strPassWord, boolean checked, int role_position) {
        SharedPreferences sharedPreferences = getSharedPreferences("LIST_USER", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!checked) {
            editor.clear();
        } else {
            editor.putString("USERNAME", strUserName);
            editor.putString("PASSWORD", strPassWord);
            editor.putBoolean("REMEMBER", checked);
            editor.putInt("ROLE", role_position);
        }
        editor.commit();
    }
    private boolean Validate(String username,String passWord){
        boolean isCheck = true;
        if(username.isEmpty() || passWord.isEmpty()){
            Toast.makeText(this, "Vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }else if(!isChuoi(strUserName) || !isChuoi(strPassWord)){
            Toast.makeText(this, "Nhập sai định dạng , không có ký tự đặc biệt", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }
        else if(!isLength(username)){
            Toast.makeText(this, "Độ dài 6 đến 12 ký tự , không khoảng trắng và không dấu", Toast.LENGTH_SHORT).show();
            isCheck = false;
        }
        return isCheck;
    }
}