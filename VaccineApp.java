import java.sql.*;

import java.util.Scanner;

public class VaccineApp {


    public static void main(String[] args) throws SQLException
    {
        // Unique table names.  Either the user supplies a unique identifier as a command line argument, or the program makes one up.
        String tableName = "";
        int sqlCode=0;      // Variable to hold SQLCODE
        String sqlState="00000";  // Variable to hold SQLSTATE
        int userChoice = -1;

        Scanner scanner = new Scanner(System.in).useDelimiter("\n");


        /*if ( args.length > 0 )
            tableName += args [ 0 ] ;
        else
            tableName += "exampletbl";
    */
        // Register the driver.  You must register the driver before you can use it.
        try { DriverManager.registerDriver ( new com.ibm.db2.jcc.DB2Driver() ) ; }
        catch (Exception cnfe){ System.out.println("Class not found"); }

        // This is the url you must use for DB2.
        //Note: This url may not valid now !
        String url = "jdbc:db2://localhost:6667/cs421";//"jdbc:db2://winter2021-comp421.cs.mcgill.ca:50000/cs421";

        //REMEMBER to remove your user id and password before submitting your code!!
        String your_userid = "mma110"; // null;
        String your_password = "sMT7bh4Y"; // null;
        //AS AN ALTERNATIVE, you can just set your password in the shell environment in the Unix (as shown below) and read it from there.
        //$  export SOCSPASSWD=yoursocspasswd
        if(your_userid == null && (your_userid = System.getenv("SOCSUSER")) == null)
        {
            System.err.println("Error!! do not have a password to connect to the database!");
            System.exit(1);
        }
        if(your_password == null && (your_password = System.getenv("SOCSPASSWD")) == null)
        {
            System.err.println("Error!! do not have a password to connect to the database!");
            System.exit(1);
        }
        Connection con = DriverManager.getConnection (url,your_userid,your_password) ;
        Statement statement = con.createStatement ( ) ;

        while(true){

            System.out.println("VaccineApp Main Menu\n" +
                    "1. Add a Person\n" +
                    "2. Assign a slot to a Person\n" +
                    "3. Enter Vaccination information\n" +
                    "4. Exit Application\n" +
                    "Please Enter Your Option:");

            userChoice = scanner.nextInt();
            System.out.println("User choice is: " + userChoice);

            if(userChoice == 1) {
                // get input info
                System.out.println( "Please Enter the health insurance number:");
                int hid =  scanner.nextInt();

                System.out.println( "Please Enter the person's name:");
                String pname =  scanner.next();
                pname = "\'" + pname + "\'";

                System.out.println( "Please Enter the city:");
                String city =  scanner.next();
                city = "\'" + city + "\'";

                System.out.println( "Please Enter the poscode:");
                String poscode =  scanner.next();
                poscode = "\'" + poscode + "\'";

                System.out.println( "Please Enter the street information:");
                String streetInfo =  scanner.next();
                streetInfo = "\'" + streetInfo + "\'";

                System.out.println( "Please Enter the birthdate:");
                String birthDate =  scanner.next();
                birthDate = "\'" + birthDate + "\'";
                //Date birthDate=Date.valueOf(strBirthDate);//converting string into sql date

                System.out.println( "Please Enter the phone:");
                String phone =  scanner.next();
                phone = "\'" + phone + "\'";

                System.out.println( "Please Enter the gender:");
                String gender =  scanner.next();
                gender = "\'" + gender + "\'";

                System.out.println( "Please Enter the level name (priority level):");
                String levelName =  scanner.next();
                levelName = "\'" + levelName + "\'";


                tableName = "Applicants";

                // Querying the applicant table
                try
                {
                    String querySQL = "SELECT healthid from " + tableName + " WHERE healthid = " + hid;
                    //System.out.println (querySQL) ;
                    java.sql.ResultSet rs = statement.executeQuery ( querySQL ) ;

                    boolean hidExist = false;

                    while ( rs.next ( ) )
                    {
                        hidExist = true;
                        System.out.println("Person exists!");
                        int hidRecord = rs.getInt ( 1 ) ;
                        System.out.println ("The hid exits:  " + hidRecord);
                        System.out.println ("Would you like to proceed and update the information to this person? y/n");
                        String proceed = scanner.next();
                        if(proceed.equalsIgnoreCase("y")){
                            // continue update
                            String updateSQL = "UPDATE applicants " + " SET pname = " + pname + ","
                                    + "city = " + pname + ","
                                    + "poscode = " + poscode + ","
                                    + "streetinfo = " + streetInfo + ","
                                    + "birthdate = " + birthDate + ","
                                    + "phone = " + phone + ","
                                    + "gender = " + gender + ","
                                    + "levelname = " + levelName +
                                    "WHERE healthid = " + hid + ";";

                            statement.executeUpdate(updateSQL);
                            System.out.println("DONE updating");


                        }
                        else{
                            // stop
                            System.out.println("No update, request terminates");
                            break;
                        }

                        break;// for safety we only check one round
                    }
                    if(!hidExist) {
                        System.out.println("This hid does not exist before, this is a new person record.");
                        // add the person information to the table

                        String insertSQL2 = "INSERT INTO applicants(healthid,pname,city,poscode,streetinfo,birthdate,phone,gender,levelname) VALUES (2000,'Jane Donk','Montreal','H2X3Q4','Rue350','1998-12-30','4389287701','M','everbodyelse');";

                        String insertSQL = "INSERT INTO Applicants (healthid,pname,city,poscode,streetinfo,birthdate,phone,gender,levelname) " +
                                "VALUES ( "  +hid + "," + pname + ", "+ city + ", "+ poscode + ", "+ streetInfo
                                + ", "+ birthDate + ", "+ phone + ", "+ gender + ", "+ levelName + " );";

                        System.out.println(insertSQL);

                        statement.executeUpdate(insertSQL);
                        System.out.println("DONE");

                    }

                }catch (SQLException e)
                {
                    sqlCode = e.getErrorCode(); // Get SQLCODE
                    sqlState = e.getSQLState(); // Get SQLSTATE

                    // Your code to handle errors comes here;
                    // something more meaningful than a print would be good
                    System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
                    System.out.println(e);
                }

            }
            else if(userChoice == 2) {


                System.out.println( "Please Enter the health insurance number:");
                int hid =  scanner.nextInt();

                System.out.println( "Please Enter the location name:");
                String locname =  scanner.next();
                locname = "\'" + locname + "\'";



                System.out.println( "Please Enter the slot time:");
                String slottime =  scanner.next();
                slottime = "\'" + slottime + "\'";

                System.out.println( "Please Enter the slot date:");
                String slotdate =  scanner.next();
                Date slotdateInDate=Date.valueOf(slotdate);
                slotdate = "\'" + slotdate + "\'";

                System.out.println( "Please Enter the tent number:");
                int tentnum =  scanner.nextInt();

                System.out.println( "Please Enter the allocation date (Today's date):");
                String allocationdate =  scanner.next();
                Date allocationInDate=Date.valueOf(allocationdate);//converting string into sql date
                allocationdate = "\'" + allocationdate + "\'";



                // check the applicant dose times using the sid
                // Querying the applicant table
                try
                {
                    String querySQL = "SELECT VBRAND, DOSETIMES " +
                            "FROM VACCSHOTS " +
                            "WHERE HEALTHID = " + hid + ";";

                    System.out.println (querySQL) ;
                    java.sql.ResultSet rs = statement.executeQuery ( querySQL ) ;

                    boolean assignSatisfied = true;

                    while ( rs.next ( ) )
                    {
                        int numDoseReq = -1;
                        int doseTimes = rs.getInt ( "DOSETIMES" ) ;

                        System.out.println("The current dose time is: " + doseTimes);

                        String brand = rs.getString("VBRAND");
                        brand = '\'' + brand + '\'';
                        String queryGetDoseNum = " SELECT NUMDOSES " +
                                " FROM VACCBRAND " +
                                " WHERE VBRAND = " + brand + " ;";
                        java.sql.ResultSet rs2 = statement.executeQuery ( queryGetDoseNum ) ;
                        while(rs2.next()){
                            numDoseReq = rs2.getInt("NUMDOSES");
                            System.out.println("The numdose required for this brand is: " +  numDoseReq);
                            break;
                        }

                        if(doseTimes >= numDoseReq ){
                            // means we cannot assign the slot
                            System.out.println("The dose time required has reached, no need to assign the slot!\n" +
                                    "Request terminated");
                            assignSatisfied = false;
                            break;

                        }
                    }
                    if(assignSatisfied){
                        // we further check the date/availability of this slot
                        System.out.println("Now check date");
                        if(allocationInDate.after(slotdateInDate)){
                            System.out.println("The date to assign has passed! Error");
                            assignSatisfied = false;
                        }
                        else {
                            String queryCheckSlot = "SELECT healthid FROM SLOT WHERE " +
                                    " SLOTTIME = " + slottime +
                                    " AND SLOTDATE = " + slotdate +
                                    " AND tentnum = " + tentnum +
                                    " AND locname = " + locname + " ;";

                            //System.out.println (queryCheckSlot) ;
                            java.sql.ResultSet rsCheckSlot = statement.executeQuery ( queryCheckSlot ) ;
                            while(rsCheckSlot.next()){
                                int hidAssigned = rsCheckSlot.getInt("healthid");
                                System.out.println("This slot has been given to another person:" + hidAssigned + "\nRequest terminated!");
                                assignSatisfied = false;
                                break;
                            }

                        }


                        if(assignSatisfied){

                            // FINALLY, we can proceed to update/assign the slot data!

                            String insertSQL = "INSERT INTO slot(slottime,slotdate,tentnum, healthid,allocationdate,locname) " +
                                    "VALUES ( "  +slottime + "," + slotdate + ", "+ tentnum + ", "+ hid + ", "+ allocationdate
                                    + ", "+ locname + " );";

                            //System.out.println(insertSQL);
                            statement.executeUpdate(insertSQL);
                            System.out.println("DONE updating the slot data");
                        }
                    }
                    else {
                        //not satisfied, do nothing
                    }


                }catch (SQLException e)
                {
                    sqlCode = e.getErrorCode(); // Get SQLCODE
                    sqlState = e.getSQLState(); // Get SQLSTATE

                    // Your code to handle errors comes here;
                    // something more meaningful than a print would be good
                    System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
                    System.out.println(e);
                }

            }
            else if(userChoice == 3) {

                System.out.println( "Please Enter the person's health insurance number:");
                int hid =  scanner.nextInt();

                System.out.println( "Please Enter the vial number related to the shot:");
                int vialnum =  scanner.nextInt();

                System.out.println( "Please Enter the batch number:");
                int batchnum =  scanner.nextInt();

                System.out.println( "Please Enter the vaccine brand name:");
                String vbrand =  scanner.next();
                String brandNameNew = vbrand;
                vbrand = "\'" + vbrand + "\'";

                System.out.println( "Please Enter the nurse license number:");
                int nurselicensenum =  scanner.nextInt();

                System.out.println( "Please Enter the dose time:");
                int dosetimes =  scanner.nextInt();

                System.out.println( "Please Enter the shot time:");
                String shottime =  scanner.next();
                shottime = "\'" + shottime + "\'";

                System.out.println( "Please Enter the shot date:");
                String shotdate =  scanner.next();
                //Date slotdateInDate=Date.valueOf(slotdate);
                shotdate = "\'" + shotdate + "\'";


                try {
                    String queryCheckPrevBrand = "SELECT DISTINCT VBRAND " +
                            "FROM VACCSHOTS " +
                            "WHERE HEALTHID = " + hid + ";";


                    //System.out.println(queryCheckPrevBrand);
                    java.sql.ResultSet rs = statement.executeQuery(queryCheckPrevBrand);

                    boolean shotSatisfied = true;

                    while (rs.next()) {
                        String prevBrand = rs.getString("VBRAND");
                        if(!prevBrand.equalsIgnoreCase(brandNameNew)){
                            System.out.println("Error: Vaccine brand mismatch! The previous brand this person got is: " + prevBrand);
                            shotSatisfied = false;
                        }
                    }

                    if(shotSatisfied){
                        System.out.println("Proceed...");

                        String insertSQL = "INSERT INTO vaccshots(vialnum,batchnum,vbrand,nurselicensenum,dosetimes,shottime,healthid,shotdate)  " +
                                "VALUES ( "  +vialnum + "," + batchnum + ", "+ vbrand + ", "+ nurselicensenum + ", "+ dosetimes
                                + ", "+ shottime + ", "+ hid + ", "+ shotdate +  " );";

                        System.out.println(insertSQL);
                        statement.executeUpdate(insertSQL);
                        System.out.println("DONE updating vaccination data");




                    }

                }catch (SQLException e)
                {
                    sqlCode = e.getErrorCode(); // Get SQLCODE
                    sqlState = e.getSQLState(); // Get SQLSTATE

                    // Your code to handle errors comes here;
                    // something more meaningful than a print would be good
                    System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
                    System.out.println(e);
                }
















            }
            else if(userChoice == 4) {
                // Finally but importantly close the statement and connection
                System.out.println("Quit, bye!");
                statement.close ( ) ;
                con.close ( ) ;
                break;
            }
            else{
                System.out.println("Not available option, error");
                break;
            }
        }
        // Finally but importantly close the statement and connection
        statement.close ( ) ;
        con.close ( ) ;
    }
}
