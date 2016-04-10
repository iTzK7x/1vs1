package Revayd;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import Revayd.Commands.Fix;
import Revayd.Commands.Records;
import Revayd.Commands.Revayd;
import Revayd.Commands.Top;
import Revayd.Countdown.LobbyCountdown;
import Revayd.Listener.InventoryClick;
import Revayd.Listener.PlayerChat;
import Revayd.Listener.PlayerDeath;
import Revayd.Listener.PlayerInteract;
import Revayd.Listener.PlayerJoin;
import Revayd.Listener.PlayerQuit;
import Revayd.Listener.Protect;
import Revayd.Listener.ServerPing;
import Revayd.SQL.MySQL;
import Revayd.Util.Color;
import Revayd.Util.FileManager;

public class Main 
extends JavaPlugin
{	
public static HashMap<String, Integer> Rounds = new HashMap<String, Integer>();	
public static HashMap<String, String> VotedFor = new HashMap<String, String>();
public static HashMap<String, Integer> Votes = new HashMap<String, Integer>();	
public static ArrayList<String> IngamePlayers = new ArrayList<String>();
public static ArrayList<String> Voted = new ArrayList<String>();

public static String Prefix()
{
if (FileManager.SettingsConfiguration.getString("Prefix") != null)
{
return Color.color(FileManager.SettingsConfiguration.getString("Prefix").replace("[RevaydSymbol]", "▏"));
}
return "§6Revayd §8▏ §3";
}

public static GameManager GameType;
public static Main Instance = null;

public static boolean NoMove = false;
public static boolean Voting = false;
public static String Map = "";

public void onEnable()
{
Bukkit.getConsoleSender().sendMessage(Main.Prefix() + "§e1vs1 §3has been enabled.");
FileManager.createConfig(); FileManager.createArenasConfig(); FileManager.createSettingsConfig(); FileManager.createMySQLConfig(); 
MySQL.connect(); MySQL.createTable(); Voting = true;

for (String Arena : FileManager.getArenas())
{
Votes.put(Arena, Integer.valueOf(0));
}

Bukkit.getPluginManager().registerEvents(new InventoryClick(), this);
Bukkit.getPluginManager().registerEvents(new PlayerInteract(), this);
Bukkit.getPluginManager().registerEvents(new PlayerDeath(), this);
Bukkit.getPluginManager().registerEvents(new ServerPing(), this);
Bukkit.getPluginManager().registerEvents(new PlayerChat(), this);
Bukkit.getPluginManager().registerEvents(new PlayerQuit(), this);
Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
Bukkit.getPluginManager().registerEvents(new Protect(), this);

getCommand("Fix").setExecutor(new Fix());
getCommand("Top").setExecutor(new Top());
getCommand("1vs1").setExecutor(new Revayd());
getCommand("Stats").setExecutor(new Records());
getCommand("Records").setExecutor(new Records());

GameType = GameManager.LOBBY;
LobbyCountdown.start();
}

public void onDisable()
{
Bukkit.getConsoleSender().sendMessage(Main.Prefix() + "§e1vs1 §3has been disabled.");
MySQL.close();
}

public Main()
{
Instance = this;
}

public static Main getInstance()
{
return Instance;
}
}