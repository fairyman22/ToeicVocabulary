package com.example.toeicvocabulary;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.toeicvocabulary.Database.DatabaseHelper;
import com.example.toeicvocabulary.Model.Question;
import com.example.toeicvocabulary.Model.Vocab;

import java.util.ArrayList;
import java.util.List;

public class RememberMeanActivity extends AppCompatActivity {
//    private TextView currentCount;
//    private TextView totalCount;
    private DatabaseHelper database_hp;
    private List<Question> questionList;
    private int currentQuestion = 0;
    int idList;

    private  int numCorrect;
    private  int numWrong;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remember_mean);
//        this.currentCount = findViewById(R.id.currentCount);
//        this.totalCount = findViewById(R.id.totalCount);




        this.questionList = new ArrayList<>();
        Intent intent = getIntent();
        this.numCorrect=0;
        this.numWrong=0;


        this.idList = intent.getIntExtra("idList",0);
        database_hp = new DatabaseHelper(this, "ToeicVocab.sqlite", null, 1);

        Cursor dataNoidung =  database_hp.GetData("SELECT * FROM Question Where idList='"+this.idList+"'");
        while (dataNoidung.moveToNext()){
            int id = dataNoidung.getInt(0);
            String question =  dataNoidung.getString(1);
            String linksound = dataNoidung.getString(2);
            String a = dataNoidung.getString(3);
            String b = dataNoidung.getString(4);
            String c = dataNoidung.getString(5);
            String d =dataNoidung.getString(6);
            String answer =dataNoidung.getString(7);
            String idList =dataNoidung.getString(8);
            String type =dataNoidung.getString(9);


            this.questionList.add(new Question(id, question,linksound, a,b,c,d,answer,idList,type));


        }
//        this.totalCount.setText(questionList.size()+"");
        showQuestionFragment(currentQuestion);

    }

    public void showNextQuestion(int numCorrect, int numWrong){
        currentQuestion++;

        this.numCorrect=numCorrect;
        this.numWrong=numWrong;


        if (currentQuestion < questionList.size()) {
            // Hiển thị Fragment câu hỏi tiếp theo
            showQuestionFragment(currentQuestion);
        } else {
            // Hiển thị kết quả hoặc thực hiện hành động khác
        }
    }
    public void showQuestionFragment(int questionIndex){
        if (questionIndex < this.questionList.size()) {
            int tmp =questionIndex+1;
//            this.currentCount.setText(tmp+"");
            RememberMeanFragment fragment = new RememberMeanFragment();
            Bundle bundle = new Bundle();

            bundle.putParcelable("question",questionList.get(questionIndex));
            bundle.putInt("currentQuestion",questionIndex+1);
            bundle.putInt("numberQuestion",this.questionList.size());
            bundle.putInt("numCorrect",this.numCorrect);
            bundle.putInt("numWrong",this.numWrong);


            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.commit();
            fragment.setArguments(bundle);
        }
    }
}
