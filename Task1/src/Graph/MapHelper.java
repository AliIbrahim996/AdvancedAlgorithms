package Graph;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Helper class used for reverse lookups in a Map/Dictionary.
 */
public class MapHelper {
	public static <T1, T2> T1 getKey(Map<T1, T2> map, T2 value) {
		for (Entry<T1, T2> kv : map.entrySet()) {
			if (kv.getValue().equals(value))
				return kv.getKey();
		}
		System.out.println("Error: Value \"" + value + "\" not found, returning null.");
		return null;
	}
}
