package com.example.qlhdnk;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SinhVien {
    private String ten;
    private String ma;

    private Date hourFinish;
    public SinhVien(String title, String desciption,  Date hourFinish) {
        this.ten = title;
        this.ma = desciption;
        this.hourFinish = hourFinish;
    }

    public SinhVien() {

    }

    public String getTen() {
        return ten;
    }

    public void setTitle(String title) {
        this.ten = title;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String desciption) {
        this.ma = desciption;
    }


    public Date getHourFinish() {
        return hourFinish;
    }

    public void setHourFinish(Date hourFinish) {
        this.hourFinish = hourFinish;
    }
    public String getDateFormat(Date d)
    {
        SimpleDateFormat dft=new  SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return dft.format(d);
    }
    /**
     * lấy định dạng giờ phút
     * @param d
     * @return
     */
    public String getHourFormat(Date d)
    {
        SimpleDateFormat dft=new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return dft.format(d);
    }
    @Override
    public String toString() {
        return this.ten+"-"+
                getHourFormat(this.hourFinish);
    }
}
