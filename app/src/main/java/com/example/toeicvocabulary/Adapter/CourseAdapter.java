package com.example.toeicvocabulary.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.toeicvocabulary.Model.Course;
import com.example.toeicvocabulary.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private List<Course> courses;
    private OnItemClickListener listener;

    public CourseAdapter(List<Course> courses) {
        this.courses = courses;

    }


     public interface OnItemClickListener{
        void onItemClick(int position);
     }
    public void setOnItemClickListener(OnItemClickListener listeners) {
        this.listener =  listeners;
    }

        @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Course course = courses.get(position);

        holder.courseNumber.setText(String.valueOf(course.getId()));
        holder.courseTitle.setText(course.getTitle());
        holder.courseDescription.setText(course.getDescription());
        holder.bind(course);

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView courseNumber;
        public TextView courseTitle;
        public TextView courseDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            courseNumber = itemView.findViewById(R.id.course_number);
            courseTitle = itemView.findViewById(R.id.course_title);
            courseDescription = itemView.findViewById(R.id.course_description);


        }
        public void bind(final Course course) {

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
            });
        }
    }
}
