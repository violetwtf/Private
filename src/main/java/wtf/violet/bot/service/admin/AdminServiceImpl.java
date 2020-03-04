package wtf.violet.bot.service.admin;

import net.dv8tion.jda.api.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wtf.violet.bot.model.Admin;
import wtf.violet.bot.repository.AdminRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Definition of the Admin service.
 * @author Violet M. vi@violet.wtf
 */
@Service
public class AdminServiceImpl implements AdminService {

  private List<Long> cache = new ArrayList<>();

  @Autowired
  private AdminRepository repository;

  /**
   * Returns true if the provided User is admin.
   * @param user JDA User to check
   */
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

  /**
   * Find all admins.
   * @return A List of all admins.
   */
  @Override
  public List<Admin> findAll() {
    List<Admin> admins = repository.findAll();
    for (Admin admin : admins) {
      cache.add(admin.getDiscordId());
    }
    return admins;
  }

  /**
   * Save an admin to the repository (and cache).
   * @param admin The repository to save
   */
  @Override
  public void save(Admin admin) {
    long adminId = admin.getDiscordId();

    if (!cache.contains(adminId)) {
      cache.add(adminId);
    }

    repository.save(admin);
  }

  /**
   * Returns the underlying AdminRepository.
   * @see wtf.violet.bot.repository.AdminRepository
   */
  public AdminRepository getRepository() {
    return repository;
  }

  /** Returns the underlying cache. You probably shouldn't touch this. */
  public List<Long> getCache() {
    return cache;
  }
}
