package fpoly.quynhph32353.duanmau.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import fpoly.quynhph32353.duanmau.Adapter.ThuThuAdapter;
import fpoly.quynhph32353.duanmau.Dao.ThuThuDao;
import fpoly.quynhph32353.duanmau.Model.ThuThu;
import fpoly.quynhph32353.duanmau.R;


public class QlThuThuFragment extends Fragment {

    View view;
    ThuThuDao thuThuDao;
    RecyclerView recyclerView;
    ArrayList<ThuThu> list = new ArrayList<>();

    ThuThuAdapter thuThuAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ql_thu_thu, container, false);
        recyclerView = view.findViewById(R.id.RecyclerView_ThuThu);
        thuThuDao = new ThuThuDao(getContext());
        list = thuThuDao.SelectAll();
        thuThuAdapter = new ThuThuAdapter(getContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(thuThuAdapter);
        return view;
    }
}