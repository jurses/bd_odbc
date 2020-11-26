package special;

import javafx.util.Pair;

public class Combobox_pair<K, V> extends Pair<K, V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Combobox_pair(K key, V value) {
		super(key, value);
	}

	@Override
	public String toString()
	{
		return (String) super.getValue();
	}
}
