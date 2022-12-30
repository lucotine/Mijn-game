import java.security.Key;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Game
{
    private Parser parser;
    private Player player;
    private Item key = new Item("Key", "Key to go trought bossrooms", 5);
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        player = new Player("Lucotine", 20, key);
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room spawnLVL1, basisForest, darkForest, dangerousMountain, bossLVL1, spicyDungeon,  spawnLVL2, waterDesert, waterSafari, safariDesert, bossLVL2,  spawnLVL3, redZone, lavaDesert, lavaCastle, finalBoss, theEnd;
        Item mastersword, triforce, keyWorld2;
      
        // create the rooms
        spawnLVL1 = new Room("the Spawn of the world lvl1", RoomType.START);
            triforce = new Item("triforce", "beautifull triangle of the world",  50);
            spawnLVL1.addItem(triforce);
            triforce.setMoveable(false);

        basisForest = new Room("the basis forest", RoomType.FOREST);
            mastersword = new Item("mastersword", "big big iron blade with a triangle", 15.6);
            basisForest.addItem(mastersword);
            keyWorld2 = new Item("keyworld2", " key for world2", 5);
            basisForest.addItem(keyWorld2);

        darkForest = new Room("the dark forest", RoomType.FOREST);

        spicyDungeon = new Room("in the Spiciest dungeon from Hyrule", RoomType.DUNGEON);
            spicyDungeon.addItem(new Item("rum", "a barrel of rum", 10.7));
            spicyDungeon.addItem(new Item("water", "a crate bottles of water", 12.2));

        dangerousMountain = new Room("the big and dangerous mountain", RoomType.MOUNTAIN);

        bossLVL1 = new Room("boss room of lvl 1", RoomType.KEYDOOR);

        spawnLVL2 = new Room("the Spawn of the world lvl2", RoomType.START);

        waterSafari = new Room("a safari full of water", RoomType.DESERTZONE);

        waterDesert = new Room("the desert with water somewhere", RoomType.DESERTZONE);

        safariDesert = new Room("a safari with desert", RoomType.DESERTZONE);

        bossLVL2 = new Room("boss room of lvl 2", RoomType.KEYDOOR);

        spawnLVL3 = new Room("the Spawn of the world lvl3", RoomType.START);

        redZone = new Room("the most dangerous zone", RoomType.LAVAZONE);

        lavaDesert = new Room("a lava zone to be carefully", RoomType.LAVAZONE);

        lavaCastle = new Room("the castle of lava where the boss reside", RoomType.LAVAZONE);

        finalBoss = new Room("the final boss room", RoomType.KEYDOOR);

        theEnd = new Room("the end room thank you for playing you can now leave the game with 'exit' ", RoomType.FINISH);




        // LVL1
        spawnLVL1.setExit("basisforest", basisForest);
        basisForest.setExit("spawn", spawnLVL1);

        basisForest.setExit("dangerousmountain", dangerousMountain);
        dangerousMountain.setExit("basisforest", basisForest);

        dangerousMountain.setExit("bossroom", bossLVL1);
        bossLVL1.setExit("dangerousmountain", dangerousMountain);

        spawnLVL1.setExit("darkforest", darkForest);
        darkForest.setExit("spawn", spawnLVL1);

        darkForest.setExit("down", spicyDungeon);
        spicyDungeon.setExit("up", darkForest);

        bossLVL1.setExit("worldlvl2", spawnLVL2);

        //LVL2
        spawnLVL2.setExit("waterdesert", waterDesert);
        waterDesert.setExit("spawnlvl2", spawnLVL2);

        spawnLVL2.setExit("watersafari-left", waterSafari);
        spawnLVL2.setExit("watersafari-right", waterSafari);
        waterSafari.setExit("spawnlvl2", spawnLVL2);

        waterSafari.setExit("safaridesert", safariDesert);
        safariDesert.setExit("watersafari", waterSafari);

        safariDesert.setExit("bossroom", bossLVL2);
        bossLVL2.setExit("safaridesert", safariDesert);

        bossLVL2.setExit("worldlvl3", spawnLVL3);

        //LVL3
        spawnLVL3.setExit("lavadesert", lavaDesert);
        lavaDesert.setExit("spawnlvl3", spawnLVL3);

        spawnLVL3.setExit("redzone", redZone);
        redZone.setExit("spawnlvl3", spawnLVL3);

        lavaDesert.setExit("lavacastle", lavaCastle);
        lavaCastle.setExit("lavadesert", lavaDesert);

        lavaCastle.setExit("finalbossroom", finalBoss);
        finalBoss.setExit("lavacastle", lavaCastle);

        finalBoss.setExit("ENDGAME", theEnd);



        player.setCurrentRoom(spawnLVL1);
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the world of Hyrule");
        System.out.println("Type 'help' if you need help. :)");
        System.out.println();
        printLocationInfo();
    }

    private void printLocationInfo() {
        System.out.println("current floor: ");
        System.out.println(player.getCurrentRoom().getLongDescription());
        System.out.println(player.getBagDescription());
        System.out.println();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        CommandWord commandWord = command.getCommandWord();
        switch (commandWord) {
            case HELP:
                printHelp();
                break;
            case GO:
                goRoom(command);
                break;
            case TAKE:
                take(command);
                break;
            case DROP:
                drop(command);
                break;
            case LOOK:
                look();
                break;
            case EAT:
                eat();
                break;
            case QUIT:
                wantToQuit = quit(command);
                break;
            default:
        }

        return wantToQuit;
    }

    private void take(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }

        int result = player.take(command.getSecondWord());
        if (result==Player.ITEM_GONE) {
            printLocationInfo();
        } else {
            if (result==Player.ITEM_NOTMOVEABLE) {
                System.out.println("Item " + command.getSecondWord() + " is not moveable");
            } else {
                System.out.println("Can't find " + command.getSecondWord());
            }

        }
    }
    private void drop(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }

        int result = player.drop(command.getSecondWord());
        if (result == Player.ITEM_DROPPED)
            printLocationInfo();
        else if (result == Player.ITEM_NOTPRESENT){
            System.out.println("Item " + command.getSecondWord() + " is not in bag");
        }

    }


    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp()
    {
        System.out.println(player.getName() + " is lost. " + player.getName() + " is alone. He wanders");
        System.out.println("around the area looking for some places.");
        System.out.println();
        System.out.println("His command words are:");
        System.out.println("   " + parser.showCommands());
    }

    private void look() {
        System.out.println(player.getName() + " is " + player.getCurrentRoom().getLongDescription());
    }

    private void eat() {
        System.out.println(player.getName() + " has eaten and is not hungry anymore");
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (direction.equals("back")){
            player.goBack();
            printLocationInfo();
            return;
        }

        //can also use else if instead of return;
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            player.setCurrentRoom(nextRoom);
            printLocationInfo();
        }

    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }




    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
