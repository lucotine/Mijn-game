import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Player {
    public static final int ITEM_GONE = 0;
    public static final int ITEM_NOTPRESENT = 1;
    public static final int ITEM_NOTMOVEABLE = 2;
    public static final int ITEM_DROPPED = 3;
    private String playerName;
    private Room currentRoom;

    private Room previousRoom;
    private ArrayList<Item> bag = new ArrayList<>();

    private int currentWeight;
    private double maxWeight;

    public Player(String playerName, Room currentRoom, int maxWeight, int weight) {
        this.playerName = playerName;
        this.currentRoom = currentRoom;
        this.maxWeight = maxWeight;
        currentWeight = weight;

    }

    public int take(String name) {
        double currentweight = 0;
        for (Item i : bag)
                currentweight += i.getWeight();
        Item item = currentRoom.getItem(name);

        if (currentweight+ item.getWeight() > maxWeight) return ITEM_NOTMOVEABLE;

        if (item!=null && item.isMoveable()) {
            bag.add(item);
            return ITEM_GONE;
        }
        if (item==null) return ITEM_NOTPRESENT;
        return ITEM_NOTMOVEABLE;
    }

    public int drop(String name) {
        for (Item item : bag) {
            if (item.getName().equals(name)){
                currentRoom.addItem(item);
                bag.remove(item);
                return ITEM_DROPPED;
            }

        }

        return ITEM_NOTPRESENT;
    }

    public String getName() {
        return playerName;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }


    public void setCurrentRoom(Room currentRoom) {
        this.previousRoom = getCurrentRoom();
        this.currentRoom = currentRoom;

    }


    public String getBagDescription() {
        if (bag.isEmpty()) {
            return "Item bag is empty.";
        }
        String returnString = "Item bag contains " + System.lineSeparator();
        for(Item i : bag) {
            returnString += "  " + i.getLongDescription() + System.lineSeparator();
        }
        return returnString;
    }

    public void goBack() {
        this.currentRoom = this.previousRoom;
    }



}
