package com.ritesh.edufleet.system;

import com.ritesh.edufleet.exception.BadRequestException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DateParser {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public Date parseDate(String dateString) {
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            throw new BadRequestException("Date format is not valid");
        }
    }


}
