package war.naval.controller;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import war.naval.model.Game;
import war.naval.model.Player;
import war.naval.repository.BattlefieldRepository;
import war.naval.repository.FieldRepository;
import war.naval.repository.GameRepository;
import war.naval.repository.PlayerRepository;
import war.naval.util.MessageResponse;

@Controller()
public class GamerController {

	@Autowired
	GameRepository gameRepository;

	@Autowired
	BattlefieldRepository battlefieldRepository;

	@Autowired
	FieldRepository fieldRepository;

	@Autowired
	PlayerRepository playerRepository;

	@ResponseBody
	@PostMapping("game/create")
	private MessageResponse create(String username) {
		try {
			Game game = null;
			Player player = playerRepository.findByUsername(username);
			if (player == null) {
				player = new Player();
				player.setUsername(username);
				player = playerRepository.save(player);
			}

			List<Game> games = gameRepository.findByOpponentIsNullOrderByCreationAsc();
			if (games == null || games.isEmpty()) {
				game = new Game();
				game.setCreation(new Timestamp(System.currentTimeMillis()));
				game.setPlayer(player);
				game.setStart(player);
			} else {
				game = games.get(0);
				if (game.getOpponent() == null && game.getPlayer().getId() != player.getId()) {
					game.setOpponent(player);
					game.setTurn(player);
				}
			}
			gameRepository.save(game);
			return new MessageResponse("OK", game);
		} catch (Exception e) {
			e.printStackTrace();
			return new MessageResponse("ERROR", e.getMessage());
		}
	}

}
