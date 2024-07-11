package com.example.uas3;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;



public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private Context context;
    private Cursor cursor;

    public StudentAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }

        String name = cursor.getString(cursor.getColumnIndex(DBHandler.COLUMN_NAME));
        String nim = cursor.getString(cursor.getColumnIndex(DBHandler.COLUMN_NIM));
        double ipk = cursor.getDouble(cursor.getColumnIndex(DBHandler.COLUMN_IPK));
        String course = cursor.getString(cursor.getColumnIndex(DBHandler.COLUMN_COURSE));
        long id = cursor.getLong(cursor.getColumnIndex(DBHandler.COLUMN_ID));

        holder.nameText.setText(name);
        holder.nimText.setText(nim);
        holder.ipkText.setText(String.valueOf(ipk));
        holder.courseText.setText(course);
        holder.itemView.setTag(id);

        holder.deleteButton.setOnClickListener(v -> {
            ((MainActivity) context).deleteStudent(id);
        });
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView nimText;
        TextView ipkText;
        TextView courseText;
        Button deleteButton;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.text_name);
            nimText = itemView.findViewById(R.id.text_nim);
            ipkText = itemView.findViewById(R.id.text_ipk);
            courseText = itemView.findViewById(R.id.text_course);
            deleteButton = itemView.findViewById(R.id.button_delete);
        }
    }
}
