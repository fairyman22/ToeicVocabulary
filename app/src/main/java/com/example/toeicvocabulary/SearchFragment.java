package com.example.toeicvocabulary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.toeicvocabulary.Adapter.CourseAdapter;
import com.example.toeicvocabulary.Adapter.VocabHomeAdapter;
import com.example.toeicvocabulary.Database.DatabaseHelper;
import com.example.toeicvocabulary.Model.Course;
import com.example.toeicvocabulary.Model.Vocab;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private EditText edt;
    ListView lv;
    List<Vocab> vocabList;
    VocabHomeAdapter adapter;


    private DatabaseHelper database_hp;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        edt= view.findViewById(R.id.editTextSearch);
        lv = view.findViewById(R.id.list_search);
        vocabList = new ArrayList<>();
        database_hp = new DatabaseHelper(getContext(), "ToeicVocab.sqlite", null, 1);


        Cursor dataNoidung =  database_hp.GetData("SELECT * FROM Wordss Where type='system'");
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
        adapter = new VocabHomeAdapter(getContext(), R.layout.word_item,vocabList);
        lv.setAdapter(adapter);

        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Xử lý tìm kiếm và và cập nhập ListView
                String word = charSequence.toString().toLowerCase();
                List<Vocab> searchResults = new ArrayList<>();
                for (Vocab vocab:vocabList) {
                    if (vocab.getWord().toLowerCase().contains(word)) {
                        searchResults.add(vocab);
                    }
                }
                adapter.clear();
                adapter.addAll(searchResults);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Vocab vocab = vocabList.get(i);
                Bundle bundle = new Bundle();
                bundle.putParcelable("vocab",  vocab);

                WordSearchFragment wordSearchFragment = new WordSearchFragment();
                wordSearchFragment.setArguments(bundle);

                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, wordSearchFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }
}