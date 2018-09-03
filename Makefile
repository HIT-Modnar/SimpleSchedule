compile :
	cd src/application/; javac -d ../../bin/ Main.java

run :
	cd bin; java application/Main

gui :
	#cd src/gui/; javac -d ../../bin/ Schedule.java SelectWeekDialog.java
	cd src/; javac -d ../bin/ gui/Schedule.java gui/SelectWeekDialog.java course/Course.java 

drive :
	cd bin; java gui/Schedule
