package me.alvsch.alvschitems.core.commands;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.alvsch.alvschitems.AlvschItems;
import me.alvsch.alvschitems.utils.Utils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MainCommandTest {

    private static ServerMock server;

    @BeforeAll
    public static void setUp() {
        // Mock the server and plugin
        server = MockBukkit.mock();
        MockBukkit.load(AlvschItems.class);
    }

    @AfterAll
    public static void tearDown() {
        // Unload the plugin
        MockBukkit.unmock();
    }

    @Test
    public void testListCommand() {
        PlayerMock player = server.addPlayer();
        player.setOp(true);

        // Execute the command
        server.execute("items", player, "list").assertSucceeded();

        // Verify
        System.out.println(player.nextMessage());
    }

    @Test
    public void testGiveCommand() {
        PlayerMock player = server.addPlayer("player");
        player.setOp(true);

        // Execute the command
        server.execute("alvschitems", player, "give", "player", "TEST_ITEM").assertSucceeded();

        // Verify the result

        System.out.println(player.nextMessage());
        Assertions.assertTrue(Utils.containsSimilarItem(player.getInventory(), AlvschItems.TEST_ITEM));
    }

    @Test
    public void testDebugCommand() {
        PlayerMock player = server.addPlayer("player");
        player.setOp(true);

        player.setItemInHand(AlvschItems.TEST_ITEM);

        // Execute the command
        server.execute("alvschitems", player, "debug", "item").assertSucceeded();

        // Verify the result
        System.out.println(player.nextMessage());
        System.out.println(player.nextMessage());
        System.out.println(player.nextMessage());
    }

    @Test
    public void testReloadCommand() {
        PlayerMock player = server.addPlayer();
        player.setOp(true);

        server.execute("alvschitems", player, "reload").assertSucceeded();

        Assertions.assertEquals("Reloading config.yml", player.nextMessage());
        Assertions.assertEquals("Reloaded config.yml", player.nextMessage());

    }
}
