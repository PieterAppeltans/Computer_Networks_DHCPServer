import java.util.Hashtable;
import java.util.Map;

/**
 * Two way searchable map. You can easily lookup in both directions. In a normal map you can only fast search 
 * in one direction, but in message creating and parsing a bidirectional search is often needed (e.g. by parsing
 * you have a byte code and you want to know the message type (->) and by creating you have a message type and
 * you want the byte code (<-)).
 * 
 * Both the keys and the values must be unique, i.e. no 2 equal keys and no 2 equal values.
 * 
 * @param	<K> 
 * 				The class of the key elements.
 * @param 	<V>
 * 				The class of the value elements. 
 * @author 	Pieter Appeltans & Hans Cauwenbergh
 */
public class BidirectionalMap <K extends Object, V extends Object> {
	
	private Map<K,V> forward = new Hashtable<K, V>();
	private Map<V,K> backward = new Hashtable<V, K>();

	/**
	 * Add a new key-value pair to the map. The user must make sure that both value and key are unique.
	 * 
	 * @param	key
	 * 				The key of the new pair.
	 * @param 	value
	 * 				The value of the new pair.
	 */
	public synchronized void add(K key, V value) {
		forward.put(key, value);
		backward.put(value, key);
	}

	/**
	 * A method to search the map by a given key.
	 * 
	 * @param	key Type: K
	 * 				The key from which the value must be returned.
	 * @return 	value Type: V
	 * 				The value which is mapped to the given key. 
	 */
	public synchronized V getForward(K key) {
		return forward.get(key);
	}

	/**
	 * Method to search the map by a given value.
	 * 
	 * @param	value Type: V
	 * 				The value from which the key must be returned.
	 * @return 	key Type: K
	 * 				The key that has as value the given value.
	 */
	public synchronized K getBackward(V value) {
		return backward.get(value);
	}
	  
}