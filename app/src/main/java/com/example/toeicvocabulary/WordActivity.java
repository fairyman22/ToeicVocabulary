package com.example.toeicvocabulary;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.toeicvocabulary.Adapter.VocabHomeAdapter;
import com.example.toeicvocabulary.Database.DatabaseHelper;
import com.example.toeicvocabulary.Model.Course;
import com.example.toeicvocabulary.Model.Vocab;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class WordActivity extends AppCompatActivity {
    private Button btnBack;
    private Button btnTrain;
    private TextView txtList;
    private ListView lv;
    private BottomSheetDialog bottomSheetDialog;
    private int id;
    private VocabHomeAdapter adapter;
    private List<Vocab> vocabList;
    private DatabaseHelper database_hp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        this.btnBack = findViewById(R.id.btnBack);
        this.btnTrain = findViewById(R.id.btnTrain);
        this.lv = findViewById(R.id.listword);
        this.txtList = findViewById(R.id.txtList);
        this.vocabList = new ArrayList<>();

        database_hp = new DatabaseHelper(this, "ToeicVocab.sqlite", null, 1);
        database_hp.QueryData("CREATE TABLE IF NOT EXISTS ListWords(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(200), type VARCHAR(200), mean VARCHAR(200))");
        database_hp.QueryData("CREATE TABLE IF NOT EXISTS Words(Id INTEGER PRIMARY KEY AUTOINCREMENT, word VARCHAR(200), listid VARCHAR(200), mean VARCHAR(200), pronunc VARCHAR(200), sound VARCHAR(200), image VARCHAR(200))");

        Intent intent = getIntent();

        Bundle bundle  = intent.getBundleExtra("listword");
        txtList.setText(bundle.getString("course_name"));
        this.id = bundle.getInt("course_id");

        Cursor dataNoidung =  database_hp.GetData("SELECT * FROM Wordss Where listid='"+this.id+"'");
        while (dataNoidung.moveToNext()){
            int idword = dataNoidung.getInt(0);
            String word =  dataNoidung.getString(1);
            String listid = dataNoidung.getString(2);
            String mean = dataNoidung.getString(3);
            String pronunc = dataNoidung.getString(4);
            String sound = dataNoidung.getString(5);
            String image =dataNoidung.getString(6);

//            Toast.makeText(getContext(),""+id,Toast.LENGTH_SHORT).show();
            vocabList.add(new Vocab(word, mean,idword, pronunc,sound,image));


        }
        adapter = new VocabHomeAdapter(this, R.layout.word_item,vocabList);
        lv.setAdapter(adapter);

        //onClickItem Lisview
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(WordActivity.this, FlashCardActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelableArrayList("vocabList",new ArrayList<>(vocabList));
                bundle.putInt("position",i);
                bundle.putInt("idCourse",id);
                intent.putExtra("words",bundle);
                startActivity(intent);
            }
        });

        //Nút quay lại
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //Nút Luyện tập
        btnTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog = new BottomSheetDialog(view.getContext());
                View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_train_layout, null);

                // Tạo và hiển thị bottom sheet
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

                Button btnNhoNghia = bottomSheetView.findViewById(R.id.btnNhoNghia);

                btnNhoNghia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        moveActivity();
                    }
                });



            }
        });

    }
    public void moveActivity(){
        Intent intent = new Intent(getApplicationContext(), RememberMeanActivity.class);
        intent.putParcelableArrayListExtra("vocabList",(ArrayList<Vocab>) this.vocabList);
        intent.putExtra("idList",id);
        startActivity(intent);
    }
    public void dismissBottomSheet(View view) {
        // Đóng bottom sheet khi nút "Hủy bỏ" được nhấn
        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
            this.bottomSheetDialog.dismiss();
        }
    }
}
