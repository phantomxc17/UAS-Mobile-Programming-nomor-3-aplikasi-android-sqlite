package com.example.uas3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private DBHandler dbHelper;
    private StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHandler(this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new StudentAdapter(this, dbHelper.getAllStudents());
        recyclerView.setAdapter(adapter);

        Button buttonAdd = findViewById(R.id.button_add);
        buttonAdd.setOnClickListener(v -> showAddStudentDialog());
    }

    private void showAddStudentDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.add_student, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setTitle("Add Student");
        builder.setPositiveButton("Add", (dialog, which) -> {
            EditText nameInput = view.findViewById(R.id.input_name);
            EditText nimInput = view.findViewById(R.id.input_nim);
            EditText ipkInput = view.findViewById(R.id.input_ipk);
            EditText courseInput = view.findViewById(R.id.input_course);

            String name = nameInput.getText().toString().trim();
            String nim = nimInput.getText().toString().trim();
            String ipkStr = ipkInput.getText().toString().trim();
            String course = courseInput.getText().toString().trim();

            if (name.isEmpty() || nim.isEmpty() || ipkStr.isEmpty() || course.isEmpty()) {
                Toast.makeText(MainActivity.this, "Data Tidak Lengkap!", Toast.LENGTH_SHORT).show();
                return;
            }

            double ipk = Double.parseDouble(ipkStr);

            dbHelper.addStudent(name, nim, ipk, course);
            adapter.swapCursor(dbHelper.getAllStudents());
        });

        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }

    public void deleteStudent(long id) {
        dbHelper.deleteStudent(id);
        adapter.swapCursor(dbHelper.getAllStudents());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}
