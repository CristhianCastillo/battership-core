package war.naval.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import war.naval.model.Player;
import war.naval.repository.PlayerRepository;

@Controller()
public class PlayerController {

	@Autowired
	PlayerRepository playerRepository;

	@ResponseBody
	@PostMapping("/player/create")
	private Player create(Player player) {

		return playerRepository.save(player);

	}

	@ResponseBody
	@PostMapping("/player/update/{idPlayer}")
	private Player update(@RequestParam Long idPlayer, Player player) {

		Player playerToUpdate = null;

		if (playerRepository.findOne(idPlayer) != null) {
			playerRepository.save(player);
		}

		return playerToUpdate;
	}

	@ResponseBody
	@GetMapping("/player/retrieve")
	private Player retrieve(Optional<Long> id, Optional<String> username) {

		Player player = null;
		
		if (id.isPresent()) {
			player = playerRepository.findOne(id.get());
		}else if (id.isPresent()) {
			player = playerRepository.findByUsername(username.get());
		}
		
		return player;

	}

	@ResponseBody
	@DeleteMapping("/player/delete")
	private void remove(Long... ids) {
		
		for (Long idPlayer : ids) {
			playerRepository.delete(idPlayer);
		}

	}

}
