
import java.util.GregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class SMDate {
    private Date dateTime;

    public SMDate() {
        this.dateTime = new Date();
    }

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

    private Date getDateTime() {
        return this.dateTime;
    }

    private void setDateTime(final Date dateTime) {
        this.dateTime = dateTime;
    }

    public boolean equals(final SMDate SMDate) {
        return this.dateTime.equals(SMDate.getDateTime());
    }

    public boolean after(final SMDate date) {
        return this.dateTime.after(date.getDateTime());
    }

    public boolean before(final SMDate date) {
        return this.dateTime.before(date.getDateTime());
    }

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

    @Override
    public String toString() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(this.dateTime);
    }

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