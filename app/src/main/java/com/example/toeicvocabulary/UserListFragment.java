package com.example.toeicvocabulary;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicvocabulary.Adapter.UserCourseAdapter;
import com.example.toeicvocabulary.Database.DatabaseHelper;
import com.example.toeicvocabulary.Model.Course;

import java.util.ArrayList;
import java.util.List;

public class UserListFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserCourseAdapter userCourseAdapter;

    List<Course> userCourse;
    private DatabaseHelper database_hp;
    private Button btnAdd;
    public UserListFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userlist, container, false);
        this.recyclerView = view.findViewById(R.id.recyclerView);
        this.btnAdd = view.findViewById(R.id.btnAdd);
        this.userCourse = new ArrayList<>();
        database_hp = new DatabaseHelper(view.getContext(), "ToeicVocab.sqlite", null, 1);
        database_hp.QueryData("CREATE TABLE IF NOT EXISTS ListWords(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(200), type VARCHAR(200), mean VARCHAR(200))");
        database_hp.QueryData("CREATE TABLE IF NOT EXISTS Words(Id INTEGER PRIMARY KEY AUTOINCREMENT, word VARCHAR(200), listid VARCHAR(200), mean VARCHAR(200), pronunc VARCHAR(200), sound VARCHAR(200), image VARCHAR(200))");

//        createListCourse();

//        this.userCourseAdapter = new UserCourseAdapter(userCourse);
//        this.recyclerView.setAdapter(userCourseAdapter);
        createListCourse();
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddCourseDialog();
            }
        });
       this.userCourseAdapter.setOnItemClickListener(new UserCourseAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(int position) {
               Course selectedCourse = userCourse.get(position);

               Intent intent = new Intent(getActivity(), WordUserActivity.class);
               Bundle bundle = new Bundle();
               bundle.putInt("course_id", selectedCourse.getId());
               bundle.putString("course_name",selectedCourse.getTitle());
               intent.putExtra("listword",bundle);
               startActivity(intent);
           }
       });


        return  view;
    }
    public void createListCourse(){
        List<Course> courses = new ArrayList<>();
        Cursor dataNoidung =  database_hp.GetData("SELECT * FROM ListWords where type='user' ");
        while (dataNoidung.moveToNext()){
            int id = dataNoidung.getInt(0);
            String name =  dataNoidung.getString(1);
            String type = dataNoidung.getString(2);
            String mean = dataNoidung.getString(3);


            courses.add(new Course(id, name, mean, type));
        }
        this.userCourse = courses;
        this.userCourseAdapter = new UserCourseAdapter(courses);
        this.recyclerView.setAdapter(userCourseAdapter);
    }
    private void showAddCourseDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.dialog_add_course, null);

        final EditText editTextTitle = dialogView.findViewById(R.id.edtTitledialog);
        final EditText editTextDescription = dialogView.findViewById(R.id.edtDescdialog);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView)
                .setTitle("Thêm Course")
                .setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Thêm xử lý khi người dùng nhấn nút "Thêm" ở đây
                        String title = editTextTitle.getText().toString();
                        String description = editTextDescription.getText().toString();
                        // Thực hiện thêm Course với title và description được nhập
//                        Toast.makeText(getContext(),title,Toast.LENGTH_SHORT).show();
                        database_hp.QueryData("INSERT INTO ListWords VALUES(null,'"+title+"','user','"+description+"')");
                        createListCourse();
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Đóng cửa sổ dialog khi người dùng nhấn nút "Hủy"
                        dialogInterface.dismiss();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
//    public void createListCard(ArrayList<Course> listcourse){
//        for (int i = 0; i < listcourse.size(); i++) {
//            CardView cardView = createCardView(listcourse.get(i));
//            linearLayout.addView(cardView);
//        }
//    }
//    private CardView createCardView(Course course) {
//        String[] randomColors = {
//                "#FF5733", "#33FF57", "#5733FF", "#33FF99", "#9966FF" // Thêm các mã màu bạn muốn ở đây
//        };
//        CardView cardView = new CardView(requireContext());
//        cardView.setLayoutParams(new LinearLayout.LayoutParams(
//                CardView.LayoutParams.MATCH_PARENT,
//                200
//        ));
//        cardView.setContentPadding(16, 16, 16, 16);
//
//        //set Button
//        Button button = new Button(requireContext());
//        button.setLayoutParams(new CardView.LayoutParams(
//                200,
//                100
//        ));
//        Drawable icon = getResources().getDrawable(R.drawable.rename_icon);
//        icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
//        // Tạo một SpannableString để chèn biểu tượng vào nội dung Button
//        SpannableString spannableString = new SpannableString("." );
//        ImageSpan imageSpan = new ImageSpan(icon, ImageSpan.ALIGN_BOTTOM);
//        spannableString.setSpan(imageSpan, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//
//        // Đặt nội dung của Button
//        button.setText(spannableString);
//
//        //setText
//
//        TextView textView = new TextView(requireContext());
//        textView.setLayoutParams(new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//        ));
//        textView.setText("Mô tả:              " + course.getTitle());
//        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//        textView.setTypeface(null, Typeface.BOLD);
//
//        cardView.addView(button);
//        cardView.addView(textView);
//
//        return cardView;
//    }
}
