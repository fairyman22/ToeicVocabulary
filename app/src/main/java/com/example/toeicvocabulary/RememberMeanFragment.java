package com.example.toeicvocabulary;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.toeicvocabulary.Model.Question;

import java.io.IOException;

public class RememberMeanFragment extends Fragment {
    private Question question = new Question();
    private TextView currentCount;
    private TextView totalCount;
    private TextView txtWrong;
    private TextView txtCorrect;
    private TextView txtWordgame;
    private TextView txtA;
    private TextView txtB;
    private TextView txtC;
    private TextView txtD;
    private Button btnClose;
    private Button btnVolum;
    private Button btnCheck;
    private CardView cardA;
    private CardView cardB;
    private CardView cardC;
    private CardView cardD;

    private MediaPlayer mediaPlayer;

    private CardView selectedCardView = null;
    private String dapantmp;
    private int numCorrect;
    private int numWrong;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remember_mean, container, false);
        this.currentCount = view.findViewById(R.id.currentCount);
        this.totalCount = view.findViewById(R.id.totalCount);
        this.txtWrong = view.findViewById(R.id.txtWrong);
        this.txtCorrect = view.findViewById(R.id.correct);
        this.txtWordgame = view.findViewById(R.id.txtWordgame);
        this.txtA = view.findViewById(R.id.daa);
        this.txtB = view.findViewById(R.id.dab);
        this.txtC = view.findViewById(R.id.dac);
        this.txtD = view.findViewById(R.id.dad);
        this.btnClose = view.findViewById(R.id.btnClose);
        this.btnVolum = view.findViewById(R.id.btnVolumegame);
        this.btnCheck = view.findViewById(R.id.btnCheck);
        this.cardA = view.findViewById(R.id.a);
        this.cardB = view.findViewById(R.id.b);
        this.cardC = view.findViewById(R.id.c);
        this.cardD = view.findViewById(R.id.d);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_MEDIA).build());

        Bundle bundle = getArguments();
        this.question = bundle.getParcelable("question");
        int currentC = bundle.getInt("currentQuestion");
//        Toast.makeText(getContext(),question.getAnswer(),Toast.LENGTH_SHORT).show();
        int numQ = bundle.getInt("numberQuestion");
        this.numCorrect = bundle.getInt("numCorrect");
        this.numWrong =bundle.getInt("numWrong");

        this.currentCount.setText(currentC+"");
        this.totalCount.setText(numQ+"");
        this.txtWordgame.setText(this.question.getQuestion());
        this.txtA.setText(this.question.getA());
        this.txtB.setText(this.question.getB());
        this.txtC.setText(this.question.getC());
        this.txtD.setText(this.question.getD());
        this.txtCorrect.setText(numCorrect+"");
        this.txtWrong.setText(numWrong+"");

        this.cardA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectCardView(cardA);
                dapantmp = txtA.getText().toString();
            }
        });
        this.cardB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectCardView(cardB);
                dapantmp = txtB.getText().toString();
            }
        });

        this.cardC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectCardView(cardC);
                dapantmp = txtC.getText().toString();
            }
        });

        this.cardD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectCardView(cardD);
                dapantmp = txtD.getText().toString();
            }
        });


        btnVolum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioAttributes(new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_MEDIA).build());
                }
                https://audio.jukehost.co.uk/5mnd3GshUdg0OyPXmi9u0wyXy06OkcDk
                try {
                    mediaPlayer.setDataSource(question.getLinkSound());
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

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnCheck.getText().toString().equals("Check")){
                    if (dapantmp.equals(question.getAnswer())){
                        Toast.makeText(getContext(),"Correct!",Toast.LENGTH_SHORT).show();
                        numCorrect++;
                    }
                    else {
                        Toast.makeText(getContext(),"Answer is "+question.getAnswer(),Toast.LENGTH_SHORT).show();
                        numWrong++;
                    }
                    btnCheck.setText("Next");
                } else if (btnCheck.getText().toString().equals("Next")) {
//                    Toast.makeText(getContext(),"Chuyen trang",Toast.LENGTH_SHORT).show();
                    ((RememberMeanActivity) getActivity()).showNextQuestion(numCorrect, numWrong);
                    btnCheck.setText("Check");
                }else {
                    Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
                }



            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }
    private void selectCardView(CardView cardView) {

        if (selectedCardView != null) {
            selectedCardView.setCardBackgroundColor(Color.parseColor("#f2f2f2"));
        }
        cardView.setCardBackgroundColor(Color.parseColor("#46b7e8"));
        selectedCardView = cardView;

    }

}
