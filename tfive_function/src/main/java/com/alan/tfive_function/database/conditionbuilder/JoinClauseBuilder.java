package com.alan.tfive_function.database.conditionbuilder;


import com.alan.tfive_function.database.annotation.CrossJoin;
import com.alan.tfive_function.database.annotation.InnerJoin;
import com.alan.tfive_function.database.annotation.InnerJoinItem;
import com.alan.tfive_function.database.annotation.LeftJoin;
import com.alan.tfive_function.database.annotation.NaturalJoin;

class JoinClauseBuilder {

    public static String buildInnerJoinClause(InnerJoin innerJoin){
        StringBuilder whereBuilder = new StringBuilder();
        InnerJoinItem[] items = innerJoin.value();
        if (items.length == 1) {
            InnerJoinItem item = items[0];
            whereBuilder.append(item.firstTable())
                    .append(" INNER JOIN ")
                    .append(item.secondTable())
                    .append(" ON ")
                    .append(item.firstTable()).append(".").append(item.firstColumn())
                    .append("=")
                    .append(item.secondTable()).append(".").append(item.secondColumn());
        } else {
            for (int i = 0; i < items.length - 1; i++){
                whereBuilder.append("(");
            }

            for (int i = 0; i < items.length; i++){
                InnerJoinItem item = items[i];

                if (i == 0) {
                    whereBuilder.append(item.firstTable())
                            .append(" INNER JOIN ")
                            .append(item.secondTable())
                            .append(" ON ")
                            .append(item.firstTable()).append(".").append(item.firstColumn())
                            .append("=")
                            .append(item.secondTable()).append(".").append(item.secondColumn())
                            .append(")");
                } else {
                    whereBuilder.append(" INNER JOIN ")
                            .append(item.secondTable())
                            .append(" ON ")
                            .append(item.firstTable()).append(".").append(item.firstColumn())
                            .append("=")
                            .append(item.secondTable()).append(".").append(item.secondColumn());

                    if (i < items.length - 1) {
                        whereBuilder.append(")");
                    }
                }
            }
        }

        return whereBuilder.toString();
    }

    public static String buildLeftJoinClause(LeftJoin leftJoin){
        return leftJoin.firstTable() + " LEFT JOIN " + leftJoin.secondTable() + " ON " +
                leftJoin.firstTable() + "." + leftJoin.firstColumn() + "=" + leftJoin.secondTable() + "." + leftJoin.secondColumn();
    }

    public static String buildCrossJoinClause(CrossJoin crossJoin){
        return crossJoin.firstTable() + " CROSS JOIN " + crossJoin.secondTable();
    }

    public static String buildNaturalJoinClause(NaturalJoin naturalJoin){
        return naturalJoin.firstTable() + " NATURAL JOIN " + naturalJoin.secondTable();
    }
}
