package com.example.qlhdnk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
public class QLHDActivity extends AppCompatActivity {
    TextView txtDate,txtTime;
    EditText editName, editDc;
    Button btnDate,btnTime,btnAdd;
    //Khai báo Datasource lưu trữ danh sách công việc
    ArrayList<HoatDong> arrJob=new ArrayList<HoatDong>();
    //Khai báo ArrayAdapter cho ListView
    ArrayAdapter<HoatDong> adapter=null;
    ListView lvCv;
    Calendar cal;
    Date dateFinish;
    Date hourFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlhdactivity);
        Intent intent = getIntent();
        if (intent != null) {
            String className = intent.getStringExtra("CLASS_NAME");
            String classYear = intent.getStringExtra("CLASS_YEAR");

            // Hiển thị thông tin lớp theo cách bạn muốn
            TextView classNameTextView = findViewById(R.id.textViewHDName);
            TextView classYearTextView = findViewById(R.id.textViewHDiaChi);

            classNameTextView.setText("Tên lớp: " + className);
            classYearTextView.setText("Khóa học: " + classYear);
        }
        getFormWidgets();
        getDefaultInfor();
        addEventFormWidgets();
    }
    private void getFormWidgets() {
        txtDate= findViewById(R.id.txtdate);
        txtTime= findViewById(R.id.txttime);
        editName = findViewById(R.id.editTen);
        editDc =(EditText) findViewById(R.id.editMa);
        btnDate=(Button) findViewById(R.id.btndate);
        btnTime=(Button) findViewById(R.id.btntime);
        btnAdd=(Button) findViewById(R.id.btnadd);
        lvCv=(ListView) findViewById(R.id.lvsinhvien);

        //Gán DataSource vào ArrayAdapter
        adapter=new ArrayAdapter<HoatDong>
                (this,
                        android.R.layout.simple_list_item_1,
                        arrJob);
        //gán Adapter vào ListView
        lvCv.setAdapter(adapter);
        fakeData();

    }

    private void fakeData() {
        cal=Calendar.getInstance();
        cal.set(2020,12,10,10,30);
        HoatDong j1=new HoatDong("Cv1","Hội thảo1",cal.getTime(),cal.getTime());
        cal.set(2019,11,10,12,15);
        HoatDong j2=new HoatDong("Cv2","Hội thảo2",cal.getTime(),cal.getTime());
        cal.set(2018,10,10,9,10);
        HoatDong j3=new HoatDong("Cv3","Hội thảo3",cal.getTime(),cal.getTime());
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
        txtDate.setText(strDate);
        //Định dạng giờ phút am/pm
        dft=new SimpleDateFormat("hh:mm a",Locale.getDefault());
        String strTime=dft.format(cal.getTime());
        //đưa lên giao diện
        txtTime.setText(strTime);
        //lấy giờ theo 24 để lập trình theo Tag
        dft=new SimpleDateFormat("HH:mm",Locale.getDefault());
        txtTime.setTag(dft.format(cal.getTime()));

        editName.requestFocus();
        //gán cal.getTime() cho ngày hoàn thành và giờ hoàn thành
        dateFinish=cal.getTime();
        hourFinish=cal.getTime();
    }

    private void addEventFormWidgets() {
        btnDate.setOnClickListener(new MyButtonEvent());
        btnTime.setOnClickListener(new MyButtonEvent());
        btnAdd.setOnClickListener(new MyButtonEvent());
        lvCv.setOnItemClickListener(new MyListViewEvent());
        lvCv.setOnItemLongClickListener(new MyListViewEvent());
    }
    /**
     * Class sự kiện của các Button
     *
     */
    private class MyButtonEvent implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            if (v.getId()== R.id.btndate){
                showDatePickerDialog();
            }
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
            openClassDetailsActivity(i);
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            //Xóa vị trí thứ arg2
            arrJob.remove(i);
            adapter.notifyDataSetChanged();
            return false;
        }
    }
    private void openClassDetailsActivity(int position) {
        // Lấy thông tin lớp từ danh sách
        HoatDong selectedClass = arrJob.get(position);

        // Tạo Intent để chuyển đến Activity QLHD và gửi thông tin lớp
        Intent intent = new Intent(QLHDActivity.this, BaoCaoActivity.class);
        intent.putExtra("HD_NAME", selectedClass.getTitle());
        intent.putExtra("HD_DiaChi", selectedClass.getDesciption());

        // Chuyển đến Activity QLHD
        startActivity(intent);
    }
    /**
     * Hàm hiển thị DatePicker dialog
     */
    public void showDatePickerDialog()
    {
        DatePickerDialog.OnDateSetListener callback=
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear,
                                          int dayOfMonth) {
                        //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Date
                        txtDate.setText((dayOfMonth) +"/"+(monthOfYear+1)+"/"+year);
                        //Lưu vết lại biến ngày hoàn thành
                        cal.set(year, monthOfYear, dayOfMonth);

                    }
                };
        //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s=txtDate.getText()+"";
        String strArrtmp[]=s.split("/");
        int ngay=Integer.parseInt(strArrtmp[0]);
        int thang=Integer.parseInt(strArrtmp[1])-1;
        int nam=Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic=new DatePickerDialog(
                QLHDActivity.this,
                callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày hoàn thành");
        pic.show();
    }
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
                hourFinish=cal.getTime();
            }
        };
        //các lệnh dưới này xử lý ngày giờ trong TimePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s=txtTime.getTag()+"";
        String strArr[]=s.split(":");
        int gio=Integer.parseInt(strArr[0]);
        int phut=Integer.parseInt(strArr[1]);
        TimePickerDialog time=new TimePickerDialog(
                QLHDActivity.this,
                callback, gio, phut, true);
        time.setTitle("Chọn giờ hoàn thành");
        time.show();
    }
    /**
     * Hàm xử lý đưa công việc vào ListView khi nhấn nút Thêm Công việc
     */
    public void processAddJob()
    {
        String title= editName.getText()+"";
        String description= editDc.getText()+"";
        HoatDong job=new HoatDong(title, description, dateFinish, hourFinish);
        arrJob.add(job);
        adapter.notifyDataSetChanged();
        //sau khi cập nhật thì reset dữ liệu và cho focus tới editCV
        editName.setText("");
        editDc.setText("");
        editName.requestFocus();
    }
}