package moi.util;

/** 
 * @author Moises Castellano
 * https://github.com/moisescastellano/tcmd-java-plugin
 */

public class NumberPrinter {
    public static String tresDigitos (int entero) {
        return (entero < 10 ? "00" : (entero < 100 ? "0" : "")) + entero;
    }

    public static String dosDigitos (int entero) {
        return (entero < 10 ? "0" : "") + entero;
    }

    public static String separaMiles (long i) {

        String s = "" + i;
        if (i < 1000) {
            return s;
        }

        StringBuffer sb = new StringBuffer();
        int p = s.length() % 3;
        if (p==0) {
            p = 3;
        }
        sb.append(s.substring(0,p));
        while (p != s.length()) {
            sb.append (",");
            sb.append (s.substring(p,p+3));
            p += 3;
        }
        return sb.toString();
    }

    public static void main (String args[]) {
        System.out.println (NumberPrinter.separaMiles(1567));
        System.out.println (NumberPrinter.separaMiles(56));
        System.out.println (NumberPrinter.separaMiles(0));
        System.out.println (NumberPrinter.separaMiles(-1));
        System.out.println (NumberPrinter.separaMiles(123456789));
        System.out.println (NumberPrinter.separaMiles(999));
        System.out.println (NumberPrinter.separaMiles(1000));

    }


}
