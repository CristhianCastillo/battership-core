package war.naval.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import war.naval.model.Battlefield;
import war.naval.model.Field;
import war.naval.model.Game;
import war.naval.model.Player;

@Repository
public interface BattlefieldRepository extends JpaRepository<Battlefield, Long> {

	List<Battlefield> findByGameAndPlayer(Game game, Player player);

	Battlefield findByGameAndFieldAndPlayer(Game game, Field field, Player player);

}
