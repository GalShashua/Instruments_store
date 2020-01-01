import java.util.ArrayList;

public interface ManagementInventory<T> {

	void addAllStringInstruments(ArrayList<? extends MusicalInstrument> general, ArrayList<? super MusicalInstrument> specific);

	void addAllWindInstruments(ArrayList<? extends MusicalInstrument> general, ArrayList<? super MusicalInstrument> specific);

	void SortByBrandAndPrice(ArrayList<T> array);

	int binnarySearchByBrandAndPrice(ArrayList<T> array, Number price, String brand);

	void addInstrument(ArrayList<T> array, T toAdd);

	boolean removeInstrument(ArrayList<T> array, T toRemove);

	boolean removeAll(ArrayList<T> array);

}
