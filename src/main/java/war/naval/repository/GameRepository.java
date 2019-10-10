package war.naval.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import war.naval.model.Game;
import war.naval.model.Player;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

	Game findByStart(Player player);

	Game findByTurn(Player player);

	List<Game> findByOpponentIsNullOrderByCreationAsc();

}
