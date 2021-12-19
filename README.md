DiskDirCrc 1.0, a [Total Commander](https://www.ghisler.com/) plugin
====================================

As the original DiskDir plugin, DiskDirCrc creates a list file with all selected files and directories, including subdirs. You can then "navigate" this list with Total Commander as if it was an archive or directory containing the files.

DiskDirCrc also calculates the CRC of the files and writes them into the index file. CRC is an error-detecting code commonly used in digital storage devices to detect accidental changes to data. DiskDirCrc can then check (Alt+Shift+F9) the integrity of files comparing the CRC in the list.

The format of the list file created by DiskDirCrc is compatible both ways to the format of DiskDir, meaning DiskDirCrc can read/navigate lists created by DiskDir and viceversa. The CRC is stored in an aditional column that DiskDir just happens to ignore.

Said format is plain text (human readable) where the first line stores the location of files (e.g. an external drive) when the list is created. When extracting or checking CRC in files, the original files should be in this same location, since the index file is not really an archive so it does not contain those files.

If the original files are in other location (e.g. another drive unit) and you want to check their CRC without moving them, you have two options:
 - manually change the path in the first line of the list file
 - use the standalone java application provided along the plugin, see below

Download and resources
----------------------
- Download the [latest release in this project](https://github.com/moisescastellano/diskdircrc-tcplugin/blob/main/releases/1.0/DiskDirCrc.zip)
- [Plugin page at totalcmd.net](http://totalcmd.net/plugring/diskdircrc.html)
- [Thread for discussing this plugin](https://www.ghisler.ch/board/viewtopic.php?t=75748) at the TC forum
- This is a work in progress, you can help with [things to do](https://github.com/moisescastellano/diskdircrc-tcplugin/blob/main/to-do.md)

Java plugin
----------------------
DiskDirCrc is written in Java, so you need to have installed a [Java Runtime Environment (JRE)](https://www.java.com/en/download/manual.jsp).

This plugin is based on the [Java plugin interface](https://github.com/moisescastellano/tcmd-java-plugin)


Known bugs and things to-do
----------------------
Dates are shown incorrectly (e.g. year shown as 2098). This is an error not in my code but in the java plugin library. Soon to be corrected.
Note: dates in the index file are in fact saved correctly, as you can check by associating the extension with the original DiskDir plugin. They are just *shown* incorrectly.

Refer to [things to do](https://github.com/moisescastellano/diskdircrc-tcplugin/blob/main/to-do.md) for other work in progress.


Speed of DiskDirCrc vs DiskDir
----------------------

In order to calculate CRCs, DiskDirCrc has to open and read the files contents. So creating a list file will take longer than DiskDir. Having the CRC in the list adds no appreciable delay for opening and navigating the list.

DiskDirCrc is a Java plugin. The first time you open a list file the JVM has to be instantiated, which in a modern computer takes less than a couple seconds. Opening following list files, or navigating, is as quick as in DiskDir. 


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


A bit of history
----------------------
I created first the standalone application for index files in 2002. Back then DiskDir and its list files were very useful for knowing and navigating what I had in CD-ROMs, as they are now for which is in external drives and others; however the former ones were more error-prone so I needed an integrity verification tool that happened to be perfectly integrable into Diskdir list files.

Development of incremental backup application began in 2005, then was abandoned for a long (long) time, then finished in 2017.

In 2021 I took in charge the adaptation to 64-bit Total Commander of abandoned Java Plugin (c) 2006-2007 Ken Handel, because I wanted to develop DiskDirCrc as a TC plugin and Java is my favourite language. I was on the verge of abandoning it but Ghisler (author of TC) stepped in to help (see https://www.ghisler.ch/board/viewtopic.php?t=75726). From there, development of DiskDirCrc plugin itself took just a couple hours.


Contact
----------------------
Author: Moises Castellano 2021

If you have any comment, suggestion or problem regarding this java plugin,
you contact me at:
 - email: moises.castellano (at) gmail.com
 - [github project issues page](https://github.com/moisescastellano/diskdircrc-tcplugin/issues)

Please specify the java plugin and the JRE version you are using.


Disclaimer
----------------------
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS “AS IS” AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


License
----------------------
Licensed under under the GNU General Public License v3.0, a strong copyleft license:
https://github.com/moisescastellano/tcmd-java-plugin/blob/main/LICENSE




