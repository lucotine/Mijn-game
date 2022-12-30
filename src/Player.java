import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Player {
    public static final int ITEM_GONE = 0;
    public static final int ITEM_NOTPRESENT = 1;
    public static final int ITEM_NOTMOVEABLE = 2;
    public static final int ITEM_DROPPED = 3;
    private String name;
    private Room currentRoom;

    private Room previousRoom;
    private ArrayList<Item> bag = new ArrayList<>();
    private boolean goToNextWorld = false;

    private double maxWeight;
    private Item key;

    public Player(String name, double maxWeight, Item key) {
        this.name = name;
        this.maxWeight = maxWeight;
        this.key = key;
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
        return name;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }
    public boolean isGoToNextWorld() {
        return goToNextWorld;
    }


    public void setGoToNextWorld(boolean goToNextWorld) {
        this.goToNextWorld = goToNextWorld;
    }

    public void setCurrentRoom(Room currentRoom) {
        int keyWorld2 = Collections.frequency(bag, key);
        this.previousRoom = getCurrentRoom();
        this.currentRoom = currentRoom;

        if (this.currentRoom.type.equals(RoomType.KEYDOOR) && currentRoom.getWorld()==keyWorld2) {
            this.goToNextWorld = true;

        } else System.out.println(" you cannot pass");

    }


    public String getBagDescription() {
        if (bag.isEmpty()) {
            return "The bag of " + name + " is empty.";
        }
        String returnString = "The bag of " + name + " contains:" + System.lineSeparator();
        for(Item i : bag) {
            returnString += "  " + i.getLongDescription() + System.lineSeparator();
        }
        return returnString;
    }

    public void goBack() {
        this.currentRoom = this.previousRoom;
    }

}
