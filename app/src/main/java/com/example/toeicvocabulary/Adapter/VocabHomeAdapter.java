package com.example.toeicvocabulary.Adapter;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.media.MediaPlayer;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicvocabulary.Model.Course;
import com.example.toeicvocabulary.Model.Vocab;
import com.example.toeicvocabulary.R;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

public class VocabHomeAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Vocab> listVocabs;
    private  MediaPlayer mediaPlayer ;


    public VocabHomeAdapter(Context context, int layout, List<Vocab> LisVocabs) {
        this.context = context;
        this.layout = layout;
        this.listVocabs = LisVocabs;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_MEDIA).build());

    }
    public  void clear(){
        this.listVocabs.clear();
        notifyDataSetChanged();
    }
    public void addAll(List<Vocab> LisVocabs){
        this.listVocabs.addAll(LisVocabs);
        notifyDataSetChanged();
    }
    private class ViewHolder{
        TextView txtTitle;
        TextView txtPronuc;
        Button btnVolume;

    }
    @Override
    public View getView(int position, View convert_view, ViewGroup parent){
        ViewHolder holder;
        if(convert_view==null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convert_view = inflater.inflate(layout, null);
            holder.txtTitle = (TextView) convert_view.findViewById(R.id.txtTitle);
            holder.txtPronuc = (TextView) convert_view.findViewById(R.id.txtPronuc);
            holder.btnVolume = (Button) convert_view.findViewById(R.id.volume);

            convert_view.setTag(holder);
        }else {
            holder = (ViewHolder) convert_view.getTag();
        }
         Vocab vocab = listVocabs.get(position);
        holder.txtTitle.setText(vocab.getWord());
        holder.txtPronuc.setText(vocab.getPronunc());
        holder.btnVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String audioPath = vocab.getSound();
//                Toast.makeText(view.getContext(), audioPath,Toast.LENGTH_SHORT).show();

                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioAttributes(new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_MEDIA).build());
                }

                try {
                    mediaPlayer.setDataSource(audioPath);
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

        return convert_view;
    }

    @Override
    public int getCount() {
        return listVocabs.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
