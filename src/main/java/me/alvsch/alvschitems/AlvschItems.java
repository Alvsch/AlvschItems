package me.alvsch.alvschitems;

import me.alvsch.alvschitems.core.AlvschRegistry;
import me.alvsch.alvschitems.core.CooldownManager;
import me.alvsch.alvschitems.core.commands.Craft;
import me.alvsch.alvschitems.core.commands.MainCommand;
import me.alvsch.alvschitems.core.scoreboard.CustomScoreboard;
import me.alvsch.alvschitems.implementation.listeners.EventListener;
import me.alvsch.alvschitems.implementation.listeners.CraftListener;
import me.alvsch.alvschitems.implementation.setup.AItemSetup;
import me.alvsch.alvschitems.implementation.setup.ARecipeSetup;
import org.bukkit.plugin.java.JavaPlugin;

public final class AlvschItems extends JavaPlugin {

	private static AlvschItems instance;
	private final AlvschRegistry registry = new AlvschRegistry(this);
	private final CooldownManager cooldownManager = new CooldownManager(this);

	@Override
	public void onEnable() {
		// Plugin startup logic

		Logger.log(Logger.LogLevel.OUTLINE, "********************");
		Logger.log(Logger.LogLevel.INFO, "Plugin is loading...");
		instance = this;

		Logger.log(Logger.LogLevel.INFO, "Commands are loading...");
		registerCommands();
		Logger.log(Logger.LogLevel.INFO, "Commands are loaded!");

		Logger.log(Logger.LogLevel.INFO, "Events are loading...");
		registerListeners();
		Logger.log(Logger.LogLevel.INFO, "Events are loaded!");

		Logger.log(Logger.LogLevel.INFO, "Items are loading!");
		AItemSetup.setup();
		ARecipeSetup.setup();
		Logger.log(Logger.LogLevel.INFO, "Items are loaded!");


		Logger.log(Logger.LogLevel.SUCCESS, "Plugin is loaded!");
		Logger.log(Logger.LogLevel.OUTLINE, "********************");

		new CustomScoreboard();
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
	}

	@SuppressWarnings("ConstantConditions")
	private void registerCommands() {
		getCommand("alvschitems").setExecutor(new MainCommand());
		getCommand("alvschitems").setTabCompleter(new MainCommand());

		getCommand("craft").setExecutor(new Craft());
	}
	private void registerListeners() {
		getServer().getPluginManager().registerEvents(new EventListener(), this);
		getServer().getPluginManager().registerEvents(new CraftListener(), this);
	}

	public static AlvschItems getInstance() {
		return instance;
	}
	public AlvschRegistry getRegistry() {
		return registry;
	}
	public CooldownManager getCooldownManager() {
		return cooldownManager;
	}



}
