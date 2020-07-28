package com.datacvg.sempmobile.baseandroid.room;


import androidx.room.TypeConverter;

import java.util.Date;

/**
 * FileName: DateConverter
 * Author: 曹伟
 * Date: 2019/9/16 15:32
 * Description:
 */

public class DateConverter {

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}
