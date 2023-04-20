package me.balbucio.plugins;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

public class Plugins extends Command {

	private Main m = Main.getInstance();

	public Plugins() {
		super("bungeeplugins");
	}

	public void execute(CommandSender sender, String[] args) {
		String prefix = m.messages.getString("prefix").replace("&", "§");
		if (!sender.hasPermission("bplugins.use")) {
			return;
		}

		if (args.length == 0) {
			TextComponent message = new TextComponent(m.messages.getString("defaultmessage").replace("&", "§")
					.replace("{prefix}", prefix).replace("{plugins}", ""));

			for (Plugin pl : ProxyServer.getInstance().getPluginManager().getPlugins()) {
				TextComponent comp = new TextComponent(" " + pl.getDescription().getName());
				String ativo = ProxyServer.getInstance().getPluginManager()
						.getPlugin(pl.getDescription().getName()) != null
								? m.messages.getString("statusativo").replace("&", "§")
								: m.messages.getString("statusdesativo").replace("&", "§");
				comp.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(m.messages
						.getString("pluginhover").replace("&", "§")
						.replace("{description}",
								pl.getDescription().getDescription() != null ? pl.getDescription().getDescription()
										: "Sem descrição!")
						.replace("{author}",
								pl.getDescription().getAuthor() != null ? pl.getDescription().getAuthor()
										: "Sem autor!")
						.replace("{file}", pl.getFile().getAbsolutePath()).replace("{status}", ativo).replace(
								"{version}", pl.getDescription().getVersion() != null ? pl.getDescription().getVersion()
										: "Sem versão!")).create()));
				message.addExtra(comp);
			}
			sender.sendMessage(message);
			return;
		}

		String arg = args[0];
		if (arg.equalsIgnoreCase("help") || arg.equalsIgnoreCase("ajuda")) {
			sender.sendMessage("§a§lbalbBungeePlugins §7- Comandos:");
			sender.sendMessage("§7Criado por Sr_Balbucio! - v1.1");
			sender.sendMessage("§b/bungeeplugins §7- Mostra os plugins do Proxy");
			sender.sendMessage("§b/bungeeplugins reload §7- Recarrega o plugin");
		} else if (arg.equalsIgnoreCase("reload")) {
			m.loadConfig();
			sender.sendMessage("§aPlugin recarregado!");
		} else {
			sender.sendMessage(
					Main.getInstance().messages.getString("notfound").replace("&", "§").replace("{prefix}", prefix));
		}
	}
}
