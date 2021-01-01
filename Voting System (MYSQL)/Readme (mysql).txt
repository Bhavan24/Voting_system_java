===========
README FILE 
=========== 

IDE - IntelliJ IDEA IDE
Java jdk Version – 14.0.2
Database - Mysql
Libraries
• JavaFX -> javafx-sdk-15.0.1 ( https://gluonhq.com/products/javafx/ )
 Java mysql driver (https://mvnrepository.com/artifact/mysql/mysql-connector-java/8.0.22 )
• JAVAFX MATERIAL DESIGN LIBRARY -> JFOENIX (https://mvnrepository.com/artifact/com.jfoenix/jfoenix/9.0.1 )


FOLDER CONTAINS 3 FOLDERS DATABASE, ELECTION, LIBRARIES (ELECTION FOLDER IS THE PROJECT FOLDER)
(PLEASE CLICK THE INFORMATION BUTTON ON THE TOP RIGHT CORNER OF 'ELECTION APPLICATION' TO VIEW MORE DETAILS ABOUT USING THE APPLICATION)

BEFORE RUNNING THE APPLICATION MAKE SURE TO

1. ADD THE LIBRARIES

> ADD JAVAFX LIBRARY TO THE PROJECT (IT IS NOT INCLUDED IN PROJECT FOLDER)
> ADD JFOENIX LIBRARY TO THE PROJECT (CHECK THE LIBRARIES FOLDER)
> ADD J CONNECTOR LIBRARY TO THE PROJECT (CHECK THE LIBRARIES FOLDER)

2. ADD THE DATABASE

> MAKE THE DATABASE, USING THE SQL FILE GIVEN IN THE DATABASE FOLDER, WITHOUT THE DATABASE THE PROJECT WON'T RUN.
> VOTERS.CSV FILE CONTAINS VOTERS, YOU CAN ADD THEM TO THE DATABASE INSIDE THE APPLICATION
  ********************************************************************************************************************
  IF YOU FACE ANY ISSUES INSERTING THE CSV FILE, 
  KINDLY REFER TO THE WEBSITE BELOW, YOU NEED TO CHANGE SOME SETTINGS IN YOUR MYSQL DATABASE
  https://stackoverflow.com/questions/32737478/how-should-i-tackle-secure-file-priv-in-mysql
  ********************************************************************************************************************

3. CHANGE THE VM OPTIONS

> BEFORE RUNNING THE APPLICATION, IN YOUR INJELLIJ IDE GO TO EDIT CONFIGURATION AND CHANGE THE VM OPTION
  ********************************************************************************************************************************************************
  VM OPTION: --module-path "YOUR_JAVAFX_PATH" --add-modules javafx.controls,javafx.fxml --add-opens java.base/java.lang.reflect=ALL-UNNAMED
  > PUT YOUR JAVAFX/LIB FOLDER PATH INSTEAD OF YOUR_JAVAFX_PATH.
  ********************************************************************************************************************************************************

4. ADMIN PANEL LOGIN DETAILS

Username	--------->	Password
------------------------------------------------------------
  admin1	-------->	1	
  admin2	--------->	2	
  admin3	--------->	3
-----------------------------------------------------------

THANK YOU!

NAME = LOGANATHAN BHAVANEETHARAN
EMAIL = bhavan2410@gmail.com
 
 
 