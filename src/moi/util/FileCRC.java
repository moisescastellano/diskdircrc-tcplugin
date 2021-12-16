package moi.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.CRC32;

/** 
 * @author Moises Castellano
 * https://github.com/moisescastellano/tcmd-java-plugin
 */

public class FileCRC {

    private static int leidosParaPunto = 4*1024*1024;
    private static byte buffer[] = new byte[64*1024]; // Size of reading buffer

    public synchronized static long calculaCRC (Object file) throws IOException {
    	
        InputStream fis = null;
    	try {
    		fis = createInputStream (file);
	        return calculaCRC(fis);
    	} finally {
    		if (fis != null) {
    			fis.close();
    		}
    	}
    	
    }
    
    private static InputStream createInputStream (Object o) throws IOException {
    	
		if (o instanceof File) {
			return new FileInputStream ((File) o);
		} else if (o instanceof Path) {
			return Files.newInputStream((Path) o);
		} else {
			throw new RuntimeException("not a File or Path");
		}
		
    }


	private static long calculaCRC(InputStream fis) throws IOException {
		CRC32 crc = new CRC32();

		int leidos = 0;

		// read function does not guarantee that everything will be read
		int i;
		while ((i=fis.read(buffer))!=-1) { // while something is read
		    crc.update (buffer, 0, i);
		    leidos += i;
		    if (leidos > leidosParaPunto) {
		        leidos = 0;
		        Print.p (".");
		    }
		}
		return crc.getValue();
	}

}
