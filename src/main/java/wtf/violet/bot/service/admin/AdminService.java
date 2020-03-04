package wtf.violet.bot.service.admin;

import net.dv8tion.jda.api.entities.User;
import wtf.violet.bot.model.Admin;

import java.util.List;

/** @see wtf.violet.bot.service.admin.AdminServiceImpl */
public interface AdminService {

  boolean isAdmin(User user);
  List<Admin> findAll();
  void save(Admin admin);

}
