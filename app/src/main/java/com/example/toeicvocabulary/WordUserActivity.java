package com.example.toeicvocabulary;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.toeicvocabulary.Adapter.VocabHomeAdapter;
import com.example.toeicvocabulary.Database.DatabaseHelper;
import com.example.toeicvocabulary.Model.Vocab;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class WordUserActivity extends AppCompatActivity {
    private Button btnBack;
    private Button btnTrain;
    private Button btnAdd;
    private TextView txtList;
    private ListView lv;
    private BottomSheetDialog bottomSheetDialog;
    VocabHomeAdapter adapter;
    List<Vocab> vocabList;
    DatabaseHelper database_hp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userword);

        this.btnBack = findViewById(R.id.btnBackuser);
        this.btnTrain = findViewById(R.id.btnTrainuser);
        this.btnAdd = findViewById(R.id.btnAdduser);
        this.lv = findViewById(R.id.listworduser);
        this.txtList = findViewById(R.id.txtListuser);
        vocabList = new ArrayList<>();

        database_hp = new DatabaseHelper(this, "ToeicVocab.sqlite", null, 1);
        database_hp.QueryData("CREATE TABLE IF NOT EXISTS ListWords(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(200), type VARCHAR(200), mean VARCHAR(200))");
        database_hp.QueryData("CREATE TABLE IF NOT EXISTS Words(Id INTEGER PRIMARY KEY AUTOINCREMENT, word VARCHAR(200), listid VARCHAR(200), mean VARCHAR(200), pronunc VARCHAR(200), sound VARCHAR(200), image VARCHAR(200))");

        Intent intent = getIntent();

        Bundle bundle  = intent.getBundleExtra("listword");
        txtList.setText(bundle.getString("course_name"));
        int id = bundle.getInt("course_id");
        showListWord(id);

        //onClickItem Lisview
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(WordUserActivity.this, FlashCardActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelableArrayList("vocabList",new ArrayList<>(vocabList));
                bundle.putInt("position",i);
                intent.putExtra("words",bundle);
                startActivity(intent);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Vocab vocab = vocabList.get(i);
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_options_usercourse, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_option1:
                                // Xử lý lựa chọn 1
                                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                builder.setTitle("Xác nhận xóa");
                                builder.setMessage("Bạn có chắc muốn xóa không?");
                                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Xử lý xóa

                                        database_hp.QueryData("DELETE FROM Wordss WHERE Id='"+vocab.getId()+"';");
                                        showListWord(id);

                                    }
                                });
                                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        dialog.dismiss();
                                    }
                                });
                                builder.show();
                                return true;
                            case R.id.action_option2:
                                AlertDialog.Builder inputDialogBuilder = new AlertDialog.Builder(view.getContext());
                                inputDialogBuilder.setTitle("Sửa dữ liệu");

                                // Layout cho Dialog (có thể sử dụng một layout tùy chỉnh cho EditText)
                                LinearLayout layout = new LinearLayout(view.getContext());
                                layout.setOrientation(LinearLayout.VERTICAL);

                                final EditText editTextWord = new EditText(view.getContext());
                                editTextWord.setText(vocab.getWord());
                                layout.addView(editTextWord);

                                final EditText editTextMean = new EditText(view.getContext());
                                editTextMean.setText(vocab.getMean());
                                layout.addView(editTextMean);

                                final EditText editTextPronuc = new EditText(view.getContext());
                                editTextPronuc .setText(vocab.getPronunc());
                                layout.addView(editTextPronuc );

                                final EditText editTextSound = new EditText(view.getContext());
                                editTextSound.setText(vocab.getSound());
                                layout.addView(editTextSound);

                                final EditText editTextImage = new EditText(view.getContext());
                                editTextImage.setText(vocab.getImage());
                                layout.addView(editTextImage);

                                inputDialogBuilder.setView(layout);

                                inputDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        String word = editTextWord.getText().toString();
                                        String mean = editTextMean.getText().toString();
                                        String pronun = editTextPronuc.getText().toString();
                                        String sound = editTextSound.getText().toString();
                                        String image = editTextImage.getText().toString();
                                        vocab.setMean(mean);
                                        vocab.setImage(image);
                                        vocab.setPronunc(pronun);
                                        vocab.setWord(word);
                                        vocab.setSound(sound);

                                        database_hp.QueryData("UPDATE Wordss SET word = '"+vocab.getWord()+"', mean = '"+vocab.getMean()+"',pronunc = '"+vocab.getPronunc()+"', sound = '"+vocab.getSound()+"', image = '"+vocab.getImage()+"' Where Id='"+vocab.getId()+"'");

                                        showListWord(id);

                                        // Xử lý dữ liệu nhập vào (value1 và value2)
                                    }
                                });

                                inputDialogBuilder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Đóng Dialog
                                        dialog.dismiss();
                                    }
                                });

                                inputDialogBuilder.show();
                                return  true;
                            // Thêm các lựa chọn khác nếu cần
                            default:
                                return false;
                        }
                    }
                });

                // Hiển thị PopupMenu
                popupMenu.show();
                return true;
            }
        });

        //Nut add
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder inputDialogBuilder = new AlertDialog.Builder(view.getContext());
                inputDialogBuilder.setTitle("Nhập dữ liệu");

                // Layout cho Dialog (có thể sử dụng một layout tùy chỉnh cho EditText)
                LinearLayout layout = new LinearLayout(view.getContext());
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText editTextWord = new EditText(view.getContext());
                editTextWord.setHint("Word");
                layout.addView(editTextWord);

                final EditText editTextMean = new EditText(view.getContext());
                editTextMean.setHint("Meaning");
                layout.addView(editTextMean);

                final EditText editTextPronuc = new EditText(view.getContext());
                editTextPronuc .setHint("Pronunciation");
                layout.addView(editTextPronuc );

                final EditText editTextSound = new EditText(view.getContext());
                editTextSound.setHint("Link - Sound");
                layout.addView(editTextSound);

                final EditText editTextImage = new EditText(view.getContext());
                editTextImage.setHint("Link - Image");
                layout.addView(editTextImage);

                inputDialogBuilder.setView(layout);

                inputDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String word = editTextWord.getText().toString();
                        String mean = editTextMean.getText().toString();
                        String pronun = editTextPronuc.getText().toString();
                        String sound = editTextSound.getText().toString();
                        String image = editTextImage.getText().toString();
                        Vocab vocab = new Vocab(word,mean,pronun,sound,image);

                        database_hp.QueryData("INSERT INTO Wordss VALUES(null,'"+vocab.getWord()+"','"+id+"','"+vocab.getMean()+"','"+vocab.getPronunc()+"','"+vocab.getSound()+"','"+vocab.getImage()+"','user')");

                        showListWord(id);

                        // Xử lý dữ liệu nhập vào (value1 và value2)
                    }
                });

                inputDialogBuilder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Đóng Dialog
                        dialog.dismiss();
                    }
                });

                inputDialogBuilder.show();
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


            }
        });

    }
    public void showListWord(int idList){
        List<Vocab> vocabs = new ArrayList<>();
        Cursor dataNoidung =  database_hp.GetData("SELECT * FROM Wordss Where listid='"+idList+"'");
        while (dataNoidung.moveToNext()){
            int idword = dataNoidung.getInt(0);
            String word =  dataNoidung.getString(1);
            String listid = dataNoidung.getString(2);
            String mean = dataNoidung.getString(3);
            String pronunc = dataNoidung.getString(4);
            String sound = dataNoidung.getString(5);
            String image =dataNoidung.getString(6);

//            Toast.makeText(getContext(),""+id,Toast.LENGTH_SHORT).show();
            vocabs.add(new Vocab(word, mean,idword, pronunc,sound,image));


        }
        this.vocabList = new ArrayList<>(vocabs);
        adapter = new VocabHomeAdapter(this, R.layout.word_item,vocabs);
        lv.setAdapter(adapter);
    }
    public void dismissBottomSheet(View view) {
        // Đóng bottom sheet khi nút "Hủy bỏ" được nhấn
        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
            this.bottomSheetDialog.dismiss();
        }
    }

}
