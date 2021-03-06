package moi.tcplugins;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


/** 
 * @author Moises Castellano 2007-2021
 * https://github.com/moisescastellano/tcmd-java-plugin
 * 
 * I have not been able to get the logging for plugins to work with Log4J. 
 * So I have created a very simple PluginLogger class.
 * To enable logging, a dir "c:/logs/[level]" should be created, where [level] is the lowest to be logged, e.g. "c:/logs/debug"
 *
 * ---------------------------------------------------------------------------------------
 * Problems with Log4J in the java plugin:
 * 
 * Handel configured the ZeroConfSocketHubAppender by default, that works along with Apache Chainsaw, sending logs visa sockets and showing in a GUI. 
 * I installed Chainsaw, but just the initial connections is logged. 
 * Also Chainsaw is quite deprecated, having a main page with lots of broken links, and dependencies are hard to find.
 * 
 * So I tried to update the commons-logging and log4j libraries and config them to log in file, but also that did not work.
 * 
 * Then I updated to log4j2, it didn't work either. 
 * 
 * I have come to the conclusion that the way the PluginClassLoader loads the jars from javalib is incompatible with Log4j's Loggers instantiation.
 * 
 * If you wanna give a try to using Log4J within this plugin, let me know if you can make it work.
*/

public class PluginLogger {
	
	static boolean TRACE_ENABLED = false;
	static boolean DEBUG_ENABLED = false;
	static boolean INFO_ENABLED = false;
	static boolean WARN_ENABLED = false;
	static boolean ERROR_ENABLED = false;
	static boolean FATAL_ENABLED = false;

	static File logsDir;
	static PrintWriter pw = null;
	
	static {
		TRACE_ENABLED = setLogsDir("c:/logs/trace");
		DEBUG_ENABLED = TRACE_ENABLED ? true : setLogsDir("c:/logs/debug");
		INFO_ENABLED = DEBUG_ENABLED ? true : setLogsDir("c:/logs/info");
		WARN_ENABLED = INFO_ENABLED ? true : setLogsDir("c:/logs/warn");
		ERROR_ENABLED = WARN_ENABLED ? true : setLogsDir("c:/logs/error");
		FATAL_ENABLED = ERROR_ENABLED ? true : setLogsDir("c:/logs/fatal");
	}

	private static boolean setLogsDir(String dir) {
		logsDir = new File(dir);
		return logsDir.exists();
	}

	static {
		if (FATAL_ENABLED) {
			try {
				File f = new File(logsDir, "javaplugin.log");
				int n = 0;
				while (f.exists()) {
					n++;
					f = new File(logsDir, "javaplugin" + n +".log");
				}
				f.createNewFile();
				pw = new PrintWriter(new FileWriter(f));
				pw.println("Logging enabled");
				pw.flush();
			} catch (Throwable t) {
				errorLogging(t);
			}
		}
	}
	
	public boolean isTraceEnabled() { return TRACE_ENABLED;	}
	public boolean isDebugEnabled() { return DEBUG_ENABLED;	}
	public boolean isInfoEnabled() { return INFO_ENABLED; }
	public boolean isWarnEnabled() { return WARN_ENABLED; }
	public boolean isErrorEnabled() { return ERROR_ENABLED; }
	public boolean isFatalEnabled() { return FATAL_ENABLED; }

	public void trace(String s) { if (TRACE_ENABLED) log ("TRACE:" + s); }
	public void debug(String s) { if (DEBUG_ENABLED) log ("DEBUG:" + s); }
	public void info(String s) { if (INFO_ENABLED) log ("INFO:" + s); }
	public void warn(String s) { if (WARN_ENABLED) log ("WARN:" + s); }
	public void error(String s) { if (ERROR_ENABLED) log ("ERROR:" + s); }
	public void fatal(String s) { if (FATAL_ENABLED) log ("FATAL:" + s); }

	public void trace(String s, Throwable t) { if (TRACE_ENABLED) log ("TRACE:" + s, t); }
	public void debug(String s, Throwable t) { if (DEBUG_ENABLED) log ("DEBUG:" + s, t); }
	public void info(String s, Throwable t) { if (INFO_ENABLED) log ("INFO:" + s, t); }
	public void warn(String s, Throwable t) { if (WARN_ENABLED) log ("WARN:" + s, t); }
	public void error(String s, Throwable t) { if (ERROR_ENABLED) log ("ERROR:" + s, t); }
	public void fatal(String s, Throwable t) { if (FATAL_ENABLED) log ("FATAL:" + s, t); }

	public void log(String s, Throwable t) {
		log("Throwable class: " + t.getClass() + " - Throwable msg: " + t.getMessage() + " - " + s);			
	}	
	
	public void log(String s) {
		if (pw != null) {
			try {
				pw.println(s);
				pw.flush();
			} catch (Throwable t) {
				errorLogging(t);
			}
		}
	}
	
	public static void errorLogging(Throwable t) {
		errorLogging(t.getClass() + " - " + t.getMessage());
	}
	
	public static void errorLogging(String s) {
		try {
			File f = new File(logsDir, "javapluginerror.log");
			int n = 0;
			while (f.exists()) {
				n++;
				f = new File(logsDir, "javapluginerror" + n +".log");
			}
			f.createNewFile();
			PrintWriter pw = new PrintWriter(new FileWriter(f));
			pw.println("errorLogging: " + s);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
