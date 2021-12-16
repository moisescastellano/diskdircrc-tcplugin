package moi.date;

import moi.util.NumberPrinter;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Moises Castellano 2007
 * https://github.com/moisescastellano/tcmd-java-plugin
 * 
 */

public class DatePrinter {

    public static String normalDate(Calendar calendario) {
        return normalDate (calendario, new String[] {"-","-","_","-","-"} );
    }

    public static String normalDate (Date fecha) {
        Calendar calendario = new GregorianCalendar();
        calendario.setTime (fecha);
        return normalDate (calendario);
    }

    public static String normalDate (Calendar calendario, String[] separadores) {
        return NumberPrinter.dosDigitos(calendario.get(Calendar.YEAR)) + separadores[0]
            + NumberPrinter.dosDigitos(calendario.get(Calendar.MONTH)+1) + separadores[1]
            + NumberPrinter.dosDigitos(calendario.get(Calendar.DAY_OF_MONTH)) + separadores[2]
            + NumberPrinter.dosDigitos(calendario.get(Calendar.HOUR_OF_DAY)) + separadores[3]
            + NumberPrinter.dosDigitos(calendario.get(Calendar.MINUTE)) + separadores[4]
            + NumberPrinter.dosDigitos(calendario.get(Calendar.SECOND));
    }

    public static String normalDate (Date fecha, String separadores) {
        Calendar calendario = new GregorianCalendar();
        calendario.setTime (fecha);
        return normalDate (calendario, separadores);
    }

    public static String normalDate (Calendar calendario, String separadores) {
        return NumberPrinter.dosDigitos(calendario.get(Calendar.YEAR)) + separadores.charAt(0)
            + NumberPrinter.dosDigitos(calendario.get(Calendar.MONTH)+1) + separadores.charAt(1)
            + NumberPrinter.dosDigitos(calendario.get(Calendar.DAY_OF_MONTH)) + separadores.charAt(2)
            + NumberPrinter.dosDigitos(calendario.get(Calendar.HOUR_OF_DAY)) + separadores.charAt(3)
            + NumberPrinter.dosDigitos(calendario.get(Calendar.MINUTE)) + separadores.charAt(4)
            + NumberPrinter.dosDigitos(calendario.get(Calendar.SECOND));
    }

    public static String normalDate() {
        return normalDate(new GregorianCalendar());
    }

    public static String shortDate(Calendar calendario) {
        return shortDate (calendario, new String[] {"-","-"} );
    }

    public static String shortDate (Calendar calendario, String[] separadores) {
        return NumberPrinter.dosDigitos(calendario.get(Calendar.YEAR)) + separadores[0]
            + NumberPrinter.dosDigitos(calendario.get(Calendar.MONTH)+1) + separadores[1]
            + NumberPrinter.dosDigitos(calendario.get(Calendar.DAY_OF_MONTH));
    }

    public static String shortDate (Calendar calendario, String separadores) {
        return NumberPrinter.dosDigitos(calendario.get(Calendar.YEAR)) + separadores.charAt(0)
            + NumberPrinter.dosDigitos(calendario.get(Calendar.MONTH)+1) + separadores.charAt(1)
            + NumberPrinter.dosDigitos(calendario.get(Calendar.DAY_OF_MONTH));
    }

}
