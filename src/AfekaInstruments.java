import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class AfekaInstruments {

	static Scanner consoleScanner = new Scanner(System.in);

	public static void main(String[] args) throws CloneNotSupportedException {
		ArrayList<MusicalInstrument> allInstruments = new ArrayList<>();
		File file = getInstrumentsFileFromUser();
		loadInstrumentsFromFile(file, allInstruments);
		if (allInstruments.size() == 0) {
			System.out.println("There are no instruments in the store currently");
			return;
		}
		System.out.println();
		printInstruments(allInstruments);
		int different = getNumOfDifferentElements(allInstruments);
		System.out.println("\n\nDifferent Instruments: " + different);
		MusicalInstrument mostExpensive = getMostExpensiveInstrument(allInstruments);
		System.out.println("\n\nMost Expensive Instrument:\n" + mostExpensive);
		startInventoryMenu(allInstruments);
	}

	/*** ----------------------Open and read data from file---------------------- ***/

	public static File getInstrumentsFileFromUser() {
		boolean stopLoop = true;
		File file;
		do {
			System.out.println("Please enter instruments file name / path:");
			String filepath = consoleScanner.nextLine();
			file = new File(filepath);
			stopLoop = file.exists() && file.canRead();

			if (!stopLoop)
				System.out.println("\nFile Error! Please try again\n\n");
		} while (!stopLoop);
		return file;
	}

	public static void loadInstrumentsFromFile(File file, ArrayList<MusicalInstrument> allInstruments) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			addAllInstruments(allInstruments, loadGuitars(scanner));
			addAllInstruments(allInstruments, loadBassGuitars(scanner));
			addAllInstruments(allInstruments, loadFlutes(scanner));
			addAllInstruments(allInstruments, loadSaxophones(scanner));

		} catch (InputMismatchException | IllegalArgumentException ex) {
			System.err.println("\n" + ex.getMessage());
			System.exit(1);
		} catch (FileNotFoundException ex) {			
//			System.err.println("\nFile Error! File was not found");
//			System.exit(2);
		} finally {
			scanner.close();
		}
		System.out.println("\nInstruments loaded from file successfully!\n");

	}

	public static ArrayList<Guitar> loadGuitars(Scanner scanner) {
		int numOfInstruments = scanner.nextInt();
		ArrayList<Guitar> guitars = new ArrayList<Guitar>(numOfInstruments);
		for (int i = 0; i < numOfInstruments; i++)
			guitars.add(new Guitar(scanner));
		return guitars;
	}

	public static ArrayList<Bass> loadBassGuitars(Scanner scanner) {
		int numOfInstruments = scanner.nextInt();
		ArrayList<Bass> bassGuitars = new ArrayList<Bass>(numOfInstruments);
		for (int i = 0; i < numOfInstruments; i++)
			bassGuitars.add(new Bass(scanner));
		return bassGuitars;
	}

	public static ArrayList<Flute> loadFlutes(Scanner scanner) {
		int numOfInstruments = scanner.nextInt();
		ArrayList<Flute> flutes = new ArrayList<Flute>(numOfInstruments);
		for (int i = 0; i < numOfInstruments; i++)
			flutes.add(new Flute(scanner));
		return flutes;
	}

	public static ArrayList<Saxophone> loadSaxophones(Scanner scanner) {
		int numOfInstruments = scanner.nextInt();
		ArrayList<Saxophone> saxophones = new ArrayList<Saxophone>(numOfInstruments);
		for (int i = 0; i < numOfInstruments; i++)
			saxophones.add(new Saxophone(scanner));
		return saxophones;
	}

	/*** ----------------------Methods---------------------- ***/
	// add instruments from one arrayList to another
	public static <T extends MusicalInstrument> void addAllInstruments(ArrayList<? super T> instruments,
			ArrayList<T> moreInstruments) {
		for (int i = 0; i < moreInstruments.size(); i++) {
			instruments.add(moreInstruments.get(i));
		}
	}

	// print all instruments in arrayList
	public static <T extends MusicalInstrument> void printInstruments(ArrayList<T> instruments) {
		for (int i = 0; i < instruments.size(); i++)
			System.out.println(instruments.get(i));
	}

	// get number of different elements
	public static <T extends MusicalInstrument> int getNumOfDifferentElements(ArrayList<T> instruments) {
		int numOfDifferentInstruments;
		ArrayList<T> differentInstruments = new ArrayList<T>();
		System.out.println();
		for (int i = 0; i < instruments.size(); i++) {
			if (!differentInstruments.contains((instruments.get(i)))) {
				differentInstruments.add(instruments.get(i));
			}
		}
		if (differentInstruments.size() == 1)
			numOfDifferentInstruments = 0;
		else
			numOfDifferentInstruments = differentInstruments.size();
		return numOfDifferentInstruments;
	}

	// search instrument by Feature characteristic
	public static <T extends MusicalInstrument> T getMostExpensiveInstrument(ArrayList<T> instruments) {
		double maxPrice = 0;
		T mostExpensive = (T) instruments.get(0);

		for (int i = 0; i < instruments.size(); i++) {
			T temp = (T) instruments.get(i);
			if (temp.getPrice().doubleValue() > maxPrice) {
				maxPrice = temp.getPrice().doubleValue();
				mostExpensive = temp;
			}
		}
		return mostExpensive;
	}

	/*** ----------------------Inventory menu---------------------- ***/
	public static void startInventoryMenu(ArrayList<MusicalInstrument> allInstruments) {
		int userOption = 0;
		AfekaInventory<MusicalInstrument> inventoryArr = new AfekaInventory<MusicalInstrument>(
				new ArrayList<MusicalInstrument>());
		do {
			System.out.print("-------------------------------------------------------------------------\n"
					+ "AFEKA MUSICAL INSTRUMENT INVENTORY MENU\n"
					+ "-------------------------------------------------------------------------\n"
					+ "1. Copy All String Instruments To Inventory\n" + "2. Copy All Wind Instruments To Inventory\n"
					+ "3. Sort Instruments By Brand And Price \n" + "4. Search Instrument By Brand And Price \n"
					+ "5. Delete Instrument \n" + "6. Delete all Instruments \n" + "7. Print Inventory Instruments \n"
					+ "Choose your option or any other key to EXIT \n" + "\nYour Option: ");

			// for option that input is not between 1 to 7 --> finish
			try {
				userOption = consoleScanner.nextInt();
				if (userOption > 7 || userOption < 0)
					throw new IllegalArgumentException();
			} catch (InputMismatchException | IllegalArgumentException e) {
				System.out.println("Finished!");
				break;
			}

			switch (userOption) {
			case 1:
				inventoryArr.addAllStringInstruments(allInstruments, inventoryArr.getArrInstruments());
				System.out.println("\nAll String Instruments Added Successfully!\n");
				break;
			case 2:
				inventoryArr.addAllWindInstruments(allInstruments, inventoryArr.getArrInstruments());
				System.out.println("\nAll Wind Instruments Added Successfully!\n");
				break;
			case 3:
				inventoryArr.SortByBrandAndPrice(inventoryArr.getArrInstruments());
				System.out.println("\nInstruments Sorted Successfully!\n");
				break;
			case 4:
				System.out.print("SEARCH INSTRUMENT:\n");
				searchInstrument(inventoryArr, consoleScanner);
				break;
			case 5:
				System.out.print("DELETE INSTRUMENT:\n");
				int index = searchInstrument(inventoryArr, consoleScanner);
				if (index >= 0) {
					System.out.print("Are You Sure?(Y/N) ");
					String toDelete = consoleScanner.next();
					//consoleScanner.next();
					if (toDelete.equalsIgnoreCase("y")) {
						inventoryArr.removeInstrument(inventoryArr.getArrInstruments(),
								(MusicalInstrument) inventoryArr.getArrInstruments().get(index));
						inventoryArr.setPriceAmount();
						System.out.println("\nInstrument Deleted Successfully!\n");
					}
				}
				break;
			case 6:
				System.out.print("Are You Sure?(Y/N) ");
				String toDelete = consoleScanner.next();
				if (toDelete.equalsIgnoreCase("y")) {
					inventoryArr.removeAll(inventoryArr.getArrInstruments());
					System.out.println("\nInstrument Deleted Successfully!\n");
				}
				break;
			case 7:
				System.out.println(inventoryArr.toString());
				break;
			}
		} while (userOption >= 0 && userOption <= 7);
	}

	// method for search a instrument by user Requirements
	// the method return the index of instruments
	// print the instrument if found
	public static int searchInstrument(AfekaInventory<MusicalInstrument> a, Scanner in) {
		System.out.print("Brand: ");
		String brand = in.next();
		brand = brand.substring(0, 1).toUpperCase() + brand.substring(1).toLowerCase();
		System.out.print("Price: ");
		Number price;
		try {
			price = in.nextDouble();
			// even if user type for price illegal value, we will find a instrument.
		} catch (InputMismatchException|NumberFormatException ex) {
			price = 0;
			consoleScanner.next();
		}
		int index = a.binnarySearchByBrandAndPrice(a.getArrInstruments(), price, brand);
		if (index >= 0) {
			System.out.print("Result:\n \t" + a.getArrInstruments().get(index).toString() + "\n\n");
		} else {
			System.out.println("\nInstrument Not Found!\n");
		}
		return index;
	}

}