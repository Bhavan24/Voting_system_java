===========
README FILE 
=========== 

IDE - IntelliJ IDEA IDE
Java jdk Version – 14.0.2
Database - MongoDB
Libraries
• JavaFX -> javafx-sdk-15.0.1 ( https://gluonhq.com/products/javafx/ )
• Java MongoDB driver (https://mvnrepository.com/artifact/org.mongodb/mongo-java-driver )
• JAVAFX MATERIAL DESIGN LIBRARY -> JFOENIX (https://mvnrepository.com/artifact/com.jfoenix/jfoenix/9.0.1 )
 Build automation tool (Framework support) - Maven

FOLDER CONTAINS 2 FOLDERS DATABASE, ELECTION (ELECTION FOLDER IS THE PROJECT FOLDER)
(PLEASE CLICK THE INFORMATION BUTTON ON THE TOP RIGHT CORNER OF 'ELECTION APPLICATION' TO VIEW MORE DETAILS ABOUT USING THE APPLICATION)

BEFORE RUNNING THE APPLICATION MAKE SURE TO...

1. ADD THE LIBRARIES;

> ADD JAVAFX LIBRARY TO THE PROJECT (IT IS NOT INCLUDED IN THE PROJECT FOLDER)
> RELOAD THE POM.XML FILE, ALL NEEDED LIBRARIES WILL BE ADDED.

2. ADD THE DATABASE;

>  CREATE THE MONGODB DATABASE CALLED 'election'. CREATE 4 COLLECTIONS ‘candidate’, ’voter’, ’admin’, ’result’. 
    ADD DATA TO ‘admin’, ‘candidate’, ‘result’ COLLECTIONS USING .JSON FILES GIVEN IN THE DATABASE FOLDER. 
    WITHOUT THE DATABASE THE PROJECT WILL NOT RUN.
> ‘VOTERS.CSV’ FILE CONTAINS VOTERS. YOU CAN ADD THEM TO THE DATABASE INSIDE THE ELECTION APPLICATION. 

3. CHANGE THE VM OPTIONS;

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
 
 