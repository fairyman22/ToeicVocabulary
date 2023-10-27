package com.example.toeicvocabulary;

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
import com.example.toeicvocabulary.Model.Vocab;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class WordFragment extends Fragment {

    private Vocab vocab;
    private TextView txtWord;
    private TextView txtpro;
    private ImageView imgv;
    private TextView txtMean;
    private Button btnshow;

    private Button btnVolume;
    MediaPlayer mediaPlayer;

    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flashcard, container, false);
        this.txtWord = view.findViewById(R.id.txtWord);
        this.txtpro = view.findViewById(R.id.txtpro);
        this.imgv = view.findViewById(R.id.imageContent);
        this.txtMean = view.findViewById(R.id.hiddenTextView);
        this.btnshow = view.findViewById(R.id.showButton);
        this.btnVolume = view.findViewById(R.id.btnVolumeflash);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_MEDIA).build());

        Bundle bundle = getArguments();
        this.vocab = bundle.getParcelable("vocab");
//        Toast.makeText(view.getContext(),bundle.getString("word"),Toast.LENGTH_SHORT).show();
        txtWord.setText(this.vocab.getWord());
        txtpro.setText(this.vocab.getPronunc());
        Picasso.get().load(this.vocab.getImage()).into(imgv);
//        txtWord.setText(bundle.getString("word"));
//        txtpro.setText("pro");
        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtMean.setText(vocab.getMean());
                txtMean.setVisibility(View.VISIBLE);
            }
        });
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



        return view;

    }
}
