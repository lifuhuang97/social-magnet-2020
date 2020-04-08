// package main.java;

import java.util.GregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


/** The official formatted date object 
 *  of the app.
 * 
 *  All dates in Social Magnet follows the
 *  format of dd/MM/yyyy HH:mm
 */
public class SMDate extends Date{
    
    private Date dateTime;

    /**
     * Constructs a SMDate object of the current time
     */
    public SMDate() {
        this.dateTime = new Date();
    }

    /**
     * Constructs a SMDate object with the input date and time
     * @param dateInput String of date time to be converted to a SMDate object
     */
    public SMDate(final String dateInput) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            this.dateTime = simpleDateFormat.parse(dateInput);
        }
        catch (ParseException ex) {
            System.out.println("Invalid Date Time Format");
            ex.printStackTrace();
        }
    }

    /**
     * Retrieves the dateTime
     * @returns the dateTime
     */
    private Date getDateTime() {
        return this.dateTime;
    }

    /**
     * Sets the dateTime
     * @param dateTime dateTime to set to
     */
    private void setDateTime(final Date dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Check if dateTime is equal to given dateTime
     * @param SMDate SMDate object to be compared against
     * @returns boolean of result
     */
    public boolean equals(final SMDate SMDate) {
        return this.dateTime.equals(SMDate.getDateTime());
    }

    /**
     * Check if dateTime is after the given dateTime
     * @param SMDate SMDate object to be compared against
     * @returns boolean of result
     */
    public boolean after(final SMDate date) {
        return this.dateTime.after(date.getDateTime());
    }

    /**
     * Check if dateTime is before given dateTime
     * @param SMDate SMDate object to be compared against
     * @returns boolean of result
     */
    public boolean before(final SMDate date) {
        return this.dateTime.before(date.getDateTime());
    }

    /**
     * Converts date to string
     * @returns string format of current date
     */
    @Override
    public String toString() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(this.dateTime);
    }

    /** Converts plantedDateTime to number of minutes of
     *  how long it has been since the crop is planted
     * 
     * @param plantedDateTime SMDate format of planted time
     * @return number of minutes since it has been planted
     * 
     *  Used to calculate growth percentage and whether crop
     *  should wilt
     */
    public int getPlantedTime (SMDate plantedDateTime){

        String plantedDateTimeStr = plantedDateTime.toString();
        String currentDateTimeStr = new SMDate().toString();
        
        // Custom date format
        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm");  
        
        Date startTime = null;
        Date currentTime = null;
        try {
            startTime = format.parse(plantedDateTimeStr);
            currentTime = format.parse(currentDateTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }    
        
        long diff = currentTime.getTime() - startTime.getTime();         

        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);

        int plantedTime = Math.toIntExact(minutes);

        return plantedTime;

    }

    /** This method is used to generate the number of minutes
     *  that the current time is from the input datetime.
     * 
     * 
     * @param date input datetime to compare against
     * @return the number of minutes since the input datetime
     */
    public static int getTimeDifferenceInMinutes(SMDate date){

        SimpleDateFormat defaultDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date plantedDate;
        Date currentTime = new Date();


        try{
            plantedDate = defaultDateFormat.parse(date.toString());

            long duration = currentTime.getTime() - plantedDate.getTime();
    
            long differenceInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
    
            return (int)differenceInMinutes;

        }catch(ParseException e){
            System.out.println("Something wrong with date format");
        }
        
        return -1;
    }

    /** This method is made especially for gifts, to check whether
     *  24 hours has passed from the input date time
     * 
     * @param date input datetime to check
     * @return true / false of whether it has been 24 hours
     */
    public static boolean checkIfSentWithinOneDay(SMDate date){

        int difference = getTimeDifferenceInMinutes(date);

        int NumberOfMinutesInADay = 60 * 24;

        if(difference > NumberOfMinutesInADay){
            return false;
        }else{
            return true;
        }

    }

}