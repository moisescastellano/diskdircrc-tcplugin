
DiskDirCrc Java plugin - Things To do
=====================================
This is a work in progress. 

Known bugs
----------
Dates are shown incorrectly (e.g. year shown as 2098). I think this is an error in the original java plugin, to be corrected.
Note dates in the index file are saved correctly, as you can check by associating the extension with the original DiskDir plugin.
They are only *shown* incorrectly.

Logging
----------

I have not been able to get the logging for plugins to work with Log4J. 
So I have created a very simple PluginLogger class.
To enable logging, a dir "c:/logs/[level]" should be created, where [level] is the lowest to be logged, e.g. "c:/logs/debug"

Problems with Log4J in the java plugin:
 
Handel configured the ZeroConfSocketHubAppender by default, that works along with Apache Chainsaw, sending logs visa sockets and showing in a GUI. 
I installed Chainsaw, but just the initial connections is logged. 
Also Chainsaw is quite deprecated, having a main page with lots of broken links, and dependencies are hard to find.
 
So I tried to update the commons-logging and log4j libraries and config them to log in file, but also that did not work.
 
Then I updated to log4j2, it didn't work either. 
 
I have come to the conclusion that the way the PluginClassLoader loads the jars from javalib is incompatible with Log4j's Loggers instantiation.

If you wanna give a try to using Log4J within this plugin, let me know if you can make it work.

Further testing
----------
Further testing should be done, with things such as Unicode characters.
If you use this plugin, let me know whether it works for you, or any problem you got into.

Contact
----------
If you want to help with the things above, or you have any comment, suggestion or problem regarding this java plugin,
you contact me at:
 - email: moises.castellano (at) gmail.com
 - [DiskDirCrc github project issues page](https://github.com/moisescastellano/diskdircrc-tcplugin/issues)
Please specify the java plugin and the JRE version you are using.

