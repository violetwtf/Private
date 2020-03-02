package wtf.violet.bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wtf.violet.bot.model.Admin;

import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {

  Admin findOneByDiscordId(long discordId);

}
