package com.nphc.hr.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateValidation {

	public static void main(String[] args)throws Exception {
		// TODO Auto-generated method stub
		DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // convert your date into desired input date format
        Date inputDate = inputDateFormat.parse("2015-07-20");
        // above lines gives Parse Exception if date is UnParsable


        // define the date format in which you want to give output
        // i.e dd-MMM-yy

        DateFormat outputDateFormat = new SimpleDateFormat("dd-MMM-yy");

        // don't assign it back to date as format return back String
        String outputDate = outputDateFormat.format(inputDate);

        System.out.println(outputDate);
	}

}
