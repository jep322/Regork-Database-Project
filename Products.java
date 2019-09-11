import java.sql.*;
import java.io.*;
import java.util.Scanner;

public class Products {
    String brandedProduct;
    String genericProduct;
    String recall;
    String supplies;
    String view;
    String update;
    String delete;
    String bProdID;
    String prodID;

    Scanner scan = new Scanner(System.in);

    public Products() {

        this.brandedProduct = "B";
        this.genericProduct = "G";
        this.supplies = "S";
        this.recall = "R";
        this.view = "V";
        this.update = "U";
        this.delete = "D";
        this.bProdID = "";
        this.prodID = "";
    }

    public String getBrandedProduct() {
        return brandedProduct;
    }

    public String getGenericProduct() {
        return genericProduct;
    }

    public String getSupplies() {
        return supplies;
    }

    public String getRecall() {
        return recall;
    }

    public String getView() {
        return view;
    }

    public String getUpdate() {
        return update;
    }

    public String getDelete() {
        return delete;
    }

    public void viewBrandedProduct(Connection con) {
        String sql = "select TO_CHAR(bprod_id, '000000') as Branded_Product_ID, bprod_name, bprod_msrp as MSRP, bprod_selling as Selling_Price from Branded_Products order by Branded_Product_ID";

        ResultSet res;
        try {
            PreparedStatement query = con.prepareStatement(sql);
            res = query.executeQuery();
            if (!res.next()) {
                System.out.print("No matches");
            } else {
                System.out.println("Here is the list of Branded Products: ");
                System.out.printf("%-8s %-50s %-10s %-8s", " Prod ID", "Prod Name", "MSRP", "Sell Price");
                System.out.println();
                System.out
                        .println("-----------------------------------------------------------------------------------");

                do {
                    System.out.printf("%-8s %-50s %-10.2f %-7.2f", res.getString("Branded_Product_ID"),
                            res.getString("bprod_name"), res.getDouble("MSRP"), res.getDouble("Selling_Price"));
                    System.out.println();
                } while (res.next());
            }
            query.close();
        } catch (SQLException e) {
            System.out.println("Error in viewing products");
        }
    }

    public void insertBrandedProduct(Connection con) {
        String sql = "insert into Branded_Products(bprod_id, bprod_name, bprod_msrp, bprod_selling) values (?, ?, ?, ?)";
        ResultSet res;
        boolean check = true;

        int idNumber = 0;
        try {
            while (check) {
                try {
                    PreparedStatement query = con.prepareStatement(sql);

                    System.out.println("Enter Branded Product ID: ");

                    while (check) {
                        try {
                            bProdID = scan.nextLine();
                        } catch (Throwable e) {
                            System.out.println("That wasn\'t nice of you...");
                            System.exit(0);
                        }
                        try {
                            idNumber = Integer.parseInt(bProdID);
                            if (bProdID.length() > 6) {
                                System.out.println("Invalid ID: Length must be less than 6. Try again. ");
                                check = true;
                            } else if (idNumber < 0 || idNumber > 99999) {
                                System.out.println("Invalid ID: Enter ID again: ");
                                check = true;
                            } else {
                                System.out.println("Setting param 1");
                                query.setString(1, bProdID);
                                check = false;
                            }
                        } catch (Exception e) {
                            System.out.println("Please Enter Id");
                        }
                    }
                    check = true;
                    System.out.println("Enter Branded Product Name: ");
                    String bProdName = "";
                    while (check) {
                        try {
                            bProdName = scan.nextLine();
                        } catch (Throwable e) {
                            System.out.println("That wasn\'t nice of you...");
                            System.exit(0);
                        }
                        if (bProdName.length() > 50) {
                            System.out.println("Invalid Name: Length must be less than 50. Try again. ");
                        } else if (bProdName.contains("\'")) {
                            System.out.println("Invalid Name: NOT ALLOWED TRY AGAIN.");
                        } else {
                            System.out.println("Setting param 2");
                            query.setString(2, bProdName);
                            check = false;
                        }
                    }
                    check = true;
                    double bProdMSRP;
                    double bProdSelling;
                    System.out.println("Enter Branded Product\'s MSRP: ");
                    while (!scan.hasNextDouble()) {
                        String trash = scan.next();

                        System.out.println("Invalid. Please Enter Number correctly (\'-----.--\'): ");
                    }
                    while (check) {
                        bProdMSRP = scan.nextDouble();
                        if (bProdMSRP < 0.00 || bProdMSRP > 99999.99) {
                            System.out.println("Invalid Number. Try again.");
                        } else {
                            System.out.println("Setting param 3");
                            query.setDouble(3, bProdMSRP);
                            check = false;
                        }
                    }
                    check = true;
                    System.out.println("Enter Branded Product\'s Selling Price: ");
                    while (!scan.hasNextDouble()) {

                        String trash = scan.next();

                        System.out.println("Invalid. Please Enter Number correctly (\'-----.--\'): ");
                    }

                    while (check) {
                        bProdSelling = scan.nextDouble();
                        if (bProdSelling < 0.00 || bProdSelling > 99999.99) {
                            System.out.println("Invalid Number. Try again.");
                        } else {
                            System.out.println("Setting param 4");
                            query.setDouble(4, bProdSelling);
                            check = false;
                            System.out.println("Successful");
                        }
                    }
                    System.out.println("Inserting Product...");
                    try {
                        query.executeUpdate();

                    } catch (SQLException e) {
                        System.out.println("Error. That ID Already Exists!");
                        quit(con);
                    }

                    System.out.println("Succesfully Inserted!");
                } catch (SQLException e) {
                    System.out.println("Error during insertion");
                }

            }
        } catch (Throwable e) {
            System.out.println("Error. Exiting....");
            quit(con);
        }

    }

    public void viewGenericProduct(Connection con) {
        String sql = "select TO_CHAR(prod_id, '000000') as Generic_Product_ID, prod_name, prod_msrp as MSRP, prod_selling as Selling_Price from Products order by Generic_Product_ID";

        PreparedStatement query = null;
        ResultSet res;
        try {
            query = con.prepareStatement(sql);
            res = query.executeQuery();
            if (!res.next()) {
                System.out.print("No matches");
            } else {
                System.out.println("Here is the list of Generic Products: ");
                System.out.printf("%-8s %-50s %-10s %-8s", " Prod ID", "Prod Name", "MSRP", "Sell Price");
                System.out.println();
                System.out
                        .println("-----------------------------------------------------------------------------------");
                do {
                    System.out.printf("%-8s %-50s %-10.2f %-7.2f", res.getString("Generic_Product_ID"),
                            res.getString("prod_name"), res.getDouble("MSRP"), res.getDouble("Selling_Price"));
                    System.out.println();
                } while (res.next());
                query.close();
            }
        } catch (SQLException e) {
            System.out.println("Error in selection. Please reconnect.");
            System.exit(0);
        }
    }

    public void insertGenericProduct(Connection con) {
        String sql = "insert into Products(prod_id, prod_name, prod_msrp, prod_selling) values (?, ?, ?, ?)";
        ResultSet res;
        boolean check = true;

        int idNumber = 0;
        try {
            while (check) {
                try {
                    PreparedStatement query = con.prepareStatement(sql);

                    System.out.println("Enter Generic Product ID: ");

                    while (check) {
                        try {
                            prodID = scan.nextLine();
                        } catch (Throwable e) {
                            System.out.println("That wasn\'t nice of you...");
                            System.exit(0);
                        }
                        try {
                            idNumber = Integer.parseInt(prodID);
                            if (prodID.length() > 6) {
                                System.out.println("Invalid ID: Length must be less than 6. Try again. ");
                                check = true;
                            } else if (idNumber < 0 || idNumber > 99999) {
                                System.out.println("Invalid ID: Enter ID again: ");
                                check = true;
                            } else {
                                System.out.println("Setting param 1");
                                query.setString(1, prodID);
                                check = false;
                            }
                        } catch (Exception e) {
                            System.out.println("Please Enter ID");
                        }
                    }

                    check = true;
                    System.out.println("Enter Generic Product Name: ");
                    String prodName = "";
                    while (check) {
                        try {
                            prodName = scan.nextLine();
                        } catch (Throwable e) {
                            System.out.println("That wasn\'t nice of you...");
                            System.exit(0);
                        }
                        if (prodName.length() > 50) {
                            System.out.println("Invalid Name: Length must be less than 50. Try again. ");
                        } else if (prodName.contains("\''")) {
                            System.out.println("Invalid Name: NOT ALLOWED TRY AGAIN.");
                        } else {
                            System.out.println("Setting param 2");
                            query.setString(2, prodName);
                            check = false;
                        }
                    }
                    check = true;
                    double prodMSRP;
                    double prodSelling;
                    System.out.println("Enter Generic Product\'s MSRP: ");
                    while (!scan.hasNextDouble()) {

                        String trash = scan.next();
                        System.out.println("Invalid. Please Enter Number correctly (\'-----.--\'): ");
                    }

                    while (check) {
                        prodMSRP = scan.nextDouble();
                        if (prodMSRP < 0.00 || prodMSRP > 99999.99) {
                            System.out.println("Invalid Number. Try again.");
                        } else {
                            System.out.println("Setting param 3");
                            query.setDouble(3, prodMSRP);
                            check = false;
                        }
                    }
                    check = true;
                    System.out.println("Enter Generic Product\'s Selling Price: ");
                    while (!scan.hasNextDouble()) {

                        String trash = scan.next();

                        System.out.println("Invalid. Please Enter Number correctly (\'-----.--\'): ");
                    }

                    while (check) {
                        prodSelling = scan.nextDouble();
                        if (prodSelling < 0.00 || prodSelling > 99999.99) {
                            System.out.println("Invalid Number. Try again.");
                        } else {
                            System.out.println("Setting param 4");
                            query.setDouble(4, prodSelling);
                            check = false;
                            System.out.println("Successful");
                        }
                    }
                    System.out.println("Inserting Product...");
                    try {
                        query.executeUpdate();

                    } catch (SQLException e) {
                        System.out.println("Error. That ID Already Exists!");
                        quit(con);
                    }

                    System.out.println("Succesfully Inserted!");
                } catch (SQLException e) {

                    System.out.println("Error during insertion");

                }

            }
        } catch (Throwable e) {
            System.out.println("Error. Exiting....");
            quit(con);
        }

    }

    public void updateBrandedProduct(Connection con) {

        System.out.println("Which product do you want to update?");
        PreparedStatement query;
        String id = "";
        int idNumber = 0;
        boolean check2 = true;

        System.out.print("Enter ID: ");
        boolean check3 = true;
        while (check3) {
            try {
                id = scan.nextLine();
            } catch (Throwable e) {
                System.out.println("That wasn\'t nice of you...");
                System.exit(0);
            }

            try {
                idNumber = Integer.parseInt(id);
                if (id.length() > 6 || id.length() <= 0) {
                    System.out.println("Error: ID Number must be within 1 - 6 digits. Try Again.");
                    check3 = true;
                } else if (idNumber < 0 || idNumber > 99999) {
                    System.out.println("Error: ID Number must be within 1 - 6 digits. Try Again.");
                    check3 = true;
                } else {
                    System.out.println("Setting ID...");
                    check3 = false;
                }
            } catch (Exception e) {
                System.out.println("");
            }
        }
        System.out.println("What would you like to update?");
        System.out.println("{M}: Product\'s MSRP");
        System.out.println("{S}: Product\'s Selling Price");
        System.out.println("{B}: Both Product\'s MSRP and Selling Price");
        System.out.println("{N}: Product\'s Name");
        System.out.println("{A}: Product\'s Name, MSRP, and Selling Price");

        String answer = "";

        boolean check4 = true;
        try {

            while (check4) {
                try {
                    answer = scan.nextLine();
                } catch (Throwable e) {
                    System.out.println("That wasn\'t nice of you...");
                    System.exit(0);
                }
                if (answer.equals("M")) {
                    check4 = false;
                    String sql = "update branded_products set bprod_msrp = ? where bprod_id = " + id;
                    try {
                        query = con.prepareStatement(sql);
                        double bProdMSRP;
                        System.out.println("Enter Branded Product\'s MSRP: ");
                        while (!scan.hasNextDouble()) {

                            String trash = scan.next();

                            System.out.println("Invalid. Please Enter Number correctly (\'-----.--\'): ");
                        }
                        boolean check = true;
                        while (check) {
                            bProdMSRP = scan.nextDouble();
                            if (bProdMSRP < 0.00 || bProdMSRP > 99999.99) {
                                System.out.println("Invalid Number. Try again.");
                            } else {
                                System.out.println("Setting param 1");
                                query.setDouble(1, bProdMSRP);
                                check = false;
                            }
                        }

                        System.out.println("Executing...");
                        query.executeUpdate();
                        System.out.println("Successful");

                    } catch (SQLException e) {
                        System.out.println("Error during execution...");
                    }
                } else if (answer.equals("S")) {
                    String sql = "update branded_products set bprod_selling = ? where bprod_id = " + id;
                    try {
                        query = con.prepareStatement(sql);
                        double bProdSelling;
                        System.out.println("Enter Branded Product\'s Selling Price: ");
                        while (!scan.hasNextDouble()) {
                            String trash = scan.next();
                            System.out.println("Invalid. Please Enter Number correctly (\'-----.--\'): ");
                        }
                        boolean check = true;
                        while (check) {
                            bProdSelling = scan.nextDouble();
                            if (bProdSelling < 0.00 || bProdSelling > 99999.99) {
                                System.out.println("Invalid Number. Try again.");
                            } else {
                                System.out.println("Setting param 1");
                                query.setDouble(1, bProdSelling);
                                check = false;
                            }
                        }

                        System.out.println("Executing...");
                        query.executeUpdate();
                        System.out.println("Successful");
                        check4 = false;

                    } catch (SQLException e) {
                        System.out.println("Error during execution...");
                    }

                } else if (answer.equals("B")) {
                    check4 = false;
                    String sql = "update branded_products set bprod_msrp = ?, bprod_selling = ? where bprod_id = " + id;
                    try {
                        query = con.prepareStatement(sql);
                        double bProdMSRP;
                        double bProdSelling;
                        System.out.println("Enter Branded Product\'s MSRP: ");
                        while (!scan.hasNextDouble()) {

                            String trash = scan.next();

                            System.out.println("Invalid. Please Enter Number correctly (\'-----.--\'): ");
                        }
                        boolean check = true;
                        while (check) {
                            bProdMSRP = scan.nextDouble();
                            if (bProdMSRP < 0.00 || bProdMSRP > 99999.99) {
                                System.out.println("Invalid Number. Try again.");
                            } else {
                                System.out.println("Setting param 1");
                                query.setDouble(1, bProdMSRP);
                                check = false;
                            }
                        }
                        check = true;
                        System.out.println("Enter Branded Product\'s Selling Price: ");
                        while (!scan.hasNextDouble()) {

                            String trash = scan.next();

                            System.out.println("Invalid. Please Enter Number correctly (\'-----.--\'): ");
                        }

                        while (check) {
                            bProdSelling = scan.nextDouble();
                            if (bProdSelling < 0.00 || bProdSelling > 99999.99) {
                                System.out.println("Invalid Number. Try again.");
                            } else {
                                System.out.println("Setting param 2");
                                query.setDouble(2, bProdSelling);
                                check = false;
                            }
                        }
                        System.out.println("Executing update");
                        query.executeUpdate();
                        System.out.println("Successful");
                        check4 = false;
                    } catch (SQLException e) {
                        System.out.println("Error during execution...");
                    } catch (Throwable e) {
                        System.out.println("Error. Exiting...");
                        quit(con);
                    }
                } else if (answer.equals("N")) {
                    check4 = false;
                    String sql = "update branded_products set bprod_name = ? where bprod_id = " + id;
                    try {
                        query = con.prepareStatement(sql);
                        String bProdName = "";
                        boolean check5 = true;
                        System.out.println("Enter name: ");
                        while (check5) {
                            try {
                                bProdName = scan.nextLine();
                                if (bProdName.length() > 50 || bProdName.length() <= 0) {
                                    System.out.println("Must Have a name or must be less than 50 characters");
                                }
                                if (bProdName.contains("\'")) {
                                    System.out.println("That\'s not allowed. Try again");
                                } else {
                                    check5 = false;
                                }
                            } catch (Throwable e) {
                                System.out.println("That was not nice at all >:(");
                                quit(con);
                            }

                        }
                        System.out.println("Setting New Name ...");
                        query.setString(1, bProdName);
                        query.executeQuery();
                        System.out.println("Set!");

                    } catch (SQLException e) {
                        System.out.println("Error");
                        System.exit(0);
                    }

                } else if (answer.equals("A")) {
                    check4 = false;
                    String sql = "update branded_products set bprod_name = ?, bprod_msrp = ?, bprod_selling = ? where bprod_id = "
                            + id;
                    try {
                        query = con.prepareStatement(sql);
                        String bProdName = "";
                        boolean check5 = true;
                        System.out.println("Enter name: ");
                        while (check5) {
                            try {
                                bProdName = scan.nextLine();
                                if (bProdName.length() > 50 || bProdName.length() <= 0) {
                                    System.out.println("Must Have a name or must be less than 50 characters");
                                }
                                if (bProdName.contains("\'")) {
                                    System.out.println("That\'s not allowed. Try again");
                                } else {
                                    check5 = false;
                                }
                            } catch (Throwable e) {
                                System.out.println("That was not nice at all >:(");
                                quit(con);
                            }

                        }
                        System.out.println("Setting New Name ...");
                        query.setString(1, bProdName);
                        System.out.println("Set!");
                        double bProdMSRP;
                        double bProdSelling;
                        System.out.println("Enter Branded Product\'s MSRP: ");
                        while (!scan.hasNextDouble()) {

                            String trash = scan.next();

                            System.out.println("Invalid. Please Enter Number correctly (\'-----.--\'): ");
                        }
                        boolean check = true;
                        while (check) {
                            bProdMSRP = scan.nextDouble();
                            if (bProdMSRP < 0.00 || bProdMSRP > 99999.99) {
                                System.out.println("Invalid Number. Try again.");
                            } else {
                                System.out.println("Setting MSRP..");
                                query.setDouble(2, bProdMSRP);
                                check = false;
                            }
                        }
                        check = true;
                        System.out.println("Enter Branded Product\'s Selling Price: ");
                        while (!scan.hasNextDouble()) {

                            String trash = scan.next();

                            System.out.println("Invalid. Please Enter Number correctly (\'-----.--\'): ");
                        }

                        while (check) {
                            bProdSelling = scan.nextDouble();
                            if (bProdSelling < 0.00 || bProdSelling > 99999.99) {
                                System.out.println("Invalid Number. Try again.");
                            } else {
                                System.out.println("Setting Sell Price..");
                                query.setDouble(3, bProdSelling);
                                check = false;
                            }
                        }
                        System.out.println("Executing update");
                        query.executeUpdate();
                        System.out.println("Successful");
                        check4 = false;
                    } catch (SQLException e) {
                        System.out.println("Error during execution...");
                    } catch (Throwable e) {
                        System.out.println("Error. Exiting...");
                        quit(con);
                    }

                } else {
                    System.out.println("Error. Invalid Command, Try Again");
                }
            }
        } catch (Throwable e) {
            System.out.println("Error. Exiting....");

            quit(con);
        }
    }

    public void updateGenericProduct(Connection con) {
        System.out.println("Which product do you want to update?");
        PreparedStatement query;
        String id = "";
        int idNumber = 0;
        boolean check2 = true;

        System.out.print("Enter ID: ");

        boolean check3 = true;

        while (check3) {
            try {
                id = scan.nextLine();
            } catch (Throwable e) {
                System.out.println("That wasn\'t nice of you...");
                System.exit(0);
            }
            try {
                idNumber = Integer.parseInt(id);
                if (id.length() > 6 || id.length() <= 0) {
                    System.out.println("Error: ID Number must be within 1 - 6 digits. Try Again.");
                    check3 = true;
                } else if (idNumber < 0 || idNumber > 99999) {
                    System.out.println("Error: ID Number must be within 1 - 6 digits. Try Again.");
                    check3 = true;
                } else {
                    System.out.println("Setting ID...");
                    check3 = false;
                }
            } catch (Exception e) {
                System.out.println("Error. Enter an Integer: ");

            }
        }
        System.out.println("What would you like to update?");
        System.out.println("{M}: Product\'s MSRP");
        System.out.println("{S}: Product\'s Selling Price");
        System.out.println("{N}: Product\'s Name");
        System.out.println("{B}: Both Product\'s MSRP and Selling Price");
        System.out.println("{A}: Product\'s Name, MSRP, Selling Price");

        String answer = "";

        boolean check4 = true;
        try {
            while (check4) {
                try {
                    answer = scan.nextLine();
                } catch (Throwable e) {
                    System.out.println("That wasn\'t nice of you...");
                    System.exit(0);
                }
                if (answer.equals("M")) {
                    String sql = "update products set prod_msrp = ? where prod_id = " + id;
                    try {
                        query = con.prepareStatement(sql);
                        double prodMSRP;
                        System.out.println("Enter Product\'s MSRP: ");
                        while (!scan.hasNextDouble()) {
                            String trash = scan.next();
                            System.out.println("Invalid. Please Enter Number correctly (\'-----.--\'): ");
                        }
                        boolean check = true;
                        while (check) {
                            prodMSRP = scan.nextDouble();
                            if (prodMSRP < 0.00 || prodMSRP > 99999.99) {
                                System.out.println("Invalid Number. Try again.");
                            } else {
                                System.out.println("Setting param 1");
                                query.setDouble(1, prodMSRP);
                                check = false;
                            }
                        }

                        System.out.println("Executing...");
                        query.executeUpdate();
                        System.out.println("Successful");
                        check4 = false;

                    } catch (SQLException e) {
                        System.out.println("Error during execution....");
                    }

                } else if (answer.equals("S")) {
                    String sql = "update products set prod_selling = ? where prod_id = " + id;
                    try {
                        query = con.prepareStatement(sql);
                        double prodSelling;
                        System.out.println("Enter Product\'s Selling Price: ");
                        while (!scan.hasNextDouble()) {
                            String trash = scan.next();
                            System.out.println("Invalid. Please Enter Number correctly (\'-----.--\'): ");
                        }
                        boolean check = true;
                        while (check) {
                            prodSelling = scan.nextDouble();
                            if (prodSelling < 0.00 || prodSelling > 99999.99) {
                                System.out.println("Invalid Number. Try again.");
                            } else {
                                System.out.println("Setting param 1");
                                query.setDouble(1, prodSelling);
                                check = false;
                            }
                        }

                        System.out.println("Executing...");
                        query.executeUpdate();
                        System.out.println("Successful");
                        check4 = false;
                    } catch (SQLException e) {
                        System.out.println("Error during execution...");
                    }

                } else if (answer.equals("B")) {
                    check4 = false;
                    String sql = "update products set prod_msrp = ?, prod_selling = ? where prod_id = " + id;
                    try {
                        query = con.prepareStatement(sql);
                        double prodMSRP;
                        double prodSelling;
                        System.out.println("Enter Product\'s MSRP: ");
                        while (!scan.hasNextDouble()) {

                            String trash = scan.next();

                            System.out.println("Invalid. Please Enter Number correctly (\'-----.--\'): ");
                        }
                        boolean check = true;
                        while (check) {
                            prodMSRP = scan.nextDouble();
                            if (prodMSRP < 0.00 || prodMSRP > 99999.99) {
                                System.out.println("Invalid Number. Try again.");
                            } else {
                                System.out.println("Setting param 1");
                                query.setDouble(1, prodMSRP);
                                check = false;
                            }
                        }
                        check = true;
                        System.out.println("Enter Product\'s Selling Price: ");
                        while (!scan.hasNextDouble()) {

                            String trash = scan.next();

                            System.out.println("Invalid. Please Enter Number correctly (\'-----.--\'): ");
                        }

                        while (check) {
                            prodSelling = scan.nextDouble();
                            if (prodSelling < 0.00 || prodSelling > 99999.99) {
                                System.out.println("Invalid Number. Try again.");
                            } else {
                                System.out.println("Setting param 2");
                                query.setDouble(2, prodSelling);
                                check = false;
                            }
                        }
                        System.out.println("Executing update");
                        query.executeUpdate();
                        System.out.println("Successful");
                        check4 = false;
                    } catch (SQLException e) {
                        System.out.println("Error in Execution");
                    } catch (Throwable e) {
                        System.out.println("Error. Something must\'ve happened exiting out..");
                        System.exit(0);
                    }
                } else if (answer.equals("N")) {
                    check4 = false;
                    String sql = "update products set prod_name = ? where prod_id = " + id;
                    try {
                        query = con.prepareStatement(sql);
                        String prodName = "";
                        boolean check5 = true;
                        System.out.println("Enter name: ");
                        while (check5) {
                            try {
                                prodName = scan.nextLine();
                                if (prodName.length() > 50 || prodName.length() <= 0) {
                                    System.out.println("Must Have a name or must be less than 50 characters");
                                }
                                if (prodName.contains("\'")) {
                                    System.out.println("That\'s not allowed. Try again");
                                } else {
                                    check5 = false;
                                }
                            } catch (Throwable e) {
                                System.out.println("That was not nice at all >:(");
                                quit(con);
                            }

                        }
                        System.out.println("Setting New Name ...");
                        query.setString(1, prodName);
                        query.executeQuery();
                        System.out.println("Set!");

                    } catch (SQLException e) {
                        System.out.println("Error");
                        System.exit(0);
                    }

                } else if (answer.equals("A")) {
                    check4 = false;
                    String sql = "update products set prod_name = ?, prod_msrp = ?, prod_selling = ? where prod_id = "
                            + id;
                    try {
                        query = con.prepareStatement(sql);
                        String prodName = "";
                        boolean check5 = true;
                        System.out.println("Enter name: ");
                        while (check5) {
                            try {
                                prodName = scan.nextLine();
                                if (prodName.length() > 50 || prodName.length() <= 0) {
                                    System.out.println("Must Have a name or must be less than 50 characters");
                                }
                                if (prodName.contains("\'")) {
                                    System.out.println("That\'s not allowed. Try again");
                                } else {
                                    check5 = false;
                                }
                            } catch (Throwable e) {
                                System.out.println("That was not nice at all >:(");
                                quit(con);
                            }

                        }
                        System.out.println("Setting New Name ...");
                        query.setString(1, prodName);
                        System.out.println("Set!");
                        double prodMSRP;
                        double prodSelling;
                        System.out.println("Enter Product\'s MSRP: ");
                        while (!scan.hasNextDouble()) {

                            String trash = scan.next();

                            System.out.println("Invalid. Please Enter Number correctly (\'-----.--\'): ");
                        }
                        boolean check = true;
                        while (check) {
                            prodMSRP = scan.nextDouble();
                            if (prodMSRP < 0.00 || prodMSRP > 99999.99) {
                                System.out.println("Invalid Number. Try again.");
                            } else {
                                System.out.println("Setting MSRP..");
                                query.setDouble(2, prodMSRP);
                                check = false;
                            }
                        }
                        check = true;
                        System.out.println("Enter Branded Product\'s Selling Price: ");
                        while (!scan.hasNextDouble()) {

                            String trash = scan.next();

                            System.out.println("Invalid. Please Enter Number correctly (\'-----.--\'): ");
                        }

                        while (check) {
                            prodSelling = scan.nextDouble();
                            if (prodSelling < 0.00 || prodSelling > 99999.99) {
                                System.out.println("Invalid Number. Try again.");
                            } else {
                                System.out.println("Setting Sell Price..");
                                query.setDouble(3, prodSelling);
                                check = false;
                            }
                        }
                        System.out.println("Executing update");
                        query.executeUpdate();
                        System.out.println("Successful");
                        check4 = false;
                    } catch (SQLException e) {
                        System.out.println("Error during execution");
                    } catch (Throwable e) {
                        System.out.println("Why.. :(");
                        quit(con);
                    }

                } else {
                    System.out.println("Error. Invalid Command, Try Again");
                }
            }
        } catch (Throwable e) {
            System.out.println("Error. Exiting....");

            quit(con);
        }

    }

    public void recallGenericProduct(Connection con) {
        ResultSet res;
        PreparedStatement query;
        System.out.println("Oh no! It seems like there is a problem with one of the generic products");
        System.out.println("Do you already know the product ID?");
        System.out.println("{Y}: Yes");
        System.out.println("{N}: No");
        boolean check = true;
        String answer = "";
        int idNumber = 0;

        while (check) {
            try {
                answer = scan.nextLine();
            } catch (Throwable e) {
                System.out.println("You weren\'t supposed to do that!");
                System.exit(0);
            }

            if (answer.equals("Y")) {
                break;
            }

            else if (answer.equals("N")) {
                System.out.println("Here is the list of products");
                viewGenericProduct(con);
                check = false;
            } else {
                System.out.println("");
            }
        }

        System.out.print("Enter ID: ");
        boolean check3 = true;
        while (check3) {
            try {
                this.prodID = scan.nextLine();
                System.out.println(prodID);
            } catch (Throwable e) {
                System.out.println("You weren\'t supposed to do that ");
                System.exit(0);
            }
            try {
                idNumber = Integer.parseInt(prodID);
                if (prodID.length() > 6 || prodID.length() <= 0) {
                    System.out.println("Error: ID Number must be within 1 - 6 digits. Try Again.");
                    check3 = true;
                } else if (idNumber < 0 || idNumber > 99999) {
                    System.out.println("Error: ID Number must be within 1 - 6 digits. Try Again.");
                    check3 = true;
                } else {
                    System.out.println("Setting ID...");
                    check3 = false;
                }
            } catch (Exception e) {
                System.out.println("Error. Enter an Integer: ");
            }
        }

        String sql = "SELECT TO_CHAR(sup_id,'000000') as sup_ID, sup_name, suppliers.address as sup_address, TO_CHAR(prod_id,'000000') as prod_id, prod_name, TO_CHAR(manufacturer.m_id, '000000') as m_id, m_name, manufacturer.address as m_address "
                + "FROM suppliers, supplies, products, manufactures, manufacturer "
                + "where products.prod_id = ? and suppliers.sup_id = supplies.s_id and products.prod_id = supplies.p_id "
                + "and manufactures.p_id = products.prod_id and manufactures.m_id = manufacturer.m_id";

        try {
            query = con.prepareStatement(sql);
            query.setString(1, prodID);
            res = query.executeQuery();
            if (!res.next()) {
                System.out.println(
                        "This product does not have a supplier / manufacturer for some reason... Should contact the Supplier Manager");
            } else {
                System.out.println("Here are the Supplier(s) and Manufacturer(s) for this product");
                System.out.printf("%-20s %-40s %-20s %-40s", "Supplier Name","Supplier Address",
                "Manufacturer Name", "Manufacturer Address");
                System.out.println();
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------");

                do {
                    System.out.printf("%-20s %-40s %-20s %-40s", res.getString("sup_name"),res.getString("sup_address"),
                            res.getString("m_name"), res.getString("m_address"));
                    System.out.println();
                } while (res.next());
                System.out.println(
                        "Should really contact these people... You have their address so go crazy with that information. I\'m a program. Not the police :-)");
            }
        } catch (SQLException e) {
            System.out.println("Error during execution...");
            quit(con);

        }

    }
    public void recallBrandedProduct(Connection con) {
        ResultSet res;
        PreparedStatement query;
        System.out.println("Oh no! It seems like there is a problem with one of the Branded products");
        System.out.println("Do you already know the product ID?");
        System.out.println("{Y}: Yes");
        System.out.println("{N}: No");
        boolean check = true;
        String answer = "";
        int idNumber = 0;

        while (check) {
            try {
                answer = scan.nextLine();
            } catch (Throwable e) {
                System.out.println("You weren\'t supposed to do that!");
                System.exit(0);
            }

            if (answer.equals("Y")) {
                break;
            }

            else if (answer.equals("N")) {
                System.out.println("Here is the list of products");
                viewBrandedProduct(con);
                check = false;
            } else {
                System.out.println("");
            }
        }

        System.out.print("Enter ID: ");
        boolean check3 = true;
        while (check3) {
            try {
                this.bProdID = scan.nextLine();
            } catch (Throwable e) {
                System.out.println("You weren\'t supposed to do that ");
                System.exit(0);
            }
            try {
                idNumber = Integer.parseInt(bProdID);
                if (bProdID.length() > 6 || bProdID.length() <= 0) {
                    System.out.println("Error: ID Number must be within 1 - 6 digits. Try Again.");
                    check3 = true;
                } else if (idNumber < 0 || idNumber > 99999) {
                    System.out.println("Error: ID Number must be within 1 - 6 digits. Try Again.");
                    check3 = true;
                } else {
                    System.out.println("Setting ID...");
                    check3 = false;
                }
            } catch (Exception e) {
                System.out.println("Error. Enter an Integer: ");
            }
        }

        String sql = "SELECT TO_CHAR(sup_id,'000000') as sup_ID, sup_name, suppliers.address as sup_address, TO_CHAR(bprod_id,'000000') as bprod_id, bprod_name, TO_CHAR(manufacturer.m_id, '000000') as m_id, m_name, manufacturer.address as m_address "
                + "FROM suppliers, has_supplier, branded_products, manufactures_branded, manufacturer "
                + "where branded_products.bprod_id = ? and suppliers.sup_id = has_supplier.s_id and branded_products.bprod_id = has_supplier.b_id "
                + "and manufactures_branded.b_id = branded_products.bprod_id and manufactures_branded.m_id = manufacturer.m_id";

        try {
            query = con.prepareStatement(sql);
            query.setString(1, bProdID);
            res = query.executeQuery();
            if (!res.next()) {
                System.out.println(
                        "This product does not have a supplier / manufacturer for some reason... Should contact the Supplier Manager");
            } else {
                System.out.println("Here are the Supplier(s) and Manufacturer(s) for this product");
                System.out.printf("%-20s %-45s %-20s %-40s", "Supplier Name","Supplier Address",
                "Manufacturer Name", "Manufacturer Address");
                System.out.println();
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------");

                do {
                    System.out.printf("%-20s %-45s %-20s %-40s", res.getString("sup_name"),res.getString("sup_address"),
                            res.getString("m_name"), res.getString("m_address"));
                    System.out.println();
                } while (res.next());
                System.out.println(
                        "Should really contact these people... You have their address so go crazy with that information. I\'m a program. Not the police :-)");
            }
        } catch (SQLException e) {
            System.out.println("Error during execution...");
            quit(con);

        }

    }

    public void quit(Connection con) {
        try {
            con.close();
            System.exit(0);
        } catch (SQLException e) {
            System.out.println("Error in closing connection");
            System.exit(0);
        }
    }

}