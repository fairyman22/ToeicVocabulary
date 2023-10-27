package com.example.toeicvocabulary.Adapter;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicvocabulary.Database.DatabaseHelper;
import com.example.toeicvocabulary.Model.Course;
import com.example.toeicvocabulary.R;

import java.util.List;

public class UserCourseAdapter extends RecyclerView.Adapter<UserCourseAdapter.ViewHolder> {

    private List<Course> courses;
    private static OnItemClickListener listener;


    public UserCourseAdapter(List<Course> courses) {
        this.courses = courses;
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public interface OnLongItemClickListener{
        void onLongItemClick(int position);
    }
    public void setOnLongItemClickListener(OnItemClickListener listeners){
        this.listener =  listeners;
    }
    public void setOnItemClickListener(OnItemClickListener listeners) {
        this.listener =  listeners;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course course = courses.get(position);
        holder.textViewTitle.setText(course.getTitle());
        holder.textViewDescription.setText(course.getDescription());
        holder.bind(course, position, this.courses);

    }


    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textViewTitle;
        TextView textViewDescription;

        public ViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.cardView);
            textViewTitle = view.findViewById(R.id.textViewTitle);
            textViewDescription = view.findViewById(R.id.textViewDescription);
        }
        public void bind(final Course course, int position, List<Course> courses) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            }
            );
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                DatabaseHelper databaseHelper = new DatabaseHelper(itemView.getContext(), "ToeicVocab.sqlite", null,1);
                @Override
                public boolean onLongClick(View view) {
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

                                            databaseHelper.QueryData("DELETE FROM ListWords WHERE Id='"+course.getId()+"';");
                                            courses.remove(position);
                                            notifyItemRemoved(position);

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
                                    // Xử lý lựa chọn 2
                                    AlertDialog.Builder inputDialogBuilder = new AlertDialog.Builder(view.getContext());
                                    inputDialogBuilder.setTitle("Nhập dữ liệu");

                                    // Layout cho Dialog (có thể sử dụng một layout tùy chỉnh cho EditText)
                                    LinearLayout layout = new LinearLayout(view.getContext());
                                    layout.setOrientation(LinearLayout.VERTICAL);

                                    final EditText editText1 = new EditText(view.getContext());
                                    editText1.setHint("Title");
                                    layout.addView(editText1);

                                    final EditText editText2 = new EditText(view.getContext());
                                    editText2.setHint("Description");
                                    layout.addView(editText2);

                                    inputDialogBuilder.setView(layout);

                                    inputDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String value1 = editText1.getText().toString();
                                            String value2 = editText2.getText().toString();
                                            course.setTitle(value1);
                                            course.setDescription(value2);
                                            databaseHelper.QueryData("UPDATE ListWords SET name = '"+course.getTitle()+"', mean = '"+course.getDescription()+"' WHERE Id='"+course.getId()+"';");
                                            notifyItemChanged(position);

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

                                    return true;
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

        }
    }
}
