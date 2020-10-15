package com.alan.tfive_function.database.table;

import com.alan.tfive_function.database.annotation.Column;
import com.alan.tfive_function.database.annotation.Table;


/**
 * Created by zhangfei on 2017/4/29.
 */
@Table("teacher")
public class Teacher extends Entity {

    @Column(name = "name", notnull = true)
    public String name;

}
