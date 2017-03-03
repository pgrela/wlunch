package com.pgrela.wlunch.utils;

import java.util.Calendar;

public class CalendarUtil {

    public static boolean areTheSameDays(Calendar first, Calendar second) {
        return first.get(Calendar.ERA) == second.get(Calendar.ERA)
                && first.get(Calendar.YEAR) == second.get(Calendar.YEAR)
                && first.get(Calendar.DAY_OF_YEAR) == second.get(Calendar.DAY_OF_YEAR);
    }


}
