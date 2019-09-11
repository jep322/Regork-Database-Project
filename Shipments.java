import java.sql.*;
import java.time.*;
import java.io.*;
import java.util.Scanner;

public class Shipments {

    String shipments;
    String view;
    String insert;
    String delete;
    String update;
    String shipID;
    String datePattern = "MM-dd-yyyy";
    Scanner scan = new Scanner(System.in);

    public Shipments() {
        this.shipments = "S";
        this.view = "V";
        this.insert = "I";
        this.delete = "D";
        this.update = "U";
        this.shipID = "";
    }
    //Getters
    public String getShipments() {
        return this.shipments;
    }

    public String getView() {
        return this.view;
    }

    public String getInsert() {
        return this.insert;
    }

    public String getDelete() {
        return this.delete;
    }

    public String getUpdate() {
        return this.update;
    }

    /**
     * 
     * @param con
     * 
     * Method to view all shipments going in and out
     * of regork AND shipments suppliers are shipping
     * to other suppliers other than regork
     */
    public void viewAllShipments(Connection con) {
        String sql = "SELECT TO_CHAR(sup_id, '000000') as sup_id, sup_name, TO_CHAR(shipments.ship_id, '000000') "
                + "as ship_id, outgoing_date, incoming_date, ship_quantity, price_per_unit, ship_cost FROM Suppliers, supplies_to, "
                + "shipments where suppliers.sup_id = supplies_to.s_id and supplies_to.ship_id = shipments.ship_id";
        PreparedStatement query;
        ResultSet res;

        try {
            query = con.prepareStatement(sql);
            res = query.executeQuery();

            if (!res.next()) {
                System.out.println("No Shipments Found");

            } else {
                System.out.printf("%-8s %-20s %-8s %-22s %-22s %-13s %-7s %-9s", " Sup_ID", "Sup_Name", " Ship_ID",
                        "Outgoing_Date", "Incoming_Date", "Ship_Count", "P/U", "Ship_Cost");
                System.out.println();
                System.out.println(
                        "----------------------------------------------------------------------------------------------------------------------");
                do {
                    System.out.printf("%-8s %-20s %-8s %-22s %-22s %-13d %-7.2f %-7.2f", res.getString("sup_id"),
                            res.getString("sup_name"), res.getString("ship_id"), res.getString("outgoing_date"),
                            res.getString("incoming_date"), res.getInt("ship_quantity"),
                            res.getDouble("price_per_unit"), res.getDouble("ship_cost"));
                    System.out.println();
                } while (res.next());
            }

        } catch (SQLException e) {
            System.out.println("Something went wrong during execution");
        }
    }
    /**
     * 
     * @param con
     * Method to view Outgoing Regork shipments
     */

    public void viewOutgoingShipments(Connection con) {
        String sql = "SELECT TO_CHAR(sup_id, '000000') as sup_id, sup_name, TO_CHAR(shipments.ship_id, '000000') "
                + "as ship_id, outgoing_date, incoming_date, ship_quantity, price_per_unit, ship_cost FROM Suppliers, supplies_to, "
                + "shipments where supplies_to.s_id = '44' and suppliers.sup_id = supplies_to.s_id and supplies_to.ship_id = shipments.ship_id";

        PreparedStatement query;
        ResultSet res;

        try {
            query = con.prepareStatement(sql);

            res = query.executeQuery();
            if (!res.next()) {
                System.out.println("No Shipments Found");

            } else {
                System.out.printf("%-8s %-20s %-8s %-22s %-22s %-13s %-7s %-9s", " Sup_ID", "Sup_Name", " Ship_ID",
                        "Outgoing_Date", "Incoming_Date", "Ship_Count", "P/U", "Ship_Cost");
                System.out.println();
                System.out.println(
                        "----------------------------------------------------------------------------------------------------------------------");
                do {
                    System.out.printf("%-8s %-20s %-8s %-22s %-22s %-13d %-7.2f %-7.2f", res.getString("sup_id"),
                            res.getString("sup_name"), res.getString("ship_id"), res.getString("outgoing_date"),
                            res.getString("incoming_date"), res.getInt("ship_quantity"),
                            res.getDouble("price_per_unit"), res.getDouble("ship_cost"));
                    System.out.println();
                } while (res.next());
            }
        } catch (SQLException e) {
            System.out.println("Error in preparing statement / executing query");

        }
    }
    /**
     * @param con
     * 
     * Method to view Incoming Regork Shipments
     */

    public void viewIncomingShipments(Connection con) {
        String sql = "SELECT TO_CHAR(sup_id, '000000') as sup_id, sup_name, TO_CHAR(shipments.ship_id, '000000') "
                + "as ship_id, outgoing_date, incoming_date, ship_quantity, price_per_unit, ship_cost FROM Suppliers, supplies_to, "
                + "shipments where supplies_to.receiving_id = '44' and suppliers.sup_id = supplies_to.s_id and supplies_to.ship_id = shipments.ship_id";
        PreparedStatement query;
        ResultSet res;

        try {
            query = con.prepareStatement(sql);

            res = query.executeQuery();

            if (!res.next()) {
                System.out.println("No Shipments Found");

            } else {
                System.out.printf("%-8s %-20s %-8s %-22s %-22s %-13s %-7s %-9s", " Sup_ID", "Sup_Name", " Ship_ID",
                        "Outgoing_Date", "Incoming_Date", "Ship_Count", "P/U", "Ship_Cost");
                System.out.println();
                System.out.println(
                        "----------------------------------------------------------------------------------------------------------------------");
                do {
                    System.out.printf("%-8s %-20s %-8s %-22s %-22s %-13d %-7.2f %-7.2f", res.getString("sup_id"),
                            res.getString("sup_name"), res.getString("ship_id"), res.getString("outgoing_date"),
                            res.getString("incoming_date"), res.getInt("ship_quantity"),
                            res.getDouble("price_per_unit"), res.getDouble("ship_cost"));
                    System.out.println();
                } while (res.next());
            }
        } catch (SQLException e) {
            System.out.println("Error in preparing statement / executing query");

        }
    }
    /**
     * 
     * @param con
     * 
     * Method to insert a New Shipment
     * A creation of a new shipment will prompt user 
     * to specify which supplier made this shipment
     */

    public void insertNewShipment(Connection con) {
        boolean check = true;
        Suppliers sup = new Suppliers();
        System.out.println("Creating a new Shipment");
        PreparedStatement query;
        LocalDate current = LocalDate.now();

        String currDate = current.getMonth().toString() + " " + Integer.toString(current.getDayOfMonth()) + ", "
                + Integer.toString(current.getYear());

        String sql = "insert into shipments(ship_id, outgoing_date, incoming_date, ship_quantity, price_per_unit) values (?, TO_DATE(?, 'MONTH DD, YYYY'), TO_DATE(?, 'MONTH DD, YYYY'), ?, ?)";

        shipID = idChecker();
        String incoming_date = incomingDateChecker();
        int ship_quantity = shipQuantity();
        double ship_unitPrice = shipPrice();

        try {
            query = con.prepareStatement(sql);
            query.setString(1, shipID);
            query.setString(2, currDate);
            query.setString(3, incoming_date);
            query.setInt(4, ship_quantity);
            query.setDouble(5, ship_unitPrice);

            System.out.println("executing...");
            query.executeUpdate();
            System.out.println("executed");

        } catch (SQLException e) {
            System.out.println("Error in insertion");
            quit(con);
        } catch (Throwable d) {
            System.out.println("Error.");
        }

        System.out.println("Which Supplier made this shipment?");
        sup.viewAllSuppliers(con);
        System.out.println("Enter Supplier\'s ID: ");
        String supID = "";
        while (check) {
            try {
                supID = scan.nextLine();
                try {
                    int idNumber = Integer.parseInt(supID);
                    if (supID.length() > 6) {
                        System.out.println("Invalid ID: Length must be less than 6. Try again. ");
                        check = true;
                    } else if (idNumber < 0 || idNumber > 99999) {
                        System.out.println("Invalid ID: Enter ID again: ");
                        check = true;
                    } else {
                        check = false;
                    }
                } catch (Exception e) {
                    System.out.println("");
                }
            } catch (Throwable e) {
                System.out.println("That wasn\'t nice of you...");
                System.exit(0);
            }
        }
        String receiver = "";
        if (supID.equals("44")) {
            check = true;
            System.out.println("Which supplier did you send this shipment to?");
            while (check) {

                try {
                    receiver = scan.nextLine();
                    try {
                        int idNumber = Integer.parseInt(receiver);
                        if (receiver.length() > 6) {
                            System.out.println("Invalid ID: Length must be less than 6. Try again. ");
                            check = true;
                        } else if (idNumber < 0 || idNumber > 99999) {
                            System.out.println("Invalid ID: Enter ID again: ");
                            check = true;
                        } else {
                            check = false;
                        }
                    } catch (Exception e) {
                        System.out.println("Please Enter ID");
                    }
                } catch (Throwable e) {
                    System.out.println("That wasn\'t nice of you...");
                    System.exit(0);
                }

            }
            regorkSuppliesTo(con, supID, receiver);
        } else {
            String newSup = "";
            String answer = "";
            System.out.println("Did this supplier send the shipment to another supplier or to Regork?");
            System.out.println("{S}: Another Supplier");
            System.out.println("{R}: To Regork");

            check = true;
            boolean outer = true;
            while (outer) {
                try {
                    answer = scan.nextLine();
                } catch (Throwable e) {
                    System.out.println("That wasn\'t nice of you...");
                    System.exit(0);
                }

                if (answer.equals("S")) {
                    outer = false;
                    System.out.println("Enter the Supplier\'s ID");
                    while (check) {
                        try {
                            newSup = scan.nextLine();
                            try {
                                int idNumber = Integer.parseInt(newSup);
                                if (newSup.length() > 6) {
                                    System.out.println("Invalid ID: Length must be less than 6. Try again. ");
                                    check = true;
                                } else if (idNumber < 0 || idNumber > 99999) {
                                    System.out.println("Invalid ID: Enter ID again: ");
                                    check = true;
                                } else {
                                    check = false;
                                }
                            } catch (Exception e) {
                                System.out.println("Error, enter an integer");
                            }
                        } catch (Throwable e) {
                            System.out.println("That wasn\'t nice of you...");
                            System.exit(0);
                        }

                    }

                    supplierSuppliesTo(con, supID, newSup);

                } else if (answer.equals("R")) {
                    outer = false;
                    supplierSuppliesTo(con, supID, "44");

                } else {
                    System.out.println("Invalid command try again.");
                }

            }

        }

    }
    /**
     * 
     * @param con
     * 
     * Method to delete Shipments
     * Meant to delete past shipments
     */

    public void deleteShipment(Connection con) {
        String sql = "delete from shipments where ship_id = ?";
        PreparedStatement query;

        String deleteID = idChecker();

        try {
            query = con.prepareStatement(sql);
            query.setString(1, deleteID);
            System.out.println("Executing ... ");
            query.executeQuery();
            System.out.println("Executed!");
        } catch (SQLException e) {
            System.out.println("Error in deletion");

        } catch (Throwable e) {
            System.out.println("Error.");
        }

    }
    /**
     * 
     * @param con
     * @param sup
     * @param rec
     * 
     * Method to connect Regork Shimpments to 
     * other Suppliers
     *
     */

    public void regorkSuppliesTo(Connection con, String sup, String rec) {
        String sql = "insert into supplies_to (s_id, ship_id, receiving_id) values (?, ?, ?)";

        PreparedStatement query;

        try {
            query = con.prepareStatement(sql);
            query.setString(1, sup);
            query.setString(2, shipID);
            query.setString(3, rec);
            System.out.println("Executing...");
            query.executeUpdate();
            System.out.println("Executed!");
        } catch (SQLException e) {
            System.out.println("That supplier might not exist ...");

        }

    }

    /**
     * 
     * @param con
     * @param sup
     * @param rec
     * 
     * Method that connects a Supplier's shipments
     * to another supplier
     */

    public void supplierSuppliesTo(Connection con, String sup, String rec) {
        String sql = "insert into supplies_to (s_id, ship_id, receiving_id) values (?, ?, ?)";

        PreparedStatement query;

        try {
            query = con.prepareStatement(sql);
            query.setString(1, sup);
            query.setString(2, shipID);
            query.setString(3, rec);
            System.out.println("Executing...");
            query.executeUpdate();
            System.out.println("Executed!");

        } catch (SQLException e) {
            System.out.println("That supplier might not exist... ");
        }

    }

    /**
     * @return date
     * Method to check date format
     */

    public String incomingDateChecker() {
        LocalDate curr = LocalDate.now();

        int year = 0;
        String month = "";
        int day = 0;
        String date = "";
        boolean dayCheck = true;
        boolean checkMonth = true;
        boolean check = true;
        System.out.println("Enter Estimated Arrival year");
        try {
            while (check) {
                while (!scan.hasNextInt()) {
                    String trash = scan.next();
                    System.out.println("Error. Enter an Integer. ");
                }
                year = scan.nextInt();
                if (year < 2019) {
                    System.out.println("Shipments can\'t time travel to the past ya know ... ");
                } else if (year > 9999) {
                    System.out.println("Oracle doesn\'t allow this so I\'m not letting you do that ");
                } else {
                    check = false;
                }
            }
        } catch (Throwable e) {
            System.out.println("thats not allowed");
            System.exit(0);
        }

        System.out.println("Enter outgoing date month (input as first 3 letters): ");
        while (checkMonth) {
            try {
                month = scan.nextLine();
            } catch (Throwable e) {
                System.out.println("That wasn\'t nice of you...");
                System.exit(0);
            }
            month = month.toUpperCase();
            try {
                if (year == 2019) {
                    if (month.equals("JAN") || month.equals("FEB") || month.equals("MAR") || month.equals("APR")) {
                        System.out.println("Shipments can\'t travel back in time ...");
                    } else {
                        if (month.equals("MAY")) {
                            checkMonth = false;
                            dayCheck = true;
                            System.out.println("Enter Estimated Arrival Day");
                            while (dayCheck) {
                                while (!scan.hasNextInt()) {
                                    String trash = scan.next();
                                    System.out.println("Error. Enter an Integer. ");
                                }
                                day = scan.nextInt();
                                if (day <= 0 || day > 31 || day < curr.getDayOfMonth()) {
                                    System.out.println("Invalid day try again");
                                } else {
                                    dayCheck = false;
                                }
                            }

                        } else if (month.equals("JUN")) {
                            checkMonth = false;
                            dayCheck = true;
                            System.out.println("Enter Estimated Arrival Day");
                            while (dayCheck) {
                                while (!scan.hasNextInt()) {
                                    String trash = scan.next();
                                    System.out.println("Error. Enter an Integer. ");
                                }
                                day = scan.nextInt();
                                if (day <= 0 || day > 30) {
                                    System.out.println("Invalid day try again");
                                } else {
                                    dayCheck = false;
                                }
                            }

                        } else if (month.equals("JUL")) {
                            checkMonth = false;
                            dayCheck = true;
                            System.out.println("Enter Estimated Arrival Day");
                            while (dayCheck) {
                                while (!scan.hasNextInt()) {
                                    String trash = scan.next();
                                    System.out.println("Error. Enter an Integer. ");
                                }
                                day = scan.nextInt();
                                if (day <= 0 || day > 31) {
                                    System.out.println("Invalid day try again");
                                } else {
                                    dayCheck = false;
                                }
                            }

                        } else if (month.equals("AUG")) {
                            checkMonth = false;
                            dayCheck = true;
                            System.out.println("Enter Estimated Arrival Day");
                            while (dayCheck) {
                                while (!scan.hasNextInt()) {
                                    String trash = scan.next();
                                    System.out.println("Error. Enter an Integer. ");
                                }
                                day = scan.nextInt();
                                if (day <= 0 || day > 31) {
                                    System.out.println("Invalid day try again");
                                } else {
                                    dayCheck = false;
                                }
                            }

                        } else if (month.equals("SEP")) {
                            checkMonth = false;
                            dayCheck = true;
                            System.out.println("Enter Estimated Arrival Day");
                            while (dayCheck) {
                                while (!scan.hasNextInt()) {
                                    String trash = scan.next();
                                    System.out.println("Error. Enter an Integer. ");
                                }
                                day = scan.nextInt();
                                if (day <= 0 || day > 30) {
                                    System.out.println("Invalid day try again");
                                } else {
                                    dayCheck = false;
                                }
                            }

                        } else if (month.equals("OCT")) {
                            checkMonth = false;
                            dayCheck = true;
                            System.out.println("Enter Estimated Arrival Day");
                            while (dayCheck) {
                                while (!scan.hasNextInt()) {
                                    String trash = scan.next();
                                    System.out.println("Error. Enter an Integer. ");
                                }
                                day = scan.nextInt();
                                if (day <= 0 || day > 31) {
                                    System.out.println("Invalid day try again");
                                } else {
                                    dayCheck = false;
                                }
                            }

                        } else if (month.equals("NOV")) {
                            checkMonth = false;
                            dayCheck = true;
                            System.out.println("Enter Estimated Arrival Day");
                            while (dayCheck) {
                                while (!scan.hasNextInt()) {
                                    String trash = scan.next();
                                    System.out.println("Error. Enter an Integer. ");
                                }
                                day = scan.nextInt();
                                if (day <= 0 || day > 30) {
                                    System.out.println("Invalid day try again");
                                } else {
                                    dayCheck = false;
                                }
                            }

                        } else if (month.equals("DEC")) {
                            checkMonth = false;
                            dayCheck = true;
                            System.out.println("Enter Estimated Arrival Day");
                            while (dayCheck) {
                                while (!scan.hasNextInt()) {
                                    String trash = scan.next();
                                    System.out.println("Error. Enter an Integer. ");
                                }
                                day = scan.nextInt();
                                if (day <= 0 || day > 31) {
                                    System.out.println("Invalid day try again");
                                } else {
                                    dayCheck = false;
                                }
                            }

                        } else {
                            System.out.print("");
                        }

                    }
                } else {
                    if (month.equals("JAN")) {
                        checkMonth = false;
                        dayCheck = true;
                        System.out.println("Enter Estimated Arrival Day");
                        while (dayCheck) {
                            while (!scan.hasNextInt()) {
                                String trash = scan.next();
                                System.out.println("Error. Enter an Integer. ");
                            }
                            day = scan.nextInt();
                            if (day <= 0 || day > 31) {
                                System.out.println("Invalid day try again");
                            } else {
                                dayCheck = false;
                            }
                        }

                    } else if (month.equals("FEB")) {
                        checkMonth = false;
                        dayCheck = true;
                        System.out.println("Enter Estimated Arrival Day");
                        while (dayCheck) {
                            while (!scan.hasNextInt()) {
                                String trash = scan.next();
                                System.out.println("Error. Enter an Integer. ");
                            }
                            day = scan.nextInt();
                            if (year % 4 == 0) {
                                if (day <= 0 || day > 29) {
                                    System.out.println("Invalid day try again");
                                }
                                dayCheck = false;
                            } else {
                                if (day <= 0 || day > 28) {
                                    System.out.println("Invalid day try again");
                                } else {
                                    dayCheck = false;
                                }
                            }

                        }

                    } else if (month.equals("MAR")) {
                        checkMonth = false;
                        dayCheck = true;
                        System.out.println("Enter Estimated Arrival Day");
                        while (dayCheck) {
                            while (!scan.hasNextInt()) {
                                String trash = scan.next();
                                System.out.println("Error. Enter an Integer. ");
                            }
                            day = scan.nextInt();
                            if (day <= 0 || day > 31) {
                                System.out.println("Invalid day try again");
                            } else {
                                dayCheck = false;
                            }
                        }

                    } else if (month.equals("APR")) {
                        checkMonth = false;
                        dayCheck = true;
                        System.out.println("Enter Estimated Arrival Day");
                        while (dayCheck) {
                            while (!scan.hasNextInt()) {
                                String trash = scan.next();
                                System.out.println("Error. Enter an Integer. ");
                            }
                            day = scan.nextInt();
                            if (day <= 0 || day > 30) {
                                System.out.println("Invalid day try again");
                            } else {
                                dayCheck = false;
                            }
                        }

                    } else if (month.equals("MAY")) {
                        checkMonth = false;
                        dayCheck = true;
                        System.out.println("Enter Estimated Arrival Day");
                        while (dayCheck) {
                            while (!scan.hasNextInt()) {
                                String trash = scan.next();
                                System.out.println("Error. Enter an Integer. ");
                            }
                            day = scan.nextInt();
                            if (day <= 0 || day > 31) {
                                System.out.println("Invalid day try again");
                            } else {
                                dayCheck = false;
                            }
                        }

                    } else if (month.equals("JUN")) {
                        checkMonth = false;
                        dayCheck = true;
                        System.out.println("Enter Estimated Arrival Day");
                        while (dayCheck) {
                            while (!scan.hasNextInt()) {
                                String trash = scan.next();
                                System.out.println("Error. Enter an Integer. ");
                            }
                            day = scan.nextInt();
                            if (day <= 0 || day > 30) {
                                System.out.println("Invalid day try again");
                            } else {
                                dayCheck = false;
                            }
                        }

                    } else if (month.equals("JUL")) {
                        checkMonth = false;
                        dayCheck = true;
                        System.out.println("Enter Estimated Arrival Day");
                        while (dayCheck) {
                            while (!scan.hasNextInt()) {
                                String trash = scan.next();
                                System.out.println("Error. Enter an Integer. ");
                            }
                            day = scan.nextInt();
                            if (day <= 0 || day > 31) {
                                System.out.println("Invalid day try again");
                            } else {
                                dayCheck = false;
                            }
                        }

                    } else if (month.equals("AUG")) {
                        checkMonth = false;
                        dayCheck = true;
                        System.out.println("Enter Estimated Arrival Day");
                        while (dayCheck) {
                            while (!scan.hasNextInt()) {
                                String trash = scan.next();
                                System.out.println("Error. Enter an Integer. ");
                            }
                            day = scan.nextInt();
                            if (day <= 0 || day > 31) {
                                System.out.println("Invalid day try again");
                            } else {
                                dayCheck = false;
                            }
                        }

                    } else if (month.equals("SEP")) {
                        checkMonth = false;
                        dayCheck = true;
                        System.out.println("Enter Estimated Arrival Day");
                        while (dayCheck) {
                            while (!scan.hasNextInt()) {
                                String trash = scan.next();
                                System.out.println("Error. Enter an Integer. ");
                            }
                            day = scan.nextInt();
                            if (day <= 0 || day > 30) {
                                System.out.println("Invalid day try again");
                            } else {
                                dayCheck = false;
                            }
                        }

                    } else if (month.equals("OCT")) {
                        checkMonth = false;
                        dayCheck = true;
                        System.out.println("Enter Estimated Arrival Day");
                        while (dayCheck) {
                            while (!scan.hasNextInt()) {
                                String trash = scan.next();
                                System.out.println("Error. Enter an Integer. ");
                            }
                            day = scan.nextInt();
                            if (day <= 0 || day > 31) {
                                System.out.println("Invalid day try again");
                            } else {
                                dayCheck = false;
                            }
                        }

                    } else if (month.equals("NOV")) {
                        checkMonth = false;
                        dayCheck = true;
                        System.out.println("Enter Estimated Arrival Day");
                        while (dayCheck) {
                            while (!scan.hasNextInt()) {
                                String trash = scan.next();
                                System.out.println("Error. Enter an Integer. ");
                            }
                            day = scan.nextInt();
                            if (day <= 0 || day > 30) {
                                System.out.println("Invalid day try again");
                            } else {
                                dayCheck = false;
                            }
                        }

                    } else if (month.equals("DEC")) {
                        checkMonth = false;
                        dayCheck = true;
                        System.out.println("Enter Estimated Arrival Day");
                        while (dayCheck) {
                            while (!scan.hasNextInt()) {
                                String trash = scan.next();
                                System.out.println("Error. Enter an Integer. ");
                            }
                            day = scan.nextInt();
                            if (day <= 0 || day > 31) {
                                System.out.println("Invalid day try again");
                            } else {
                                dayCheck = false;
                            }
                        }

                    } else {
                        System.out.print("");
                    }

                }
            } catch (Throwable e) {
                System.out.println("Please stop doing control-d");
                System.exit(0);
            }

        }
        date += month + " " + day + ", " + year;
        return date;
    }

    /**
     * 
     * @return the quantity
     * 
     * Method to prompt user to 
     * enter the quantity of shipments
     * a supplier is making
     */

    public int shipQuantity() {
        int quant = 0;
        boolean check = true;
        System.out.println("Enter Shipment Quantity: ");
        try {
            while (check) {
                while (!scan.hasNextInt()) {
                    String trash = scan.next();
                    System.out.println("Invalid. Please Enter Number correctly (\'-------\'): ");
                }
                quant = scan.nextInt();
                if (quant < 0 || quant > 9999999) {
                    System.out.println("Invalid Number. Try again.");
                } else {
                    check = false;
                }
            }
        } catch (Throwable e) {
            System.out.println("Stop Entering Weird Stuff");
            System.exit(0);
        }
        return quant;
    }
    /**
     * 
     * @return price per unit
     * 
     * Method to prompt user to enter shipment's price
     * per unit
     */

    public double shipPrice() {
        double cost = 0;
        boolean check = true;
        System.out.println("Enter Shipment Price Per Unit: ");
        try {
            while (check) {
                while (!scan.hasNextDouble()) {
                    String trash = scan.next();
                    System.out.println("Invalid. Please Enter Number correctly (\'-------\'): ");
                }
                cost = scan.nextDouble();
                if (cost < 0.00 || cost > 99999.99) {
                    System.out.println("Invalid Number. Try again.");
                } else {
                    check = false;
                }
            }
        } catch (Throwable e) {
            System.exit(0);
        }
        return cost;
    }
    /**
     * 
     * @return ship_id
     * Method to return the shipment ID
     */

    public String idChecker() {
        System.out.println("Enter Shipment ID");
        boolean check = true;
        String answer = "";
        int idNumber = 0;
        while (check) {
            try {
                answer = scan.nextLine();
            } catch (Throwable e) {
                System.out.println("You weren\'t supposed to do that");
                System.exit(0);
            }

            try {
                idNumber = Integer.parseInt(answer);
                if (answer.length() > 6) {
                    System.out.println("Invalid ID: Length must be less than 6. Try again. ");
                    check = true;
                } else if (idNumber < 0 || idNumber > 99999) {
                    System.out.println("Invalid ID: Enter ID again: ");
                    check = true;
                } else {
                    check = false;
                }
            } catch (Exception e) {

                System.out.print("Please enter ID");
            }
        }
        return answer;

    }
    public void quit(Connection con) {
        System.out.println("Exiting...");
        try {
            con.close();
            System.exit(0);
        } catch (SQLException e) {
            System.out.println("Error during closing connection");
            System.exit(0);
        }
    }

}