package com.alan.tfive_function.database.table;

import com.alan.tfive_function.database.annotation.Column;
import com.alan.tfive_function.database.annotation.ID;

/**
 * @author alan
 * function:
 */
public class Entity extends Query  {

    private static final long serialVersionUID = -6833637753877258272L;

    // ID value of new created object but not saved
    public static final long NOT_SAVED = 0;

    @ID
    @Column(name = _ID)
    public long id = NOT_SAVED;

    // All classes share this
    public static final String _ID = "_id";
    public static final String[] COUNT_COLUMNS = new String[]{"count(*)"};


}
