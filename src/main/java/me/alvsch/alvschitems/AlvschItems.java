package me.alvsch.alvschitems;

import lombok.Getter;
import me.alvsch.alvschitems.api.Rarity;
import me.alvsch.alvschitems.api.item.BaseItem;
import me.alvsch.alvschitems.api.item.CustomItemStack;
import me.alvsch.alvschitems.core.AlvschRegistry;
import me.alvsch.alvschitems.core.CooldownManager;
import me.alvsch.alvschitems.core.commands.Craft;
import me.alvsch.alvschitems.core.commands.MainCommand;
import me.alvsch.alvschitems.core.services.CustomItemDataService;
import me.alvsch.alvschitems.core.task.TaskHandler;
import me.alvsch.alvschitems.implementation.listeners.CraftListener;
import me.alvsch.alvschitems.implementation.listeners.EventListener;
import me.alvsch.alvschitems.implementation.listeners.PlayerJoinListener;
import me.alvsch.alvschitems.implementation.setup.RecipeSetup;
import me.alvsch.alvschitems.utils.Utils;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.File;
import java.io.IOException;

public final class AlvschItems extends JavaPlugin {

	@Getter
	private static AlvschItems instance;

	private final AlvschRegistry registry = new AlvschRegistry(this);
	@Getter
	private final CooldownManager cooldownManager = new CooldownManager(this);
	@Getter
	private final TaskHandler taskHandler = new TaskHandler();

	private final CustomItemDataService customItemDataService = new CustomItemDataService(this, "alvsch_item");

	@Getter
	private FileConfiguration config;

	private FileConfiguration permissions;
	private FileConfiguration messages;

	public static CustomItemStack TEST_ITEM = new CustomItemStack("TEST_ITEM", Material.STICK);

	public AlvschItems() {
		super();
	}

	/**
	 * This constructor is invoked in Unit Test environments only.
	 *
	 * @param loader
	 *            Our {@link JavaPluginLoader}
	 * @param description
	 *            A {@link PluginDescriptionFile}
	 * @param dataFolder
	 *            The data folder
	 * @param file
	 *            A {@link File} for this {@link Plugin}
	 */
	@ParametersAreNonnullByDefault
	public AlvschItems(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
		super(loader, description, dataFolder, file);
	}


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

		Logger.log(Logger.LogLevel.INFO, "Loading files...");
		createFiles();
		Logger.log(Logger.LogLevel.INFO, "Files are loaded!");

		new BaseItem("TEST_ITEM", "Test Item", TEST_ITEM, Rarity.TEST, null).register();
		Logger.log(Logger.LogLevel.INFO, "Recipes are loading!");
		RecipeSetup.setup();
		Logger.log(Logger.LogLevel.INFO, "Recipes are loaded!");

		Logger.log(Logger.LogLevel.SUCCESS, "Plugin is loaded!");
		Logger.log(Logger.LogLevel.OUTLINE, "********************");
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
		getServer().getScheduler().cancelTasks(this);

	}

	/**
	 * Register plugin commands
	 */
	@SuppressWarnings("ConstantConditions")
	private void registerCommands() {
		getCommand("alvschitems").setExecutor(new MainCommand(this));
		getCommand("alvschitems").setTabCompleter(new MainCommand(this));

		getCommand("craft").setExecutor(new Craft());
	}

	/**
	 * Register event listeners
	 */
	private void registerListeners() {
		getServer().getPluginManager().registerEvents(new CraftListener(), this);

		getServer().getPluginManager().registerEvents(new EventListener(), this);

		getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);

	}

	@SuppressWarnings("all")
	private void loadFile(File file, String resourcePath) {
		if(!file.exists()) {
			file.getParentFile().mkdirs();
			saveResource(resourcePath, false);
		}
	}

	/**
	 * Create and load config files
	 * @return If the files was created and loaded successfully
	 */
	public boolean createFiles() {
		File configf = new File(getDataFolder(), "config.yml");
		loadFile(configf, "config.yml");
		File permissionsf = new File(getDataFolder(), "permissions.yml");
		loadFile(permissionsf, "permissions.yml");
		File messagesf = new File(getDataFolder(), "messages.yml");
		loadFile(messagesf, "messages.yml");

		config = new YamlConfiguration();
		permissions = new YamlConfiguration();
		messages = new YamlConfiguration();
		try {
			config.load(configf);
			permissions.load(permissionsf);
			messages.load(messagesf);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public static AlvschRegistry getRegistry() {
		return instance.registry;
	}

	public static CustomItemDataService getCustomItemDataService() {
		return instance.customItemDataService;
	}


	public String getPermission(String key) {
		String permission = permissions.getString(key);
		if(permission == null) throw new NullPointerException("Permission not found");
		return permission;
	}

	public String getMessage(String key) {
		String message = messages.getString(key);
		if(message == null) message = "Message not found: " + key;
		return Utils.color(message);
	}

}
