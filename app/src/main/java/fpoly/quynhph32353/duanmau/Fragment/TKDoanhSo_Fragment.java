package fpoly.quynhph32353.duanmau.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import fpoly.quynhph32353.duanmau.Dao.ThongKeDao;
import fpoly.quynhph32353.duanmau.R;


public class TKDoanhSo_Fragment extends Fragment {
    View view;
    EditText edt_tuNgay, edt_denNgay;
    TextView txt_DoanhThu;
    ThongKeDao thongKeDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tk_doanh_so_, container, false);
        edt_tuNgay = view.findViewById(R.id.edt_TuNgay);
        edt_denNgay = view.findViewById(R.id.edt_DenNgay);
        txt_DoanhThu = view.findViewById(R.id.txt_DoanhThu);
        thongKeDao = new ThongKeDao(getContext());

        view.findViewById(R.id.btn_TuNgay).setOnClickListener(v -> {
            showDatePickerDialog(edt_tuNgay);
        });
        view.findViewById(R.id.btn_DenNgay).setOnClickListener(v -> {
            showDatePickerDialog(edt_denNgay);
        });
        view.findViewById(R.id.btn_find).setOnClickListener(v -> {
            String tuNgay = edt_tuNgay.getText().toString();
            String denNgay = edt_denNgay.getText().toString();

            if (!tuNgay.isEmpty() && !denNgay.isEmpty()) {
                txt_DoanhThu.setText(thongKeDao.DoanhThu(tuNgay, denNgay) + " VND");
            } else {
                // Xử lý khi người dùng không nhập đầy đủ ngày
                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ ngày", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void showDatePickerDialog(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, yearSelected, monthOfYear, dayOfMonthSelected) -> {
                    Calendar selectedDateCalendar = Calendar.getInstance();
                    selectedDateCalendar.set(yearSelected, monthOfYear, dayOfMonthSelected);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String selectedDate = sdf.format(selectedDateCalendar.getTime());
                    editText.setText(selectedDate);
                },
                year,
                month,
                dayOfMonth
        );
        datePickerDialog.show();
    }
}