package com.example.toeicvocabulary;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
//import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.toeicvocabulary.Model.Vocab;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;


public class FlashCardActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private List<WordFragment> fragmentList = new ArrayList<>();
    private int idCourse;
    private BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);
        this.viewPager = findViewById(R.id.viewPager);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar( toolbar);

        Intent intent = getIntent();
        Bundle bundle  = intent.getBundleExtra("words");
        int posstion = bundle.getInt("position");
        this.idCourse = bundle.getInt("idCourse");
        ArrayList<Vocab> vocabList = bundle.getParcelableArrayList("vocabList");

//        createListFracment(vocabList);

        // Thiết lập Adapter cho ViewPager
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), vocabList);
        this.viewPager.setAdapter(pagerAdapter);
        this.viewPager.setCurrentItem(posstion);


    }

    private static class PagerAdapter extends FragmentPagerAdapter {
        private final ArrayList<Vocab> vocabList;

        PagerAdapter(FragmentManager fragmentManager, ArrayList<Vocab> vocabList) {
            super(fragmentManager);
            this.vocabList = vocabList;

        }

        @Override
        public Fragment getItem(int position) {
            Vocab vocab = vocabList.get(position);
            WordFragment wordFragment = new WordFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("vocab", vocab);

            wordFragment.setArguments(bundle);
            return wordFragment;
        }

        @Override
        public int getCount() {
            return this.vocabList.size();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.flashcard_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_left) {
            onBackPressed();
            return true;
        } else if (id == R.id.action_right) {
            bottomSheetDialog = new BottomSheetDialog(this);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void moveActivity(){
        Intent intent = new Intent(getApplicationContext(), RememberMeanActivity.class);
//        intent.putParcelableArrayListExtra("vocabList",(ArrayList<Vocab>) this.vocabList);
        intent.putExtra("idList",idCourse);
        startActivity(intent);
    }
}
