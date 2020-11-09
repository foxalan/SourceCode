package com.alan.tfive_function.database.table;


import com.alan.tfive_function.database.annotation.Column;
import com.alan.tfive_function.database.annotation.Table;

/**
 * @author alan
 * function:
 */
@Table("t_record")
public class DailyRecord extends Entity {

    //日期
    @Column(name = "date", notnull = true)
    public String date;

    //运动次数
    @Column(name = "sport", notnull = true)
    public Integer pointSport;

    @Column(name = "eyes", notnull = true)
    public Integer pointEyes;

    @Column(name = "study", notnull = true)
    public Integer pointStudy;

    @Column(name = "jet", notnull = true)
    public Integer pointJet;

    @Column(name = "phone", notnull = true)
    public Integer pointPhone;

    @Column(name = "total", notnull = true)
    public Integer totalPoint;

    @Override
    public String toString() {
        return "DailyRecord{" +
                "date='" + date + '\'' +
                ", pointSport=" + pointSport +
                ", pointEyes=" + pointEyes +
                ", pointStudy=" + pointStudy +
                ", pointJet=" + pointJet +
                ", pointPhone=" + pointPhone +
                ", totalPoint=" + totalPoint +
                '}';
    }
}
