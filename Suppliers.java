import java.sql.*;
import java.io.*;
import java.util.Scanner;

public class Suppliers {
    String suppliers;
    String bSuppliers;
    String gSuppliers;
    String supID;
    Scanner scan = new Scanner(System.in);
    Products prod = new Products();

    public Suppliers() {
        this.suppliers = "S";
        this.bSuppliers = "B";
        this.gSuppliers = "G";
        this.supID = "";
    }

    public String getSuppliers() {
        return this.suppliers;
    }

    public String getBSuppliers() {
        return this.bSuppliers;
    }

    public String getGSuppliers() {
        return this.gSuppliers;
    }

    public void viewAllSuppliers(Connection con) {
        String sql = "select TO_CHAR(sup_id,'000000') as SUP_ID, sup_name, address from suppliers order by SUP_ID";
        ResultSet res;
        PreparedStatement query;

        try {
            query = con.prepareStatement(sql);
            res = query.executeQuery();

            if (!res.next()) {
                System.out.println("No Suppliers found...");
            } else {
                System.out.println("Here is the list of all Suppliers: ");
                System.out.printf("%-8s %-30s %-60s", " SUP_ID", "SUP_NAME", "ADDRESS");
                System.out.println();
                System.out.println(
                    "---------------------------------------------------------------------------------------------------");
                do {
                    System.out.printf("%-8s %-30s %-60s",
                            res.getString("SUP_ID"), res.getString("sup_name"), res.getString("address"));
                    System.out.println();
                } while (res.next());
                query.close();
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong.");
        }

    }

    public void viewBrandedSuppliers(Connection con) {
        Scanner scan = new Scanner(System.in);
        String type = "";
        PreparedStatement query = null;
        ResultSet res = null;
        String id = "";
        int idNumber = 0;
        System.out.println("How do you want to view Branded Suppliers?");
        System.out.println("{a} view all?");
        System.out.println("{s} view a specific supplier?");
        boolean check = true;
        while (check) {

            try {
                type = scan.nextLine();
            } catch (Throwable e) {
                System.out.println("That wasn\'t nice of you...");
                System.exit(0);
            }
            if (type.equals("a")) {
                check = false;
                String sql = "select TO_CHAR(sup_id, '000000') as SUP_ID, sup_name, bprod_id, bprod_name, bprod_MSRP as MSRP, bprod_selling as Selling_Price "
                        + "from suppliers, has_supplier, branded_products where suppliers.sup_id = has_supplier.s_id and "
                        + "has_supplier.b_id = branded_products.bprod_id order by SUP_ID";
                try {
                    query = con.prepareStatement(sql);
                    res = query.executeQuery();
                    if (!res.next()) {
                        System.out.println("No matches found.");
                    } else {
                        System.out.printf("%-8s %-30s %-8s %-40s %-10s %-8s", " SUP_ID", "SUP_NAME", "BPROD_ID",
                                "BPROD_NAME", "MSRP", "SELLING_PRICE");
                        System.out.println();
                        System.out.println(
                                "--------------------------------------------------------------------------------------------------------------------");
                        do {
                            System.out.printf("%-8s %-30s %-8s %-40s %-10.2f %-7.2f", res.getString("SUP_ID"),
                                    res.getString("sup_name"), res.getString("bprod_id"), res.getString("bprod_name"),
                                    res.getDouble("MSRP"), res.getDouble("Selling_Price"));
                            System.out.println();
                        } while (res.next());
                    }
                    query.close();

                } catch (SQLException e) {
                    System.out.println("Error in selection. Please reconnect.");
                }
            } else if (type.equals("s")) {
                check = false;
                String sql = "select TO_CHAR(sup_id, '000000') as SUP_ID, sup_name, bprod_id, bprod_name, bprod_MSRP as MSRP, bprod_selling as Selling_Price "
                        + "from suppliers, has_supplier, branded_products where suppliers.sup_id = ? and suppliers.sup_id = has_supplier.s_id and "
                        + "has_supplier.b_id = branded_products.bprod_id order by SUP_ID";
                try {
                    query = con.prepareStatement(sql);

                    boolean check3 = true;
                    while (check3) {
                        System.out.print("Enter ID: ");
                        try {
                            id = scan.nextLine();
                        } catch (Throwable e) {
                            System.out.println("That wasn\'t nice of you...");
                            System.exit(0);
                        }
                        try {
                            idNumber = Integer.parseInt(id);
                            check3 = false;
                        } catch (Exception e) {
                            System.out.println("Error: Please enter a correctly formatted ID (\'000000\')");
                        }
                        if (id.length() > 6 || id.length() <= 0) {
                            System.out.println("Error: ID Number must be within 1 - 6 digits. Try Again.");
                            check3 = true;
                        } else if (idNumber < 0 || idNumber > 99999) {
                            System.out.println("Error: ID Number must be within 1 - 6 digits. Try Again.");
                            check3 = true;
                        } else {
                            query.setString(1, id);
                            check = false;
                            res = query.executeQuery();
                            if (!res.next()) {
                                System.out.println("No matches found.");
                            } else {
                                System.out.printf("%-8s %-30s %-8s %-40s %-10s %-8s", " SUP_ID", "SUP_NAME", "BPROD_ID",
                                        "BPROD_NAME", "MSRP", "SELLING_PRICE");
                                System.out.println();
                                System.out.println(
                                        "--------------------------------------------------------------------------------------------------------------------");
                                do {
                                    System.out.printf("%-8s %-30s %-8s %-40s %-10.2f %-7.2f", res.getString("SUP_ID"),
                                            res.getString("sup_name"), res.getString("bprod_id"), res.getString("bprod_name"),
                                            res.getDouble("MSRP"), res.getDouble("Selling_Price"));
                                    System.out.println();
                                } while (res.next());
                            }
                            query.close();
                        }
                    }

                } catch (SQLException e) {
                    System.out.println("Such ID does not exist or there was an error in selection...");
                }

            } else {
                System.out.print("invalid selection. please try again.");
            }
        }

    }

    public void viewGenericSuppliers(Connection con) {
        Scanner scan = new Scanner(System.in);
        String type = "";
        PreparedStatement query = null;
        ResultSet res = null;
        String id = "";
        int idNumber = 0;
        System.out.println("How do you want to view Suppliers?");
        System.out.println("{a} view all?");
        System.out.println("{s} view a specific supplier?");
        boolean check = true;
        while (check) {
            try {
                type = scan.nextLine();
            } catch (Throwable e) {
                System.out.println("That wasn\'t nice of you...");
                System.exit(0);
            }
            if (type.equals("a")) {
                check = false;
                String sql = "select TO_CHAR(sup_id, '000000') as SUP_ID, sup_name, prod_id, prod_name, prod_MSRP as MSRP, prod_selling as Selling_Price "
                        + "from suppliers, supplies, products where suppliers.sup_id = supplies.s_id and "
                        + "supplies.p_id = products.prod_id order by SUP_ID";

                try {
                    query = con.prepareStatement(sql);
                    res = query.executeQuery();
                    if (!res.next()) {
                        System.out.println("No matches found.");
                    } else {
                        System.out.printf("%-8s %-30s %-8s %-30s %-10s %-8s", " SUP_ID", "SUP_NAME", "PROD_ID",
                                "PROD_NAME", "MSRP", "SELLING_PRICE");
                        System.out.println();
                        System.out.println(
                                "--------------------------------------------------------------------------------------------------------------");
                        do {
                            System.out.printf("%-8s %-30s %-8s %-30s %-10.2f %-7.2f", res.getString("SUP_ID"),
                                    res.getString("sup_name"), res.getString("prod_id"), res.getString("prod_name"),
                                    res.getDouble("MSRP"), res.getDouble("Selling_Price"));
                            System.out.println();
                        } while (res.next());
                    }
                    query.close();
                } catch (SQLException e) {
                    System.out.println("Error in selection.");
                }
            } else if (type.equals("s")) {
                check = false;
                String sql = "select TO_CHAR(sup_id, '000000') as SUP_ID, sup_name, prod_id, prod_name, prod_MSRP as MSRP, prod_selling as Selling_Price "
                        + "from suppliers, supplies, products where suppliers.sup_id = ? and suppliers.sup_id = supplies.s_id and "
                        + "supplies.p_id = products.prod_id order by SUP_ID";
                try {
                    query = con.prepareStatement(sql);

                    boolean check3 = true;
                    while (check3) {
                        System.out.print("Enter ID: ");

                        try {
                            id = scan.nextLine();
                        } catch (Throwable e) {
                            System.out.println("That wasn\'t nice of you...");
                            System.exit(0);
                        }
                        try {
                            idNumber = Integer.parseInt(id);
                            check3 = false;
                        } catch (Exception e) {
                            System.out.println("Error: Please enter a correctly formatted ID (\'000000\')");
                        }
                        if (id.length() > 6 || id.length() <= 0) {
                            System.out.println("Error: ID Number must be within 1 - 6 digits. Try Again.");
                            check3 = true;
                        } else if (idNumber < 0 || idNumber > 99999) {
                            System.out.println("Error: ID Number must be within 1 - 6 digits. Try Again.");
                            check3 = true;
                        } else {
                            query.setString(1, id);
                            check = false;
                            res = query.executeQuery();
                            if (!res.next()) {
                                System.out.println("No Matching Data");
                            } else {
                                System.out.printf("%-8s %-30s %-8s %-30s %-10s %-8s", " SUP_ID", "SUP_NAME", "PROD_ID",
                                        "PROD_NAME", "MSRP", "SELLING_PRICE");
                                System.out.println();
                                System.out.println(
                                        "--------------------------------------------------------------------------------------------------------------");
                                do {
                                    System.out.printf("%-8s %-30s %-8s %-30s %-10.2f %-7.2f", res.getString("SUP_ID"),
                                            res.getString("sup_name"), res.getString("prod_id"),
                                            res.getString("prod_name"), res.getDouble("MSRP"),
                                            res.getDouble("Selling_Price"));
                                    System.out.println();
                                } while (res.next());
                            }
                            query.close();
                        }
                    }

                } catch (SQLException e) {
                    System.out.println("Such ID does not exist or there was an error in selection...");
                }

            } else {
                System.out.print("invalid selection. please try again.");
            }
        }

    }

    public void insertSupplier(Connection con) {
        String sql = "insert into Suppliers(sup_id, sup_name, address) values (?, ?, ?)";
        ResultSet res;
        PreparedStatement query;
        boolean check = true;

        int idNumber = 0;
        while (check) {
            try {
                query = con.prepareStatement(sql);

                System.out.println("Enter Supplier\'s ID: ");

                while (check) {
                    try {
                        supID = scan.nextLine();
                    } catch (Throwable e) {
                        System.out.println("That wasn\'t nice of you...");
                        System.exit(0);
                    }

                    try {
                        idNumber = Integer.parseInt(supID);
                        if (supID.length() > 6) {
                            System.out.println("Invalid ID: Length must be less than 6. Try again. ");
                            check = true;
                        } else if (idNumber < 0 || idNumber > 99999) {
                            System.out.println("Invalid ID: Enter ID again: ");
                            check = true;
                        } else {
                            System.out.println("Setting param 1");
                            query.setString(1, supID);
                            check = false;
                        }
                    } catch (Exception e) {
                        System.out.println("Error, enter an integer");
                    }
                }

                check = true;
                System.out.println("Enter Supplier\'s Name: ");
                String supName = "";
                while (check) {
                    try {
                        supName = scan.nextLine();
                    } catch (Throwable e) {
                        System.out.println("That wasn\'t nice of you...");
                        System.exit(0);
                    }
                    if (supName.length() > 50) {
                        System.out.println("Invalid Name: Length must be less than 50. Try again. ");
                    } else if (supName.contains("\''")) {
                        System.out.println("Invalid Name: NOT ALLOWED TRY AGAIN.");
                    } else {
                        System.out.println("Setting param 2");
                        query.setString(2, supName);
                        check = false;
                    }
                }
                check = true;

                String address = "";
                System.out.println(
                        "Enter Supplier\'s Address (This should be entered as street # street name city state zip, but I'm not stopping you. I'm just a program, not the police): ");
                while (check) {
                    try {
                        address = scan.nextLine();
                    } catch (Throwable e) {
                        System.out.println("That wasn\'t nice of you...");
                        System.exit(0);
                    }

                    if (address.length() <= 0) {
                        System.out.println("Error: Address is too short. Please enter again.");
                    } else if (address.contains("\'")) {
                        System.out.println("Error: Invalid character inserted. Please Enter Again.");
                    } else {
                        query.setString(3, address);
                        check = false;
                    }
                }
                System.out.println("Inserting Supplier...");
                try {
                    query.executeUpdate();

                } catch (SQLException e) {
                    
                    System.out.println("Error! Exiting... ");
                    System.out.println("(Please Check if duplicate Supplier ID)");
                    quit(con);
                    break;
                }

                System.out.println("Succesfully Inserted!");
            } catch (SQLException e) {
                System.out.println("Error. Exiting");
                quit(con);
            } catch (Throwable e) {
                System.out.println("ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ERROR ");
                System.out.println("TERMINATING...");
                System.exit(0);
            }

        }

        System.out.println("Does this supplier supply a generic product or a branded product?");
        System.out.println("{G}: Generic?");
        System.out.println("{B}: Branded?");

        boolean typeProduct = true;
        String typeAnswer = "";

        while (typeProduct) {
            try {
                typeAnswer = scan.nextLine();
            } catch (Throwable e) {
                System.out.println("That\'s not nice :(");
                System.exit(0);
            }
            if (typeAnswer.equals("G")) {
                typeProduct = false;
                prod.insertGenericProduct(con);
                updateSupplies(con, this.supID, prod.prodID);

            } else if (typeAnswer.equals("B")) {
                typeProduct = false;
                prod.insertBrandedProduct(con);
                updateHasSupplier(con, this.supID, prod.bProdID);
            } else {
                System.out.println("Error. A supplier that doesn\'t supply any products...? Try again. ");
            }
        }
    }

    public void updateSupplies(Connection con, String sup, String prod) {
        String sql = "insert into supplies(s_id, p_id) values (" + sup + ", " + prod + ")";

        PreparedStatement query;
        try {
            query = con.prepareStatement(sql);
            query.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error during update");
        }

    }

    public void updateHasSupplier(Connection con, String sup, String bProd) {
        String sql = "insert into has_supplier(b_id, s_id) values (" + bProd + ", " + sup + ")";

        PreparedStatement query;
        try {
            query = con.prepareStatement(sql);

            query.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error during update");
        }

    }

    public void quit(Connection con) {
        try {
            con.close();
            System.exit(0);
        } catch (SQLException e) {
            System.out.println("Error in closing connection..");
            System.exit(0);
        }

    }
}