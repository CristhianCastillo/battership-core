package war.naval.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import war.naval.dto.ShipDto;
import war.naval.model.Battlefield;
import war.naval.model.Field;
import war.naval.model.Game;
import war.naval.model.Player;
import war.naval.repository.BattlefieldRepository;
import war.naval.repository.FieldRepository;
import war.naval.repository.GameRepository;
import war.naval.repository.PlayerRepository;
import war.naval.util.MessageResponse;

@Controller()
public class BattlefieldController {

	@Autowired
	BattlefieldRepository battlefieldRepository;

	@Autowired
	FieldRepository fieldRepository;

	@Autowired
	GameRepository gameRepository;

	@Autowired
	PlayerRepository playerRepository;

	@ResponseBody
	@PostMapping("/battlefield/create")
	private MessageResponse sendBattefield(@RequestParam Long idGame, @RequestParam String username,
			@RequestBody List<ShipDto> ships) {
		try {
			validateShips(ships);
			List<Battlefield> battlefields = new ArrayList<>();
			Game game = gameRepository.findOne(idGame);
			if (game == null || game.getId() == null)
				throw new Exception("El juego no existe!!");
			Player player = playerRepository.findByUsername(username);
			if (player == null || player.getId() == null)
				throw new Exception("El jugador no existe!!");

			for (ShipDto ship : ships) {
				Field field = fieldRepository.findByXAndY(ship.getX(), ship.getY());
				if (field == null)
					throw new Exception("El punto de disparo no existe!!!");
				Battlefield battlefield = battlefieldRepository.findByGameAndFieldAndPlayer(game, field, player);
				if (battlefield == null) {
					battlefield = new Battlefield();
					battlefield.setGame(game);
					battlefield.setField(field);
					battlefield.setShip(ship.isShip());
					battlefield.setPlayer(player);
					battlefieldRepository.save(battlefield);
				}
			}

			battlefields = battlefieldRepository.findByGameAndPlayer(game, player);
			return new MessageResponse("OK", battlefields);
		} catch (Exception e) {
			e.printStackTrace();
			return new MessageResponse("EROOR", e.getMessage());
		}
	}

	@ResponseBody
	@GetMapping("/battlefield/attack")
	private MessageResponse sendAttack(Long idGame, String username, int x, int y) {
		try {
			Game game = gameRepository.findOne(idGame);
			if (game == null || game.getId() == null)
				throw new Exception("El juego no existe!!!");
			if(game.getWon() != null && game.getWon().getId() != null)
				throw new Exception("El juego ya fue ganado por el jugador: " + game.getWon().getUsername());
			Player turnPlayer = game.getTurn();
			if (turnPlayer == null || turnPlayer.getId() == null)
				throw new Exception("Este juego no tiene un turno asignado!");
			Player player = playerRepository.findByUsername(username);
			if (player == null || player.getId() == null)
				throw new Exception("El jugador no existe");
			Player opponent = null;
			if (game.getPlayer() == player) {
				opponent = game.getOpponent();
			} else if (game.getOpponent() == player) {
				opponent = game.getPlayer();
			}
			Field field = fieldRepository.findByXAndY(x, y);
			if (field == null)
				throw new Exception("La coordenada no existe!! Otro Planeta!!");
			Battlefield battlefield = battlefieldRepository.findByGameAndFieldAndPlayer(game, field, opponent);
			if (battlefield == null || battlefield.getId() == null)
				throw new Exception("El campo de juego no existe!!!");
			if (battlefield.getImpact() != null && battlefield.getImpact()) {
				throw new Exception("La posición ya fue impactada!!");
			}

			battlefield.setImpact(true);
			battlefieldRepository.save(battlefield);
			game.setTurn(opponent);
			game = gameRepository.save(game);

			// know if player win!! --> search player enemy, with game, with ship eq true
			// if impact for all is true --- ypu win!!

			List<Battlefield> listOponnet = battlefieldRepository.findByGameAndPlayer(game, opponent);
			List<Battlefield> listOponnetWithShips = new ArrayList<Battlefield>();
			for (int i = 0; i < listOponnet.size(); i++) {
				Battlefield fieldTemp = listOponnet.get(i);
				if (fieldTemp.getShip())
					listOponnetWithShips.add(fieldTemp);
			}

			boolean youWin = true;
			for (int i = 0; i < listOponnetWithShips.size(); i++) {
				Battlefield fieldTemp = listOponnetWithShips.get(i);
				if (fieldTemp.getImpact() == null || !fieldTemp.getImpact()) {
					youWin = false;
					break;
				}
			}
			
			if(youWin) {
				game.setWon(player);
				gameRepository.save(game);
			}

			Map<String, Object> bodyCustom = new HashMap<String, Object>();
			bodyCustom.put("win", youWin);
			bodyCustom.put("impact", battlefield.getShip());
			return new MessageResponse("OK", bodyCustom);
		} catch (Exception e) {
			e.printStackTrace();
			return new MessageResponse("EROOR", e.getMessage());
		}
	}

	@ResponseBody
	@GetMapping("/battlefield/fields")
	private MessageResponse fields() {
		try {
			List<Field> fields = fieldRepository.findAll();
			List<ShipDto> ships = new ArrayList<>();
			for (Field field : fields) {
				ships.add(new ShipDto(field.getX(), field.getY(), false));
			}
			return new MessageResponse("OK", ships);
		} catch (Exception e) {
			e.printStackTrace();
			return new MessageResponse("EROOR", e.getMessage());
		}
	}

	private void validateShips(List<ShipDto> ships) throws Exception {

		if (ships == null || ships.isEmpty() || ships.size() > 100 || ships.size() < 100) {
			throw new Exception("Los barcos son obligatorios y no pueden haber mas de 100 campos");
		}

		int count = 0;
		for (ShipDto shipDto : ships) {
			if (shipDto.isShip()) {
				count++;
			}
		}

//		if (count > 20 || count < 20) {
//			throw new Exception("Los barcos son obligatorios. Deben ser 20.");
//		}

		if (count > 2 || count < 2) {
			throw new Exception("Los barcos son obligatorios. Deben ser 2.");
		}

	}

	public static void print2D(boolean mat[][]) {
		// Loop through all rows
		for (int i = 0; i < mat.length; i++) {
			System.out.println();
			// Loop through all elements of current row
			for (int j = 0; j < mat[i].length; j++) {
				boolean result = mat[i][j];
				if (result) {
					System.out.print(" [1] ");
				} else {
					System.out.print(" [0] ");
				}

			}
		}
	}

}
