package wtf.violet.bot.service.admin;

import net.dv8tion.jda.api.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wtf.violet.bot.model.Admin;
import wtf.violet.bot.repository.AdminRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

  private List<Long> cache = new ArrayList<>();

  @Autowired
  private AdminRepository repository;

  @Override
  public boolean isAdmin(User user) {
    long id = user.getIdLong();

    if (cache.contains(id)) {
      return true;
    }

    Admin admin = repository.findOneByDiscordId(id);
    boolean isAdmin = admin != null;

    if (isAdmin) {
      cache.add(id);
    }

    return isAdmin;
  }

  @Override
  public List<Admin> findAll() {
    List<Admin> admins = repository.findAll();
    for (Admin admin : admins) {
      cache.add(admin.getDiscordId());
    }
    return admins;
  }

  @Override
  public void save(Admin admin) {
    long adminId = admin.getDiscordId();

    if (!cache.contains(adminId)) {
      cache.add(adminId);
    }

    repository.save(admin);
  }

  public AdminRepository getRepository() {
    return repository;
  }

  public List<Long> getCache() {
    return cache;
  }
}
