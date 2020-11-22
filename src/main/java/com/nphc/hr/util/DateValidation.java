package com.nphc.hr.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateValidation {

	
	public static Date validateDate(String stringDate) throws Exception {
		
		SimpleDateFormat logformat1 = new SimpleDateFormat("dd-MMM-yy");
		SimpleDateFormat logformat2 = new SimpleDateFormat("yyyy-MM-dd");

		java.util.Date date = null;
		try {
			date = new Date(logformat1.parse(stringDate).getTime());			

		} catch (ParseException e) {
			try {
				date = new Date(logformat2.parse(stringDate).getTime());				
			} catch (ParseException p) {
				p.getMessage();
			}
		}

		return date;
	}
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
