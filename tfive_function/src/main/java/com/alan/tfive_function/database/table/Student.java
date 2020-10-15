package com.alan.tfive_function.database.table;

import com.alan.tfive_function.database.annotation.Column;
import com.alan.tfive_function.database.annotation.Foreign;
import com.alan.tfive_function.database.annotation.Table;


/**
 * Created by zhangfei on 2017/4/29.
 */
@Table("student")
public class Student extends Entity {

    @Foreign(Teacher.class)
    @Column(name = "teacher_id", notnull = true)
    public long teacherId;

    @Column(name = "name", notnull = true)
    public String name;

    @Column(name = "age", notnull = true)
    public Integer age;
}
