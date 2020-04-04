
import java.util.GregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class SMDate {
    private Date dateTime;

    /**
     * Constructs a SMDate object
     */
    public SMDate() {
        this.dateTime = new Date();
    }

    /**
     * Constructs a SMDate object with the current date and time
     * @param dateInput
     */
    //TODO check if description is correct
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
     * Increment current date by a given amount
     * @param amount amount to be increased by
     * @returns SMDate of incremeneted date
     */
    public SMDate computeDate(final int amount) {
        final GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(this.dateTime);
        try {
            gregorianCalendar.add(11, amount);
        }
        catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
        final SMDate smDate = new SMDate();
        smDate.setDateTime(gregorianCalendar.getTime());
        return smDate;
    }

    /**
     * Converts date to string
     * @returns string format of current date
     */
    @Override
    public String toString() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(this.dateTime);
    }

    /**
     * Converts planted time from SMDate format to int format
     * @param plantedDateTime SMDate format of planted time
     * @returns int format of planted time representing minutes
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

}