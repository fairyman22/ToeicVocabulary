package com.example.toeicvocabulary;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicvocabulary.Adapter.CourseAdapter;
import com.example.toeicvocabulary.Model.Course;

import java.util.List;

public class ChooseListDialogFragment extends DialogFragment {
    private RecyclerView recyclerView;
    private List<Course> listVocabData;
    private OnListSelectedListener mListener;
    public interface OnListSelectedListener {
        void onListSelected(Course selectedCourse);
    }
    public ChooseListDialogFragment(List<Course> listVocabData) {
        this.listVocabData = listVocabData;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getDialog() != null) {
            // Lấy cửa sổ dialog và cài đặt các thuộc tính
            Window window = getDialog().getWindow();

            // Thiết lập các thuộc tính cửa sổ dialog
            if (window != null) {
                WindowManager.LayoutParams params = window.getAttributes();
                params.width = (int) (Resources.getSystem().getDisplayMetrics().widthPixels * 0.8);
                window.setAttributes(params);
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_list_dialog_layout, container, false);
        recyclerView = view.findViewById(R.id.recycler_dialog);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        CourseAdapter adapter = new CourseAdapter(this.listVocabData);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Course selectedList = listVocabData.get(position);
                mListener.onListSelected(selectedList);
                dismiss();
            }
        } );

        return view;
    }
    public void setOnListSelectedListener(OnListSelectedListener listener) {
        mListener = listener;
    }

    // Triển khai logic hiển thị danh sách listvocab và xử lý lựa chọn

    // Khi người dùng chọn một listvocab

}
