package moi.tcplugins;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import moi.date.DatePrinter;
import moi.util.FileCRC;
import plugins.wcx.HeaderData;
import plugins.wcx.OpenArchiveData;
import plugins.wcx.WCXPluginAdapter;

/**
 * @author Moises Castellano 2021
 * based on Ken Handel's JCatalogue java plugin 2006 
 * https://github.com/moisescastellano/tcmd-java-plugin
 */

public class DiskDirCrcPlugin extends WCXPluginAdapter {
	
	private static PluginLogger log = new PluginLogger(); 

	private static final String DATE_PATTERN = "yyyy.MM.dd HH:mm.ss";

	/**
	 * The default locale.
	 */
	private Locale locale = Locale.getDefault();

	private class CatalogInfo {
		/**
		 * The name of the archive.
		 */
		private String arcName;

		/**
		 * The LST reader itself.
		 */
		private RandomAccessFile file;

		/**
		 * The current file pointer.
		 */
		private long fp;

		/**
		 * The current header data of the LST file.
		 */
		private HeaderData headerData;
		
		/**
		 * The original files dir
		 */
		private String originalDir;
		
		/**
		 * The last dir read in the archive
		 */
		private String lastDirRead;
	}

	@SuppressWarnings("resource")
	@Override
	public Object openArchive(OpenArchiveData archiveData) {
		if (log.isDebugEnabled()) {
			log.debug(this.getClass().getName() + ".openArchive(archiveData)");
		}
		try {
			RandomAccessFile pf = new RandomAccessFile(archiveData.getArcName(), "rw");
			CatalogInfo catalogInfo = new CatalogInfo();
			catalogInfo.file = pf;
			catalogInfo.arcName = archiveData.getArcName();
			catalogInfo.originalDir = null;
			return catalogInfo;
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public int closeArchive(Object archiveData) {
		if (log.isDebugEnabled()) {
			log.debug(this.getClass().getName() + ".closeArchive(archiveData)");
		}
		RandomAccessFile pf = ((CatalogInfo) archiveData).file;
		try {
			pf.close();
			return SUCCESS;
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return E_ECLOSE;
	}

	@Override
	public int processFile(Object archiveData, int operation, String destPath, String destName) {
		if (log.isDebugEnabled()) {
			log.debug(this.getClass().getName() + ".processFile(archiveData, operation, destPath, destName)");
		}
		CatalogInfo catalogInfo = (CatalogInfo) archiveData;
		HeaderData headerData = catalogInfo.headerData;
		String fullOriginName = catalogInfo.originalDir + headerData.getFileName();
		if (log.isDebugEnabled()) {
			log.debug(this.getClass().getName() + ".processFile() headerData = " + headerData.getFileName() + ", destPath = " + destPath
					+ ",  destName = " + destName + ", operation = " + operation + ", fullOriginName=" + fullOriginName);
		}
		try {
			if (operation == PK_EXTRACT) {
				String fullDestName = (destPath==null?"":destPath) + destName;
				if (log.isDebugEnabled()) {
					log.debug(this.getClass().getName() + ".processFile() EXTRACT " + fullDestName);
				}
				return copyFile(new File(fullOriginName), new File(fullDestName), false);
			} else if (operation == PK_TEST) {
				if (log.isDebugEnabled()) {
					log.debug(this.getClass().getName() + ".processFile() TEST " + headerData.getFileName());
				}
				return checkFile(new File(fullOriginName), headerData.getFileCRC());
			} else if (operation == PK_SKIP) {
				if (log.isDebugEnabled()) {
					log.debug(this.getClass().getName() + ".processFile() SKIP " + headerData.getFileName());
				}
			}
		} catch (RuntimeException e) {
			log.error(e.getMessage(), e);
		}
		return SUCCESS;
	}

	@Override
	public int readHeader(Object archiveData, HeaderData headerData) {
		if (log.isDebugEnabled()) {
			log.debug(this.getClass().getName() + ".readHeader(archiveData, headerData)");
		}
		try {
			CatalogInfo catalogInfo = (CatalogInfo) archiveData;
			catalogInfo.headerData = headerData;
			RandomAccessFile file = catalogInfo.file;
			headerData.setArcName(catalogInfo.arcName);
			if (catalogInfo.fp != -1) {
				file.seek(file.getFilePointer());
				log.debug("seek to " + file.getFilePointer());
			} else {
				catalogInfo.fp = file.getFilePointer();
				log.debug("fp=" + catalogInfo.fp);
			}
			String line = file.readLine();
			if (catalogInfo.originalDir == null) { // in the diskDir format, first line of the archive is the original directory
				catalogInfo.originalDir = line;
				line = file.readLine();
			}
			log.debug("read line=" + line);
			if (line != null) {
				parseLine(line, catalogInfo.lastDirRead, headerData);
				if (headerData.getFileName().endsWith(File.separator)) { // is a dir
					catalogInfo.lastDirRead = headerData.getFileName();
				}
				log.debug("SUCCESS");
				return SUCCESS;
			}
			log.debug("END_OF_ARCHIVE");
			return E_END_ARCHIVE;
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			
		}
		if (log.isErrorEnabled()) {
			log.error(this.getClass().getName() + ".readHeader() E_BAD_DATA");
		}
		return E_BAD_DATA;
	}
	
    private void parseLine (String line, String lastDir, HeaderData headerData) throws IOException {
        StringTokenizer st = new StringTokenizer (line,"\t");
        int num = st.countTokens();
        String name = st.nextToken();
        if (name.endsWith(File.separator)) {    // is a dir
            if (num != 4) {
                throw new IOException ("Error in file format: " + num + " tokens found");
            }
            headerData.setFileAttr(HeaderData.FILE_ATTRIBUTE_DIRECTORY);
            headerData.setFileName(name);
            headerData.setUnpSize(Long.parseLong(st.nextToken()));
			headerData.setFileTime(getFileTime(st.nextToken(),st.nextToken()));
        } else { // is a file (not a dir)
            if (num != 5) {
                if (num == 4) {
                    // throw new IOException ("Index without CRC");
                } else {
                    throw new IOException ("Error in file format: " + num +" tokens found");
                }
            }
            headerData.setFileName((lastDir==null?"":lastDir) + name); // lastDir can be null for files in the root of the archive
            headerData.setUnpSize(Long.parseLong(st.nextToken()));
			headerData.setFileTime(getFileTime(st.nextToken(),st.nextToken()));
			if (num == 5) {
				headerData.setFileCRC(Long.parseLong(st.nextToken()));
			} else {
				headerData.setFileCRC(0);
			}
        }
    }

    private Calendar getFileTime(String day, String hour) {
    	SimpleDateFormat df = new SimpleDateFormat();
    	df.applyPattern(DATE_PATTERN);
		Calendar cal = Calendar.getInstance(locale);
    	try {
    		cal.setTime(df.parse(day + " " + hour));
    	} catch (ParseException e) {
    		log.error(e.getMessage(), e);
    	}
    	if (log.isDebugEnabled()) {
    		log.debug(this.getClass().getName() + ".readHeader() time="	+ cal);
    	}
    	return cal;
    }


	@Override
	public int getPackerCaps() {
		return /* PK_CAPS_HIDE | */ PK_CAPS_NEW | PK_CAPS_MULTIPLE | PK_CAPS_MEMPACK;
	}

	@Override
	public int packFiles(String packedFile, String subPath, String srcPath,	String addList, int flags) {
		try {
			if (log.isDebugEnabled()) {
				log.debug(DiskDirCrcPlugin.class.getName() + ".packFiles() packedFile=" + packedFile 
						+ " - subPath=" + subPath+ " - srcPath=" + srcPath);
			}
			File pf = new File(packedFile);
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(pf)));
			writer.println(srcPath);
			StringTokenizer tok = new StringTokenizer(addList, ":");
			while (tok.hasMoreTokens()) {
				String addFilename = tok.nextToken();
				if (!addFilename.equals("")) {
					String fullName = srcPath + addFilename;
					File file = new File(fullName);
					printFile(file, addFilename, writer, true);					
					if (log.isDebugEnabled()) {
						log.debug(DiskDirCrcPlugin.class.getName() + ".packFiles() addFilename=" + addFilename);
					}
				}
			}
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return SUCCESS;
	}
	
    private void printFile (File file, String addFileName, PrintWriter pw, boolean calcCRC) throws IOException   {
    	
    		// addFilename is needed for subdirs; it already comes with backslash for dirs
    		if (file.isDirectory()) {
                pw.print(addFileName +  "\t");
    		} else {
    			pw.print(file.getName() + "\t");
    		}
            pw.print (file.length() + "\t");
            long lastModified = file.lastModified();
            pw.print (DatePrinter.normalDate(new Date(lastModified), "..\t:."));
            long crc = -1;
            if (calcCRC && !file.isDirectory()) {
                pw.print ('\t');
                try {
                	crc = FileCRC.calculaCRC(file);
                	pw.print (crc);
                } catch (Exception e) {
                	pw.print(e.getMessage());
                }
            }
            pw.println();
       }
	

	/**
	 * Copy source file to target file.
	 * 
	 * @param source
	 *            the source file
	 * @param dest
	 *            the target file
	 * @param overwrite
	 *            overwrite destination
	 * @return FS_FILE_... constants
	 */
	private int copyFile(final File source, final File dest, final boolean overwrite) {
		if (overwrite) {
			dest.delete();
		}
		if (dest.exists()) {
			return E_ECREATE;
		}
		FileWriter fw = null;
		FileReader fr = null;
		BufferedReader br = null;
		BufferedWriter bw = null;

		try {
			fr = new FileReader(source);
			fw = new FileWriter(dest);
			br = new BufferedReader(fr);
			bw = new BufferedWriter(fw);

			int fileLength = (int) source.length();

			char[] charBuff = new char[fileLength];

			while (br.read(charBuff, 0, fileLength) != -1) {
				bw.write(charBuff, 0, fileLength);
			}
		} catch (FileNotFoundException fnfe) {
			return E_EOPEN;
		} catch (IOException ioe) {
			return E_EWRITE;
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (bw != null) {
					bw.close();
				}
			} catch (IOException ioe) {
				return E_EWRITE;
			}
		}
		return SUCCESS;
	}

	private int checkFile(File source, long crcInIndex) {
		if (!source.exists()) {
			return E_EOPEN;
		}		
		if (crcInIndex == 0) { // no crc has been calculated
			return SUCCESS;
		}
		try {
			long crc = FileCRC.calculaCRC(source);
			return (crc == crcInIndex) ? SUCCESS : E_BAD_DATA;
		} catch (FileNotFoundException fnfe) {
			return E_EOPEN;
		} catch (IOException e) {
			return E_EREAD;
		}
	}

}
