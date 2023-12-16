package com.example.qlhdnk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BaoCaoActivity extends AppCompatActivity {
    TextView txtTime;
    EditText editTen, editMa;
    Button btnTime,btnAdd;
    //Khai báo Datasource lưu trữ danh sách công việc
    ArrayList<SinhVien> arrJob=new ArrayList<SinhVien>();
    //Khai báo ArrayAdapter cho ListView
    ArrayAdapter<SinhVien> adapter=null;
    ListView lvsv;
    Calendar cal;
    Date hour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bao_cao);
        Intent intent = getIntent();
        if (intent != null) {
            String className = intent.getStringExtra("HD_NAME");
            String classYear = intent.getStringExtra("HD_DiaChi");

            // Hiển thị thông tin lớp theo cách bạn muốn
            TextView classNameTextView = findViewById(R.id.textViewHDName);
            TextView classYearTextView = findViewById(R.id.textViewHDiaChi);

            classNameTextView.setText("Tên HD: " + className);
            classYearTextView.setText("Địa chỉ HD: " + classYear);
        }
        getFormWidgets();
        getDefaultInfor();
        addEventFormWidgets();
    }

    private void getFormWidgets() {

        txtTime= findViewById(R.id.txttime);
        editTen = findViewById(R.id.editTen);
        editMa =(EditText) findViewById(R.id.editMa);

        btnTime=(Button) findViewById(R.id.btntime);
        btnAdd=(Button) findViewById(R.id.btnadd);
        lvsv =(ListView) findViewById(R.id.lvsinhvien);

        //Gán DataSource vào ArrayAdapter
        adapter=new ArrayAdapter<SinhVien>
                (this,
                        android.R.layout.simple_list_item_1,
                        arrJob);
        //gán Adapter vào ListView
        lvsv.setAdapter(adapter);
        fakeData();

    }

    private void fakeData() {
        cal=Calendar.getInstance();
        cal.set(2020,12,10,10,30);
        SinhVien j1=new SinhVien("Cv1","Hội thảo1",cal.getTime());
        cal.set(2019,11,10,12,15);
        SinhVien j2=new SinhVien("Cv1","Hội thảo1",cal.getTime());
        cal.set(2018,10,10,9,10);
        SinhVien j3=new SinhVien("Cv1","Hội thảo1",cal.getTime());
        arrJob.add(j1);
        arrJob.add(j2);
        arrJob.add(j3);
        //
        adapter.notifyDataSetChanged();
    }

    /**
     * Hàm lấy các thông số mặc định khi lần đầu tiền chạy ứng dụng
     */
    private void getDefaultInfor() {
        //lấy ngày hiện tại của hệ thống
        cal=Calendar.getInstance();
        SimpleDateFormat dft=null;
        //Định dạng ngày / tháng /năm
        dft=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strDate=dft.format(cal.getTime());
        //hiển thị lên giao diện

        //Định dạng giờ phút am/pm
        dft=new SimpleDateFormat("hh:mm a",Locale.getDefault());
        String strTime=dft.format(cal.getTime());
        //đưa lên giao diện
        txtTime.setText(strTime);
        //lấy giờ theo 24 để lập trình theo Tag
        dft=new SimpleDateFormat("HH:mm",Locale.getDefault());
        txtTime.setTag(dft.format(cal.getTime()));

        editTen.requestFocus();
        //gán cal.getTime() cho ngày hoàn thành và giờ hoàn thành
        hour =cal.getTime();
    }

    private void addEventFormWidgets() {

        btnTime.setOnClickListener(new BaoCaoActivity.MyButtonEvent());
        btnAdd.setOnClickListener(new BaoCaoActivity.MyButtonEvent());
        lvsv.setOnItemClickListener(new BaoCaoActivity.MyListViewEvent());
        lvsv.setOnItemLongClickListener(new BaoCaoActivity.MyListViewEvent());
    }
    /**
     * Class sự kiện của các Button
     *
     */
    private class MyButtonEvent implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {

            if (v.getId()== R.id.btntime) {
                showTimePickerDialog();
            }
            if (v.getId()==R.id.btnadd) {
                processAddJob();
            }
        }
    }
    /**
     * Class sự kiện của ListView
     */
    private class MyListViewEvent implements
            AdapterView.OnItemClickListener,
            AdapterView.OnItemLongClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //Hiển thị nội dung công việc tại vị trí thứ arg2
            Toast.makeText(BaoCaoActivity.this,
                    arrJob.get(i).getMa(),
                    Toast.LENGTH_LONG).show();
            //trở lại giao diện bên trên
            editTen.setText(arrJob.get(i).getTen());
            editMa.setText(arrJob.get(i).getMa());
            txtTime.setText(arrJob.get(i).getHourFormat(arrJob.get(i).getHourFinish()));
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            //Xóa vị trí thứ arg2
            arrJob.remove(i);
            adapter.notifyDataSetChanged();
            return false;
        }
    }
    /**
     * Hàm hiển thị DatePicker dialog
     */

    /**
     * Hàm hiển thị TimePickerDialog
     */
    public void showTimePickerDialog()
    {
        TimePickerDialog.OnTimeSetListener callback=new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view,
                                  int hourOfDay, int minute) {
                //Xử lý lưu giờ và AM,PM
                String s=hourOfDay +":"+minute;
                int hourTam=hourOfDay;
                if(hourTam>12)
                    hourTam=hourTam-12;
                txtTime.setText(hourTam +":"+minute +(hourOfDay>12?" PM":" AM"));
                //lưu giờ thực vào tag
                txtTime.setTag(s);
                //lưu vết lại giờ vào hourFinish
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);
                hour =cal.getTime();
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong TimePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s=txtTime.getTag()+"";
        String strArr[]=s.split(":");
        int gio=Integer.parseInt(strArr[0]);
        int phut=Integer.parseInt(strArr[1]);
        TimePickerDialog time=new TimePickerDialog(
                BaoCaoActivity.this,
                callback, gio, phut, true);
        time.setTitle("Chọn giờ hoàn thành");
        time.show();
    }
    /**
     * Hàm xử lý đưa công việc vào ListView khi nhấn nút Thêm Công việc
     */
    public void processAddJob()
    {
        String title= editTen.getText()+"";
        String description= editMa.getText()+"";
        SinhVien job=new SinhVien(title, description, hour);
        arrJob.add(job);
        adapter.notifyDataSetChanged();
        //sau khi cập nhật thì reset dữ liệu và cho focus tới editCV
        editTen.setText("");
        editMa.setText("");
        editTen.requestFocus();
    }
}