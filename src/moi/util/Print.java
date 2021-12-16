package moi.util;

/** 
 * @author Moises Castellano
 * https://github.com/moisescastellano/tcmd-java-plugin
 */

public class Print {
	
    public static void ln (String s) {
        System.out.println (s);
    }

    public static void pln (String s) {
        System.out.println (s);
    }

    public static void p (String s) {
        System.out.print (s);
    }

    public static void p (Long l) {    	
        System.out.print (l);    	
    }
    
    public static void p (String[] ss) {
        for (int i = 0; i < ss.length; i++) {
            String s = ss[i];
            if (s== null) {
                pln();
            } else {
                pln (ss[i]);
            }
        }
    }

    public static void pln() {
        System.out.println ();
    }

    public static void ln() {
        System.out.println ();
    }

    public static void mark () {
        pln (marks[0]);
    }

    public static void mark (int marca) {
        pln (marks[marca]);
    }

    public static void upperMark (String s) {
        pln (marks[0]);
        pln (s);
    }

    public static void mark (String s) {
        pln (marks[0]);
        pln (s);
        pln (marks[0]);
    }

    public static void mark (String s, int marca) {
        pln (marks[marca]);
        pln (s);
        pln (marks[marca]);
    }

    public static void mark (String s, int marca1, int marca2) {
        pln (marks[marca1]);
        pln (s);
        pln (marks[marca2]);
    }

    public static String[] marks = {
        "---------------------------------------------------------------",
        "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++",
        "***************************************************************",
        "///////////////////////////////////////////////////////////////",
        "===============================================================",
        "..............................................................."
    };

}
