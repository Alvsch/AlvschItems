package me.alvsch.alvschitems.core.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.alvsch.alvschitems.AlvschItems;
import me.alvsch.alvschitems.utils.Utils;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

public class MainCommandTest {

    private static ServerMock server;
    private static PlayerMock player;

    @BeforeAll
    public static void setUp() {
        // Mock the server and plugin
        server = MockBukkit.mock();
        MockBukkit.load(AlvschItems.class);
    }

    @BeforeEach
    public void beforeEach() {
        // Kick all players
        server.setPlayers(0);
        player = server.addPlayer("player");
        player.setOp(true);
    }

    @AfterAll
    public static void tearDown() {
        // Unload the plugin
        MockBukkit.unmock();
    }



    @Test
    public void testMainCommandNotEnoughArguments() {
        server.execute("alvschitems", player).assertFailed();
    }


    @Test
    public void testListCommandSuccess() {
        // Execute the command
        server.execute("alvschitems", player, "list").assertSucceeded();

        // Verify
        Assertions.assertEquals(Utils.color("&aOpening items list"), player.nextMessage());
    }

    @Test
    public void testListCommandPlayerNotFound() {
        server.execute("alvschitems", player, "list", "NonExistent").assertFailed();
    }


    @Test
    public void testGiveCommand() {
        // Execute the command
        server.execute("alvschitems", player, "give", "player", "TEST_ITEM").assertSucceeded();

        // Verify the result
        // System.out.println(player.nextMessage());
        boolean success = Utils.containsSimilarItem(player.getInventory(), AlvschItems.TEST_ITEM);
        Assertions.assertTrue(success);
    }

    @Test
    public void testGiveCommandNotEnoughArguments() {
        server.execute("alvschitems", player, "give", "player").assertFailed();
    }

    @Test
    public void testGiveCommandPlayerNotFound() {
        server.execute("alvschitems", player, "give", "NonExistent").assertFailed();
    }

    @Test
    public void testGiveCommandItemNotFound() {
        server.execute("alvschitems", player, "give", "player", "NonExistent").assertFailed();
    }

    @Test
    public void testGiveCommandWithAmount() {
        server.execute("alvschitems", player, "give", "player", "TEST_ITEM", "10").assertSucceeded();
        boolean success = Utils.containsSimilarItem(player.getInventory(), AlvschItems.TEST_ITEM);
        Assertions.assertTrue(success);
    }


    @Test
    public void testDebugCommand() {
        player.setItemInHand(AlvschItems.TEST_ITEM);

        // Execute the command
        server.execute("alvschitems", player, "debug", "item").assertSucceeded();

        // Verify the result
        Assertions.assertEquals("ID: TEST_ITEM", player.nextMessage());
        Assertions.assertEquals("Name: Test Item", player.nextMessage());
        Assertions.assertEquals("Rarity: TEST", player.nextMessage());
    }

    @Test
    public void testDebugCommandNotEnoughArguments() {
        server.execute("alvschitems", player, "debug").assertFailed();
    }


    @Test
    public void testReloadCommand() {
        server.execute("alvschitems", player, "reload").assertSucceeded();

        Assertions.assertEquals("Reloading config.yml", player.nextMessage());
        Assertions.assertEquals("Reloaded config.yml", player.nextMessage());

    }



    @Test
    public void testOneArgumentTabComplete() {
        List<String> list = server.getCommandTabComplete(player, "alvschitems ");
        Assertions.assertTrue(list.containsAll(List.of("give", "list", "debug", "reload")));
    }


    @Test
    public void testTwoArgumentTabCompleteList() {
        List<String> list = server.getCommandTabComplete(player, "alvschitems list ");
        List<String> onlinePlayersList = new ArrayList<>();
        server.getOnlinePlayers().forEach(p -> onlinePlayersList.add(p.getName()));
        Assertions.assertEquals(onlinePlayersList, list);
    }


    @Test
    public void testTwoArgumentTabCompleteGive() {
        List<String> list = server.getCommandTabComplete(player, "alvschitems give ");
        List<String> onlinePlayersList = new ArrayList<>();
        server.getOnlinePlayers().forEach(p -> onlinePlayersList.add(p.getName()));
        Assertions.assertEquals(onlinePlayersList, list);
    }

    @Test
    public void testThreeArgumentTabCompleteGive() {
        List<String> list = server.getCommandTabComplete(player, "alvschitems give player ");
        Assertions.assertEquals(List.of("TEST_ITEM"), list);
    }


    @Test
    public void testTwoArgumentTabCompleteDebug() {
        List<String> list = server.getCommandTabComplete(player, "alvschitems debug ");
        Assertions.assertEquals(List.of("item"), list);
    }

}
