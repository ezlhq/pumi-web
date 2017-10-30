package com.pumi.pumi_web.utls;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public final static String dateTimeSecondPattern = "yyyy-MM-dd HH:mm:ss";

	public static String getCurrentData(){
		return new SimpleDateFormat(dateTimeSecondPattern).format(new Date());
	}
}
