package wtf.violet.bot;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import wtf.violet.bot.command.CommandManager;
import wtf.violet.bot.command.HelpCommand;
import wtf.violet.bot.command.eval.EvalCommand;
import wtf.violet.bot.command.ping.PingCommand;
import wtf.violet.bot.listener.MessageListener;
import wtf.violet.bot.model.Admin;
import wtf.violet.bot.repository.AdminRepository;
import wtf.violet.bot.service.admin.AdminService;
import wtf.violet.bot.service.guildsettings.GuildSettingsServiceImpl;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

@Service
@EnableAdminServer
public class Bot implements BotService {

  private static Bot instance;

  @Autowired
  private GuildSettingsServiceImpl guildSettingsService;
  @Autowired
  private AdminService adminService;

  private UUID adminCode;
  private boolean adminCodeClaimable = false;

  private CommandManager commandManager = new CommandManager();

  public Bot() throws LoginException {
    instance = this;

    CommandManager.register(new PingCommand());
    CommandManager.register(new EvalCommand());
    CommandManager.register(new HelpCommand());

    new JDABuilder()
        .setToken(System.getenv("DISCORD_TOKEN"))
        // Disable all cache flags
        .setDisabledCacheFlags(EnumSet.allOf(CacheFlag.class))
        .setActivity(Activity.watching("your privacy rights!"))
        .addEventListeners(new MessageListener())
        .build();
  }

  @EventListener(ApplicationReadyEvent.class)
  public void onReady() {
    List<Admin> admin = adminService.findAll();
    if (admin.size() == 0) {
      // I know it's not super secure but it's only run once ever
      adminCode = UUID.randomUUID();
      adminCodeClaimable = true;
      System.out.println("Send this code to become admin: " + adminCode);
    }
  }

  public static Bot getInstance() {
    return instance;
  }

  public GuildSettingsServiceImpl getGuildSettingsService() {
    return guildSettingsService;
  }

  public CommandManager getCommandManager() {
    return commandManager;
  }

  public AdminService getAdminService() {
    return adminService;
  }

  public UUID getAdminCode() {
    return adminCode;
  }

  public boolean isAdminCodeClaimable() {
    return adminCodeClaimable;
  }

  public void setAdminCodeClaimable(boolean adminCodeClaimable) {
    this.adminCodeClaimable = adminCodeClaimable;
  }

}
