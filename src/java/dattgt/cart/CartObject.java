
package dattgt.cart;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author DATTGT
 */
public class CartObject implements Serializable{
    private Map<String, Integer> items;

    public Map<String, Integer> getItems() {
        return items;
    }

    public void addItemsToCart(String sku, int quantity) {

        if (sku == null) {
            return;
        }
        if (sku.trim().isEmpty()) {
            return;
        }
        if(quantity == 0){
            return;
        }
        //1. check existed items
        if (this.items == null) {
            this.items = new HashMap<>();
        }
        //2. Check existed item name
        if (this.items.containsKey(sku)) {
            quantity += this.items.get(sku);
        }
        //3. update items
        this.items.put(sku, quantity);
    }

    public void removedItemFromCart(String sku) {
        if (sku == null) {
            return;
        }
        //1. check existed items
        if (this.items == null) {
            return;
        }
        //2. check existed item name
        if (this.items.containsKey(sku)) {
            this.items.remove(sku);
            if (this.items.isEmpty()) {
                this.items = null;
            }
        }
    }
}
