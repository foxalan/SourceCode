package com.alan.tfive_function.database.table;

import com.alan.tfive_function.database.annotation.Column;
import com.alan.tfive_function.database.annotation.Table;

/**
 * @author alan
 * function:
 */

@Table("t_user")
public class UserInfo extends Entity {

    @Column(name = "name")
    public String userName;

}
