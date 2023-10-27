package com.example.toeicvocabulary;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class SettingFragment extends Fragment {
    private TextView txtname;
    private TextView txtold;
    private TextView txtphone;
    private TextView txtemail;
    private Button btnSua;
    private Button btnContact;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        txtname = view.findViewById(R.id.txtName);
        txtold = view.findViewById(R.id.txtOld);
        txtphone = view.findViewById(R.id.txtPhone);
        txtemail = view.findViewById(R.id.txtEmail);
        btnSua = view.findViewById(R.id.btnCustomInfo);
        btnContact = view.findViewById(R.id.btnContact);
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddCourseDialog();
            }
        });
        return view;
    }
    private void showAddCourseDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.dialog_custom_user, null);

        final EditText editname = dialogView.findViewById(R.id.edtNameUser);
        final EditText editold = dialogView.findViewById(R.id.edtOldUser);
        final EditText editphone = dialogView.findViewById(R.id.edtPhoneUser);
        final EditText editemail = dialogView.findViewById(R.id.edtEmailuser);

        editname.setText(this.txtname.getText().toString());
        editold.setText(this.txtold.getText().toString());
        editphone.setText(this.txtphone.getText().toString());
        editemail.setText(this.txtemail.getText().toString());

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView)
                .setTitle("Chỉnh sửa thông tin")
                .setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        txtname.setText(editname.getText().toString());
                        txtold.setText(editold.getText().toString());
                        txtphone.setText(editphone.getText().toString());
                        txtemail.setText(editemail.getText().toString());

//                        Toast.makeText(getContext(),title,Toast.LENGTH_SHORT).show();

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
}
