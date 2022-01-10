DiskDirCrc - history of changes
===============================

v1.1 - 2022-jan-10
------------------
- DiskDirCrc 1.1 is now based on [Java Plugin Interface v2.2](https://github.com/moisescastellano/tcmd-java-plugin)
  - PluginClassLoader was incomplete (missing e.g. findResources implementation). It has now been completed
- DiskDirCrc is now hosted at [Github pages](https://moisescastellano.github.io/diskdircrc-tcplugin/)
- Source code is now available at [Github project](https://github.com/moisescastellano/diskdircrc-tcplugin)
- Corrected: erroneous extra string on errormessages.ini showing some memory dump
- Logging is updated to SLF4J (previously was based on deprecated Apache commons-logging implementation).
  - Logging now works for Log4j2 via SLF4J.
  - Logging is disabled by default, any logging implementation has been removed from the plugin itself.
  - Documentation about [how to configure logging for plugins](https://github.com/moisescastellano/tcmd-java-plugin/blob/main/logging.md).

v1.0 - 2021-dec-16
------------------
- DiskDirCrc is now available at [Totalcmd.net](http://totalcmd.net/plugring/diskdircrc.html)
- DiskDirCrc creates a list file with all selected files and directories, including subdirs. 
  - You can then "navigate" this list with Total Commander as if it was an archive or directory containing the files.
  - DiskDirCrc also calculates the CRC of the files and writes them into the index file.
  - DiskDirCrc can then check (Alt+Shift+F9) the integrity of files comparing the CRC in the list.
