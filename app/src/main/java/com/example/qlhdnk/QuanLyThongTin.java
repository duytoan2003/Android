package com.example.qlhdnk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class QuanLyThongTin extends AppCompatActivity {
    private EditText classNameEditText;
    private Spinner classYearSpinner;
    private ListView classListView;
    private Button addButton;


    ArrayList<ClassInfo> arrJob=new ArrayList<ClassInfo>();
    private ArrayAdapter<ClassInfo> classAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_thong_tin);
        classNameEditText = findViewById(R.id.editTextClassName);
        classYearSpinner = findViewById(R.id.spinnerClassYear);
        classListView = findViewById(R.id.listViewClasses);
        addButton = findViewById(R.id.buttonAdd);

        // Tạo danh sách các khóa học để hiển thị trong Spinner
        String[] classYears = {"Khóa 1", "Khóa 2", "Khóa 3", "Khóa 4"};

        // Thiết lập Adapter cho Spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, classYears);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classYearSpinner.setAdapter(spinnerAdapter);

        // Khởi tạo danh sách và Adapter cho ListView
        classAdapter=new ArrayAdapter<ClassInfo>
                (this,
                        android.R.layout.simple_list_item_1,
                        arrJob);
        //gán Adapter vào ListView
        classListView.setAdapter(classAdapter);

        // Xử lý sự kiện khi nhấn nút Thêm
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClass();
            }
        });
        classListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Xử lý sự kiện khi một dòng được nhấn
                openClassDetailsActivity(position);
            }
        });
        classListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
    }
    private void openClassDetailsActivity(int position) {
        // Lấy thông tin lớp từ danh sách
        ClassInfo selectedClass = arrJob.get(position);

        // Tạo Intent để chuyển đến Activity QLHD và gửi thông tin lớp
        Intent intent = new Intent(QuanLyThongTin.this, QLHDActivity.class);
        intent.putExtra("CLASS_NAME", selectedClass.getClassName());
        intent.putExtra("CLASS_YEAR", selectedClass.getClassYear());

        // Chuyển đến Activity QLHD
        startActivity(intent);
    }
    private void addClass() {
        // Lấy dữ liệu từ EditText và Spinner
        String className = classNameEditText.getText().toString();
        String classYear = classYearSpinner.getSelectedItem().toString();

        // Tạo đối tượng ClassInfo và thêm vào danh sách
        ClassInfo newClass = new ClassInfo(className, classYear);
        arrJob.add(newClass);

        // Cập nhật Adapter
        classAdapter.notifyDataSetChanged();

        // Xóa nội dung EditText sau khi thêm
        classNameEditText.getText().clear();
    }
}