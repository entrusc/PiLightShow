Pi LightShow
============

A simple light show written in Java to use with a Raspberry Pi (http://raspberrypi.org). All you need is a RGB-(HighPower-)LED
and a few electrical parts to build your very own light show!

This software runs on a default Raspberry Pi on a simple debian OS image (obtainabe from here: http://raspberrypi.org/downloads
use the soft float variant) with an installed Oracle JVM.

how to build / run?
===================

The entire project is build with maven. Just clone the master branch, open the directory in NetBeans and hit build. Or if
you prefer the command line: 

    mvn package

should build everything correctly.

After that a archive should have been created in ./target/ called LightShow.zip. Copy this file to your Pi (via SCP/FTP), unpack
it into a folder and start it in that folder on the Pi using the following command:

    java -jar LightShow-1.0-SNAPSHOT.jar

If you prefer the light show to only take place at night try something like that to just have it
active from before 08:00h and after 18:00h:

    java -jar LightShow-1.0-SNAPSHOT.jar 8 18

you can also use float values (like 8.5 for 08:30h).

To test the light show on your computer you can use the debugging mode. To do that you just need to pass the "debug" parameter
like that:

    java -jar LightShow-1.0-SNAPSHOT.jar debug

This will open a swing window displaying what the RGB-LED would normally show.
