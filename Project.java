
/** 
 * Jeffery Park
 * Cse241
 * Project
 * 05/03/2019
 */
import java.sql.*;
import java.io.*;
import java.util.Scanner;

/**
 * Main Class for this project
 */
public class Project {
	public static void main(String[] args) throws SQLException, IOException, java.lang.ClassNotFoundException {
		Scanner scan = new Scanner(System.in);
		Console console = System.console();
		Connection con;
		ResultSet res;

		System.out.println("=====================================================================================");
		System.out.println("|    __________    _________     ________     _______    __________    __    ___    |");
		System.out.println("|   |   ____   \\  |         |  /    __   \\   /  ___  \\  |   ____   \\  |  |  /  /    |");
		System.out.println("|   |  |    \\  |  |    _____| |   /   |__|  |  /   \\  | |  |    \\  |  |  | /  /     |");
		System.out.println("|   |  |____/  /  |   |____   |  |          | |     | | |  |____/  /  |  |/  /      |");
		System.out.println("|   |   ___   |   |    ____|  |  |   _____  | |     | | |   ___   |   |     /       |");
		System.out.println(
				"|   |  |   \\  \\   |   |_____  |   \\ |__   \\ | |     | | |  |   \\  \\   |   _  \\      |");
		System.out.println(
				"|   |  |    \\  \\  |         |  \\       |  | |  \\___/  | |  |    \\  \\  |  | \\  \\     |");
		System.out.println(
				"|   |__|     \\__\\ |_________|   \\____/ |__|  \\_______/  |__|     \\__\\ |__|  \\__\\    |");
		System.out.println("|                                                                                   |");
		System.out.println("=====================================================================================");

		System.out.println("Welcome to Regork Database!");
		System.out.println("What would you like to do?");
		System.out.println("{E}: Enter Regork\'s Management");
		System.out.println("{Q}: Quit");
		String userInput = "";

		String user = "";
		String pass = "";

		boolean some = true;
		boolean check = true;
		while (check) {
			try {
				userInput = scan.nextLine();
			} catch (Throwable e) {
				System.out.println("That wasn\'t nice of you...");
				System.exit(0);
			}
			if (userInput.equals("E")) {
				while (check) {
					System.out.println("Enter UserID");
					try {
						user = scan.nextLine();
					} catch (Throwable e) {
						System.out.println("That wasn\'t nice of you...");
						System.exit(0);
					}
					System.out.println("Enter Password");
					try {
						pass = String.valueOf(console.readPassword());
					} catch (Throwable e) {
						System.out.println("That wasn\'t nice of you...");
						System.exit(0);
					}
					try {
						con = DriverManager.getConnection("jdbc:oracle:thin:@edgar1.cse.lehigh.edu:1521:cse241", user,
								pass);
						System.out.println("connection successfully made.");
						check = false;

						boolean check1 = true;
						String view = "";
						while (check1) {
							printEntryMenu();
							try {
								view = scan.nextLine();
							} catch (Throwable e) {
								System.out.println("That wasn\'t nice of you...");
								System.exit(0);
							}
							switch (view) {
							case ("P"):
								Products prod = new Products();
								printTypesOfProducts();
								String typeProduct = "";
								boolean checker = true;
								while (checker) {
									try {
										typeProduct = scan.nextLine();
									} catch (Throwable e) {
										System.out.println("That wasn\'t nice of you...");
										System.exit(0);
									}
									if (typeProduct.equals(prod.getBrandedProduct())) {
										checker = false;
										String brandedAction = "";
										boolean check3 = true;
										while (check3) {
											printBrandedProductMenu();
											try {
												brandedAction = scan.nextLine();
											} catch (Throwable e) {
												System.out.println("That wasn\'t nice of you...");
												System.exit(0);
											}
											if (brandedAction.equals(prod.getView())) {
												prod.viewBrandedProduct(con);
												goBackOrQuit(con);
											} else if (brandedAction.equals(prod.getUpdate())) {
												prod.updateBrandedProduct(con);
												goBackOrQuit(con);
											} else if (brandedAction.equals(prod.getRecall())) {
												prod.recallBrandedProduct(con);
												goBackOrQuit(con);
											}
											else if (brandedAction.equals("B")) {
												check3 = false;
											} else if (brandedAction.equals("Q")) {
												quit(con);
											} else {
												System.out.println("Error: Invalid Command. Enter command again");
											}
										}
									} else if (typeProduct.equals(prod.getGenericProduct())) {
										checker = false;
										String genericAction = "";
										boolean checking = true;
										while (checking) {
											printGenericProductMenu();
											try {
												genericAction = scan.nextLine();
											} catch (Throwable e) {
												System.out.println("That wasn\'t nice of you...");
												System.exit(0);
											}

											if (genericAction.equals(prod.getView())) {
												prod.viewGenericProduct(con);
												goBackOrQuit(con);
											} else if (genericAction.equals(prod.getUpdate())) {
												prod.updateGenericProduct(con);
												goBackOrQuit(con);
											} else if (genericAction.equals(prod.getRecall())) {
												prod.recallGenericProduct(con);
												goBackOrQuit(con);
											} else if (genericAction.equals("B")) {
												checking = false;
											} else if (genericAction.equals("Q")) {
												quit(con);
											} else {
												System.out.println("Invalid Selection. Please try again");
											}
										}
									} else {
										System.out.println("Error: Invalid Selection. Please Try Again");
									}
								}
								break;
							case ("S"):
								Shipments ship = new Shipments();
								String shipmentAction = "";

								boolean checkType = true;

								while (checkType) {
									printShipmentMenu();
									try {
										shipmentAction = scan.nextLine();
									} catch (Throwable e) {
										System.out.println("That wasn\'t nice of you...");
										System.exit(0);
									}

									if (shipmentAction.equals(ship.getView())) {

										boolean bool = true;
										String typeShipment = "";
										while (bool) {
											printShipmentTypeMenu();
											try {
												typeShipment = scan.nextLine();
											} catch (Throwable e) {
												System.out.println("You weren\'t supposed to do that");
												try {
													con.close();
												} catch (SQLException d) {
													System.out.println("Error in closing connection...");
												}
												System.exit(0);
											}

											if (typeShipment.equals("A")) {
												ship.viewAllShipments(con);
												goBackOrQuit(con);
											} else if (typeShipment.equals("O")) {
												ship.viewOutgoingShipments(con);
												goBackOrQuit(con);
											} else if (typeShipment.equals("I")) {
												ship.viewIncomingShipments(con);
												goBackOrQuit(con);
											} else if (typeShipment.equals("B")) {
												bool = false;
											} else {
												System.out.println("Invalid command. Try Again");
											}
										}
									} else if (shipmentAction.equals(ship.getInsert())) {
										ship.insertNewShipment(con);
										goBackOrQuit(con);
									} else if (shipmentAction.equals(ship.getDelete())) {
										ship.deleteShipment(con);
										goBackOrQuit(con);
									} else if (shipmentAction.equals("B")) {
										checkType = false;
									} else if (shipmentAction.equals("Q")) {
										quit(con);
									} else {
										System.out.println("Such action does not exist, Try again");
									}
								}

								break;
							case ("U"):
								Suppliers sup = new Suppliers();

								String answer = "";
								boolean checkSup = true;
								while (checkSup) {
									printSupplyMenu();
									try {
										answer = scan.nextLine();
									} catch (Throwable e) {
										System.out.println("That wasn\'t nice of you...");
										System.exit(0);
									}

									switch (answer) {
									case ("V"):
										printViewSuppliers(con);
										goBackOrQuit(con);
										break;
									case ("I"):
										sup.insertSupplier(con);
										goBackOrQuit(con);
										break;
									case ("B"):
										checkSup = false;
										break;
									case ("Q"):
										quit(con);
										break;
									}
								}
								break;
							case ("Q"):
								quit(con);
								break;
							default:
								System.out.println("Error: No Such Role Exsts. Try again: ");
								break;
							}

						}

					} catch (SQLException e) {
						System.out.println("Error: Enter Credentials again.");
					}

				}
			} else if (userInput.equals("Q")) {
				System.exit(0);
			} else {
				System.out.println("Error: Wrong Input, try again");
			}
		}

	}

	/**
	 * @param con Connection to the databse
	 * 
	 *            This Method is used to go back to different interfaces / menus
	 *            throughout the program.
	 * 
	 */

	public static void goBackOrQuit(Connection con) {
		Scanner scan = new Scanner(System.in);
		String userInput = "";
		System.out.println("{B}: Go Back?");
		System.out.println("{Q}: Quit");
		boolean checkBack = true;
		while (checkBack) {
			try {
				userInput = scan.nextLine();
			} catch (Throwable e) {
				System.out.println("That wasn\'t nice of you...");
				System.exit(0);
			}
			if (userInput.equals("B")) {
				checkBack = false;
			} else if (userInput.equals("Q")) {
				System.out.println("Exiting...");
				try {
					con.close();
				} catch (SQLException e) {
					System.out.print("Error on closing connection .. ");
				}
				System.exit(0);
			} else {
				System.out.println("Error: Invalid Command: Enter command again.");
			}
		}

	}

	/**
	 * Prints the menu for Branded Products
	 */

	public static void printBrandedProductMenu() {
		System.out.println("Branded Products:");
		System.out.println("What would you like to do?");
		System.out.println("{V}: View Branded Products and Prices");
		System.out.println("{U}: Update A Product");
		System.out.println("{R}: Recall A Product");
		System.out.println("{B}: Go back");
		System.out.println("{Q}: Quit");
	}

	/**
	 * Prints the menu for Generic Products
	 */

	public static void printGenericProductMenu() {
		System.out.println("Generic Products:");
		System.out.println("What would you like to do?");
		System.out.println("{V}: View Generic Products and Prices");
		System.out.println("{U}: Update A Product");
		System.out.println("{R}: Recall A Product");
		System.out.println("{B}: Go back");
		System.out.println("{Q}: Quit");
	}

	/**
	 * Prompts user to specify what type of product he/she would like to view.
	 */

	public static void printTypesOfProducts() {
		System.out.println("Would you like to view Branded Products or Generic Products?");
		System.out.println("{B}: Branded Products");
		System.out.println("{G}: Generic Products");
	}
	/**
	 * Prints menu options for Shipment Manager
	 */

	public static void printShipmentMenu() {
		System.out.println("Shipments: ");
		System.out.println("What would you like to do? ");
		System.out.println("{V}: View Shipments");
		System.out.println("{I}: Insert New Shipment");
		System.out.println("{D}: Delete A Shipment");
		System.out.println("{B}: Go Back");
		System.out.println("{Q}: Quit");

	}

	/**
	 * Prompts user to specify what type of shipment
	 * he/she would like to view.
	 */
	public static void printShipmentTypeMenu() {
		System.out.println("Would you like to view all shipments, outgoing shipments, or incoming shipments?");
		System.out.println("{A}: All Shipments");
		System.out.println("{O}: Outgoing Shipments");
		System.out.println("{I}: Incoming Shipments");
		System.out.println("{B}: Go Back");
	}

	/**
	 * Prints menu for the Supplier Manger Interface
	 */
	public static void printSupplyMenu() {
		System.out.println("Suppliers: ");
		System.out.println("What would you like to do? ");
		System.out.println("{V}: View Suppliers");
		System.out.println("{I}: Insert New Supplier");
		System.out.println("{B}: Go back");
		System.out.println("{Q}: Quit");
	}
	/**
	 * @param con - Connection
	 * 
	 * A function to quit out of the connection 
	 * to the database and out of the program
	 */

	public static void quit(Connection con) {
		System.out.println("Closing ...");
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println("Error in closing connection...");
			System.out.println("Exiting...");
			System.exit(0);
		}
		System.exit(0);
	}

	/**
	 * @param con Connection
	 * 
	 * Prompts user if he/she would like to view all Suppliers
	 * in the database or if he/she would like to view separate
	 * types of Suppliers.
	 * 
	 */

	public static void printViewSuppliers(Connection con) {
		Scanner scan = new Scanner(System.in);
		Suppliers sup = new Suppliers();
		System.out.println("What type of Suppliers would you like to view?");
		System.out.println("{A}: All Suppliers");
		System.out.println("{B}: Branded Suppliers");
		System.out.println("{G}: Generic Suppliers");

		String answer = "";

		boolean check = true;
		while (check) {
			try {
				answer = scan.nextLine();
			} catch (Throwable e) {
				System.out.println("That wasn\'t nice of you...");
				System.exit(0);
			}
			switch (answer) {
			case ("A"):
				check = false;
				sup.viewAllSuppliers(con);
				break;
			case ("B"):
				check = false;
				sup.viewBrandedSuppliers(con);
				break;
			case ("G"):
				check = false;
				sup.viewGenericSuppliers(con);
				break;
			default:
				System.out.println("Invalid Selection. Try again");
			}

		}

	}

	/**
	 * Prints Menu to view different interfaces
	 */

	public static void printEntryMenu() {
		System.out.println("Welcome to Regork\'s Management!");
		System.out.println("What is your role?");
		System.out.println("{P}: Store Product Manager");
		System.out.println("{S}: Shipments Manager");
		System.out.println("{U}: Supplies Manager");
		System.out.println("{Q}: Quit");

	}
}