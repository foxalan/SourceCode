package com.alan.tfive_function.database.table;


import com.alan.tfive_function.database.annotation.Column;
import com.alan.tfive_function.database.annotation.InnerJoin;
import com.alan.tfive_function.database.annotation.InnerJoinItem;

@InnerJoin(@InnerJoinItem(firstTable = "teacher", firstColumn = "_id",
        secondTable = "student", secondColumn = "teacher_id"))
public class Relation extends Query {
    @Column(name = "teacher_id", aliasName = "student._id as teacher_id")
    public long teacherId;

    @Column(name = "teacher_name", aliasName = "teacher.name as teacher_name")
    public String teacherName;

    @Column(name = "student_id", aliasName = "student._id as student_id")
    public long studentId;

    @Column(name = "student_name", aliasName = "student.name as student_name")
    public String studentName;

    @Column(name = "age")
    public int studentAge;
}
