package com.example.toeicvocabulary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicvocabulary.Adapter.CourseAdapter;
import com.example.toeicvocabulary.Adapter.VocabHomeAdapter;
import com.example.toeicvocabulary.Database.DatabaseHelper;
import com.example.toeicvocabulary.Model.Course;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private CourseAdapter adapter;
    DatabaseHelper database_hp;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);

        database_hp = new DatabaseHelper(getContext(), "ToeicVocab.sqlite", null, 1);
        database_hp.QueryData("CREATE TABLE IF NOT EXISTS ListWords(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(200), type VARCHAR(200), mean VARCHAR(200))");
        database_hp.QueryData("CREATE TABLE IF NOT EXISTS Word(Id INTEGER PRIMARY KEY AUTOINCREMENT, tu VARCHAR(200), listid VARCHAR(200), mean VARCHAR(200), pronunc, sound, image)");



        List<Course> courses = new ArrayList<>();
        Cursor dataNoidung =  database_hp.GetData("SELECT * FROM ListWords Where type='system' ");
        while (dataNoidung.moveToNext()){
            int id = dataNoidung.getInt(0);
            String name =  dataNoidung.getString(1);
            String type = dataNoidung.getString(2);
            String mean = dataNoidung.getString(3);

//            Toast.makeText(getContext(),""+id,Toast.LENGTH_SHORT).show();
            courses.add(new Course(id, name, mean, type));


        }
        // Thêm nhiều khóa học khác vào danh sách ở đây...


        // Khởi tạo và thiết lập Adapter cho RecyclerView
        this.adapter = new CourseAdapter(courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Course selectedCourse = courses.get(position);


                Intent intent = new Intent(getActivity(), WordActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("course_id", selectedCourse.getId());
                bundle.putString("course_name",selectedCourse.getTitle());
                intent.putExtra("listword",bundle);
                startActivity(intent);
            }
        });






        return view;




    }
}
