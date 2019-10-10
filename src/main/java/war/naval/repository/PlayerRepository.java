package war.naval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import war.naval.model.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

	Player findByUsername(String username);

}
