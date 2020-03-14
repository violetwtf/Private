/*
 *     Private - Discord bot that protects your server
 *     Copyright (C) 2020  Violet M. <vi@violet.wtf>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package wtf.violet.bot.service.admin;

import lombok.Getter;
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
public final class AdminServiceImpl implements AdminService {

  @Getter private List<Long> cache = new ArrayList<>();

  @Autowired @Getter private AdminRepository repository;

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

}
