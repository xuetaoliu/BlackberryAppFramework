package com.blackberryappframework.Utility;

import java.util.Date;
import java.util.TimeZone;

import net.rim.device.api.i18n.SimpleDateFormat;
import net.rim.device.api.io.http.HttpDateParser;
import net.rim.device.api.util.MathUtilities;

public class MeasurementUtils {

	// 1 meter = 0.000621371192 mile
	public static double meterToMile(double meter, int decimal) {
		double result = meter * 0.000621371192;

		result = adjustDecimal(result, decimal);
		return result;
	}

	private static double adjustDecimal(double value, int decimal) {
		double decimalNumber = MathUtilities.pow(10, decimal);

		double result = MathUtilities.round(value * decimalNumber);
		System.out.print(" adjustDecimal --- original:" + value + " decimal: "
				+ decimal + " result: " + result);

		result = result / decimalNumber;
		System.out.println(" final: " + result + " decimalNumber: "
				+ decimalNumber);
		return result;
	}

	private static final long YEAR_IN_MILLIS = 31556926000L;
	private static final long MONTH_IN_MILLIS = 2629743830L;
	private static final long WEEK_IN_MILLIS = 604800000L;
	private static final long DAY_IN_MILLIS = 86400000L;
	private static final long HOUR_IN_MILLIS = 3600000L;
	private static final long MINUTE_IN_MILLIS = 60000L;

	private static final String yearsPrefix = "ys";
	private static final String yearPrefix = "y";
	private static final String monthsPrefix = "mths";
	private static final String monthPrefix = "mth";
	private static final String weeksPrefix = "wks";
	private static final String weekPrefix = "wk";
	private static final String daysPrefix = "ds";
	private static final String dayPrefix = "d";
	private static final String hoursPrefix = "hrs";
	private static final String hourPrefix = "hr";
	private static final String minutesPrefix = "ms";
	private static final String minutePrefix = "m";

	public static boolean passedDate(String date) {
		Date formatter = new Date(HttpDateParser.parse(date));
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss+SSSS");
		int offset = TimeZone.getDefault().getRawOffset();
		formatter.setTime(formatter.getTime() + offset);

		long msValidDate = formatter.getTime();
		long diffInMillis = System.currentTimeMillis() - msValidDate;

		return (diffInMillis > 0);

	}

	public static boolean passedDate(long time) {
		Date formatter = new Date();

		int offset = TimeZone.getDefault().getRawOffset();
		formatter.setTime(time + offset);

		long msValidDate = formatter.getTime();
		long diffInMillis = System.currentTimeMillis() - msValidDate;

		return (diffInMillis > 0);

	}

	public static String getValidUntilFormated(String validDate) {

		Date formatter = new Date(HttpDateParser.parse(validDate));
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss+SSSS");
		int offset = TimeZone.getDefault().getRawOffset();
		formatter.setTime(formatter.getTime() + offset);

		long msValidDate = formatter.getTime();
		long diffInMillis = System.currentTimeMillis() - msValidDate;

		long years = Math.abs(diffInMillis / YEAR_IN_MILLIS);
		long months = Math.abs(diffInMillis / MONTH_IN_MILLIS);
		long weeks = Math.abs(diffInMillis / WEEK_IN_MILLIS);
		long days = Math.abs(diffInMillis / DAY_IN_MILLIS);
		long hours = Math.abs(diffInMillis / HOUR_IN_MILLIS);
		// We really don't want to show "Expires in 0 min". So minutes +1 solves
		// that
		long minutes = Math.abs(diffInMillis / MINUTE_IN_MILLIS) + 1;

		// Log.i(TAG, "years: " + years + " months " + months + " weeks " +
		// weeks + " days " + days + " hours " + hours +
		// " minutes " + minutes);

		long param1 = 0;
		String param2 = "";

		if (years != 0) {
			param1 = years;
			// Handle plural "s"
			if (param1 > 1) {
				param2 = yearsPrefix;
			} else {
				param2 = yearPrefix;
			}
		} else if (months != 0) {
			param1 = months;
			// Handle plural "s"
			if (param1 > 1) {
				param2 = monthsPrefix;
			} else {
				param2 = monthPrefix;
			}
		} else if (weeks != 0) {
			param1 = weeks;
			// Handle plural "s"
			if (param1 > 1) {
				param2 = weeksPrefix;
			} else {
				param2 = weekPrefix;
			}
		} else if (days != 0) {
			param1 = days;
			// Handle plural "s"
			if (param1 > 1) {
				param2 = daysPrefix;
			} else {
				param2 = dayPrefix;
			}
		} else if (hours != 0) {
			param1 = hours;
			// Handle plural "s"
			if (param1 > 1) {
				param2 = hoursPrefix;
			} else {
				param2 = hourPrefix;
			}
		} else {
			param1 = minutes;
			// Handle plural "s"
			if (param1 > 1) {
				param2 = minutesPrefix;
			} else {
				param2 = minutePrefix;
			}
		}

		String validUntilFormatedText = "";
		if (diffInMillis < 0) {
			validUntilFormatedText = param1 + " " + param2;
		} else {
			validUntilFormatedText = "Expired " + param1 + " " + param2
					+ " ago";
		}

		return " "+validUntilFormatedText;
	}

	public static String getValidUntilFormated(long time) {

		Date formatter = new Date();

		int offset = TimeZone.getDefault().getRawOffset();
		formatter.setTime(time + offset);

		long msValidDate = formatter.getTime();
		long diffInMillis = System.currentTimeMillis() - msValidDate;

		long years = Math.abs(diffInMillis / YEAR_IN_MILLIS);
		long months = Math.abs(diffInMillis / MONTH_IN_MILLIS);
		long weeks = Math.abs(diffInMillis / WEEK_IN_MILLIS);
		long days = Math.abs(diffInMillis / DAY_IN_MILLIS);
		long hours = Math.abs(diffInMillis / HOUR_IN_MILLIS);
		// We really don't want to show "Expires in 0 min". So minutes +1 solves
		// that
		long minutes = Math.abs(diffInMillis / MINUTE_IN_MILLIS) + 1;

		// Log.i(TAG, "years: " + years + " months " + months + " weeks " +
		// weeks + " days " + days + " hours " + hours +
		// " minutes " + minutes);

		long param1 = 0;
		String param2 = "";

		if (years != 0) {
			param1 = years;
			// Handle plural "s"
			if (param1 > 1) {
				param2 = yearsPrefix;
			} else {
				param2 = yearPrefix;
			}
		} else if (months != 0) {
			param1 = months;
			// Handle plural "s"
			if (param1 > 1) {
				param2 = monthsPrefix;
			} else {
				param2 = monthPrefix;
			}
		} else if (weeks != 0) {
			param1 = weeks;
			// Handle plural "s"
			if (param1 > 1) {
				param2 = weeksPrefix;
			} else {
				param2 = weekPrefix;
			}
		} else if (days != 0) {
			param1 = days;
			// Handle plural "s"
			if (param1 > 1) {
				param2 = daysPrefix;
			} else {
				param2 = dayPrefix;
			}
		} else if (hours != 0) {
			param1 = hours;
			// Handle plural "s"
			if (param1 > 1) {
				param2 = hoursPrefix;
			} else {
				param2 = hourPrefix;
			}
		} else {
			param1 = minutes;
			// Handle plural "s"
			if (param1 > 1) {
				param2 = minutesPrefix;
			} else {
				param2 = minutePrefix;
			}
		}

		String validUntilFormatedText = "";
		if (diffInMillis < 0) {
			validUntilFormatedText = param1 + " " + param2;
		} else {
			validUntilFormatedText = "Expired " + param1 + " " + param2
					+ " ago";
		}

		return " "+validUntilFormatedText;
	}
}
