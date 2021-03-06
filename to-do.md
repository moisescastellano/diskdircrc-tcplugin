
DiskDirCrc Java plugin - Things To do
=====================================

This is a work in progress. **Help wanted!** - in particular with Visual C++ issues. See contact below.

Check also this project's [issues page](https://github.com/moisescastellano/diskdircrc-tcplugin/issues) and Java Plugin Interface's [issues page](https://github.com/moisescastellano/tcmd-java-plugin/issues).

Priority issues
---------
There are now a couple (closely related) issues reported regarding this Java plugin interface, which are difficult to solve for myself as they are Visual C++ related, which I am not familiar with.

For some JDK/JRE versions the plugin has a hard time to find or load the Java Virtual Machine.

[The first one](https://github.com/moisescastellano/javadecompiler-tcplugin/issues/1) is the error "**JRE is not installed**" when finding the library, that the plugin searchs in two ways described in the issue.

[The second one](https://github.com/moisescastellano/tcmd-java-plugin/issues/2) is plugin complains "**LoadLibrary Failed / Starting Java Virtual Machine failed**". Specific JREs failing are reported in the issue.

If you are giving a try to the Java plugin interface or any of its plugins, **some working (Oracle JREs) versions** are: jre1.8.0_211, jre-8u311-windows-x64

If you are a developer and can help, the [JVM search code is performed here](https://github.com/moisescastellano/tcmd-java-plugin/blob/main/src/vc-project/Total%20Commander%20Java%20Plugin/java.cpp)

Known bugs
----------
Dates are shown incorrectly (e.g. year shown as 2098). I think this is an error in the original java plugin, to be corrected.
Note: dates in the index file are **saved correctly**, as you can check by associating the extension with the original DiskDir plugin.
They are only **shown** incorrectly.

Standalone application for creating / checking index files
----------------------

A standalone java application (meaning Total Commander is not needed to execute it) is provided along with the plugin. 
**(Note: to be provided soon, just needs a bit documentation about usage for public release)**

This can be useful for things like:
 - batch-creating in background multiple lists of files
 - batch-checking the integrity of files in multiple lists
 - checking the integrity of files moved from the original location (specified in the first line of the list file)


Standalone application for incremental backups
----------------------
An incremental backup is one in which successive copies of the data contain only the portion that has changed since the preceding backup copy was made.

A basic tool for incremental backups is also provided along with the plugin.
**(Note: to be provided soon, just needs a bit documentation about usage for public release)**

Taking advantage of the indexing and CRC-checking facilities developed for the previous application, along with the zip libraries included in every Java release, the backup application just needs to read all the previous backup lists to know which files need to be archived in the new backup, checking CRCs if desired.

Important note!: Beware this tool can create incremental backups that are stored in a directory containing zips / indexes; however in case of needing a complete restoring of a backup at a certain date, a manual  process would have to be done based on the listings: I have never needed such a complete restoring and a tool for it has not been developed. See Disclaimer.

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

