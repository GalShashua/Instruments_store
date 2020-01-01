import java.util.ArrayList;
import java.util.Collections;

public class AfekaInventory<T extends MusicalInstrument> implements ManagementInventory<T> {
	private ArrayList<T> arrInstruments;
	private double priceAmount;
	private boolean isSorted;

	//constractor1
	public AfekaInventory() {
		setArrInstruments(new ArrayList<T>());
	}

	//constractor2
	public AfekaInventory(ArrayList<T> arrInstruments) {
		setArrInstruments(arrInstruments);
	}

	
	//---------getters and setters---------//
	public ArrayList<T> getArrInstruments() {
		return this.arrInstruments;
	}

	public void setArrInstruments(ArrayList<T> arrInstruments) {
		this.arrInstruments = arrInstruments;
		setSorted(false);
		setPriceAmount();
	}

	public double getPriceAmount() {
		return priceAmount;
	}

	public void setPriceAmount() {
		double sum = 0;
		if (this.arrInstruments.size() == 0) 
			this.priceAmount = sum;
		else {
			for (int i = 0; i < arrInstruments.size(); i++) {
				sum = SumDifferentTypes(sum, this.arrInstruments.get(i).getPrice());
			}
			this.priceAmount = sum;
		}
	}

	public boolean isSorted() {
		return isSorted;
	}

	public void setSorted(boolean isSorted) {
		this.isSorted = isSorted;
	}

	//generic method that add two numbers from different types
	public static <E extends Number, V extends Number> double SumDifferentTypes(E num1, V num2) {
		return num1.doubleValue() + num2.doubleValue();
	}

	
	//copy only string instruments from one array list to another
	public void addAllStringInstruments(ArrayList<? extends MusicalInstrument> general, ArrayList<? super MusicalInstrument> arrInstruments) {
		for (int i = 0; i < general.size(); i++) {
			if (general.get(i) instanceof StringInstrument)
				arrInstruments.add((StringInstrument) general.get(i));
		}
		setPriceAmount();
		setSorted(false);
	}

	//copy only wind instruments from one array list to another
	@Override
	public void addAllWindInstruments(ArrayList<? extends MusicalInstrument> general, ArrayList<? super MusicalInstrument> arrInstruments) {
		for (int i = 0; i < general.size(); i++) {
			if (general.get(i) instanceof WindInstrument)
				arrInstruments.add((WindInstrument) general.get(i));
		}
		setPriceAmount();
		setSorted(false);
	}

	//sort instruments by brand and price (low to high)
	@Override
	public void SortByBrandAndPrice(ArrayList<T> arrInstruments) {
		Collections.sort(arrInstruments);
		this.setSorted(true);
	}

	@Override
	public int binnarySearchByBrandAndPrice(ArrayList<T> arrInstruments, Number price, String brand) {
		if (isSorted()) {
			ArrayList<MusicalInstrument> sameBrand = new ArrayList<>();
			int low = 0;
			int high = arrInstruments.size() - 1;
			while (low <= high) {
				int mid = low + (high - low) / 2;
				if (brand.compareTo(arrInstruments.get(mid).getBrand()) < 0)
					high = mid - 1;
				else if (brand.compareTo(arrInstruments.get(mid).getBrand()) > 0)
					low = mid + 1;
				else {		//same brand, start sort by price
					if (price.doubleValue() == arrInstruments.get(mid).getPrice().doubleValue())
						return mid;
					else {
						//if we get price that not exist:
						//for number, we need to return the closer price
						//for another input, we need to return the lower price
						for (int i = 0; i < arrInstruments.size(); i++) {
							if (arrInstruments.get(i).getBrand().compareTo(brand) == 0) 
								sameBrand.add(arrInstruments.get(i));							
						}
						MusicalInstrument closerPrice=closePrice(sameBrand, price);
						for (int i = 0; i < arrInstruments.size(); i++) {
							if (closerPrice.equals(arrInstruments.get(i))) 
								return i;
						}

					}
				}
			}
		}
		return -1;
	}

	//add instrument
	@Override
	public void addInstrument(ArrayList<T> arrInstruments, T toAdd) {
		arrInstruments.add(toAdd);
	}

	//remove instrument
	@Override
	public boolean removeInstrument(ArrayList<T> arrInstruments, T toRemove) {
		return arrInstruments.remove(toRemove); // return boolean
	}

	//remove all instruments from array list
	@Override
	public boolean removeAll(ArrayList<T> arrInstruments) {
		if (arrInstruments.removeAll(arrInstruments)) {
			setSorted(false);
			setPriceAmount();
			return true;
		}
		return false;
	}

	//method that find the closer instrument by price 
	public static MusicalInstrument closePrice (ArrayList<MusicalInstrument> forSameBrand, Number price) {
		double dist1 = Math.abs(price.doubleValue() - forSameBrand.get(0).getPrice().doubleValue());
		double dist2;
		MusicalInstrument min = forSameBrand.get(0);
		for (int i = 0; i < forSameBrand.size(); i++) {
			dist1 = Math.abs(price.doubleValue() - min.getPrice().doubleValue());
			dist2 = Math.abs(price.doubleValue() - forSameBrand.get(i).getPrice().doubleValue());
			if (dist2 < dist1)
				min = forSameBrand.get(i);
		}
		return min;

	}
	
	//to string
	@Override
	public String toString() {
		String str = "-------------------------------------------------------------------------\n"
				+ "AFEKA MUSICAL INSTRUMENT INVENTORY MENU\n"
				+ "-------------------------------------------------------------------------\n";
		if (arrInstruments.size() != 0)
			for (int i = 0; i < arrInstruments.size(); i++)
				str = str + arrInstruments.get(i) + "\n";
		else {
			str = str + "There Is No Instruments To Show\n";
		}
		return str + String.format("\nTotal Price:%7.2f     Sorted: %b \n", getPriceAmount(), isSorted());

	}

}