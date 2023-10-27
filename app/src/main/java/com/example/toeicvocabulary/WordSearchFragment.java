package com.example.toeicvocabulary;

import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.toeicvocabulary.Database.DatabaseHelper;
import com.example.toeicvocabulary.Model.Course;
import com.example.toeicvocabulary.Model.Vocab;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordSearchFragment extends Fragment {
    private TextView txtWord;
    private Button btnVolume;
    private Button btnAdd;
    private TextView txtPronunc;
    private TextView txtMean;
    private ImageView img;
    private String linkSound, linkImg;

    private MediaPlayer mediaPlayer;

    private DatabaseHelper database_hp;
    private List<Course> courses;

    private Vocab vocab;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word_search, container, false);
        txtWord = view.findViewById(R.id.txtWordSearch);
        txtPronunc = view.findViewById(R.id.txtPronucSearch);
        txtMean = view.findViewById(R.id.txtMean);

        btnVolume = view.findViewById(R.id.btnVolume);
        btnAdd = view.findViewById(R.id.btnAddWord);

        img = view.findViewById(R.id.imgSearch);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_MEDIA).build());

        this.linkImg="";
        this.linkSound="";

        this.courses = new ArrayList<>();

        this.vocab = new Vocab();

        this.database_hp = new DatabaseHelper(getContext(), "ToeicVocab.sqlite", null, 1);

        Cursor dataNoidung =  database_hp.GetData("SELECT * FROM ListWords WHERE type='user' ");
        while (dataNoidung.moveToNext()){
            int id = dataNoidung.getInt(0);
            String name =  dataNoidung.getString(1);
            String type = dataNoidung.getString(2);
            String mean = dataNoidung.getString(3);

//            Toast.makeText(getContext(),""+id,Toast.LENGTH_SHORT).show();
            courses.add(new Course(id, name, mean, type));


        }

        Bundle bundle = getArguments();
        if(bundle != null){
            this.vocab =  bundle.getParcelable("vocab");
            txtWord.setText(vocab.getWord());
            txtPronunc.setText(vocab.getPronunc());
            txtMean.setText(vocab.getMean());
            this.linkSound = vocab.getSound();
            this.linkImg = vocab.getImage();
        }

        Picasso.get().load(this.linkImg).into(img);

        btnVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioAttributes(new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_MEDIA).build());
                }
                https://audio.jukehost.co.uk/5mnd3GshUdg0OyPXmi9u0wyXy06OkcDk
                try {
                    mediaPlayer.setDataSource(vocab.getSound());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Giải phóng mediaPlayer sau khi phát xong
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                });
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseListDialogFragment dialogFragment = new ChooseListDialogFragment(courses);
                dialogFragment.setOnListSelectedListener(new ChooseListDialogFragment.OnListSelectedListener() {
                    @Override
                    public void onListSelected(Course selectedCourse) {
                        database_hp.QueryData("INSERT INTO Wordss VALUES(null,'"+vocab.getWord()+"','"+selectedCourse.getId()+"','"+vocab.getMean()+"','"+vocab.getPronunc()+"','"+vocab.getSound()+"','"+vocab.getImage()+"','user')");
                        Toast.makeText(getContext(),"Lưu thành công", Toast.LENGTH_SHORT).show();
                    }
                });
                dialogFragment.show(getFragmentManager(), "choose_list_dialog");
            }
        });


        return  view;
    }
}
