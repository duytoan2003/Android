package com.example.qlhdnk;

public class ClassInfo {
    private String className;
    private String classYear;

    public ClassInfo(String className, String classYear) {
        this.className = className;
        this.classYear = classYear;
    }

    public String getClassName() {
        return className;
    }

    public String getClassYear() {
        return classYear;
    }
    public String toString() {
        // Override phương thức toString để xác định cách hiển thị thông tin trong ListView
        return className + " - " + classYear;
    }
}
