package me.balbucio.plugins;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Main extends Plugin{
	private static Main instance;
	private File folder = new File("plugins/balbBungeePlugins");
	private File msgs = new File(folder, "messages.yml");
	
	public Configuration messages;
	
	public void onEnable() {
		setInstance(this);	
		loadConfig();
		BungeeCord.getInstance().pluginManager.registerCommand(this, new Plugins());
		BungeeCord.getInstance().getConsole().sendMessage(new TextComponent("§f[BalbucioBungeePlugins] §2Ativado com sucesso!"));
	}
    public static Main getInstance() {
        return instance;
    }

    private static void setInstance(final Main instance) {
        Main.instance = instance;
    }
	public void loadConfig() {
		try {
			if (!folder.exists()) {
				folder.mkdir();
			}

			if (!msgs.exists()) {
				Files.copy(this.getResourceAsStream("messages.yml"), msgs.toPath());
			}
			messages = YamlConfiguration.getProvider(YamlConfiguration.class).load(msgs);
		} catch (Exception e) {
			e.printStackTrace();
			BungeeCord.getInstance().getConsole()
					.sendMessage("§c[BalbucioBungeePlugins] §aNão foi possível carregar os arquivos!");
		}
	}
}
