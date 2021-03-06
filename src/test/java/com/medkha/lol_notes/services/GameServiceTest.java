package com.medkha.lol_notes.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.medkha.lol_notes.dto.ChampionEssentielsDto;
import com.medkha.lol_notes.entities.Champion;
import com.medkha.lol_notes.entities.Game;
import com.medkha.lol_notes.entities.Role;
import com.medkha.lol_notes.exceptions.NoElementFoundException;
import com.medkha.lol_notes.repositories.GameRepository;
import com.medkha.lol_notes.services.impl.GameServiceImpl;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class GameServiceTest {
	private GameService gameService;
	private ChampionService championServiceMock;
	private RoleAndLaneService roleAndLaneServiceMock;
	private GameRepository gameRepositoryMock;

	@BeforeEach
	public void setupMock(){
		gameRepositoryMock = mock(GameRepository.class);
		championServiceMock = mock(ChampionService.class);
		roleAndLaneServiceMock = mock(RoleAndLaneService.class);
		gameService = new GameServiceImpl(gameRepositoryMock, championServiceMock, roleAndLaneServiceMock);
	}
	@Test
	public void shouldCreateGame() {
		Game game = new Game(10, "solo", "midlane");
		game.setId((long)1);
		Game result = Game.copy(game);
		result.setId((long)1);

		ChampionEssentielsDto championGotById = new ChampionEssentielsDto(game.getChampionId(), "testChamp");
		
		when(this.gameRepositoryMock.save(game)).thenReturn(result);
		when(this.championServiceMock.getChampionById(10)).thenReturn(championGotById);
		when(this.roleAndLaneServiceMock.isLane(game.getLaneName())).thenReturn(true);
		when(this.roleAndLaneServiceMock.isRole(game.getRoleName())).thenReturn(true);
		assertEquals(result, this.gameService.createGame(game));
	}
	
	@Test
	public void shouldThrowIllegalArgumentException_when_GameIsNull() {
		assertThrows(IllegalArgumentException.class, () -> { 
			this.gameService.createGame(null); 
		}); 
	}
	
	@Test 
	public void shouldGetGameById() {
		Game existingGame = new Game(10, "solo", "midlane");
		existingGame.setId((long)1);
		when(this.gameRepositoryMock.findById(existingGame.getId())).thenReturn(Optional.of(existingGame));
		assertEquals(existingGame, this.gameService.findById(existingGame.getId()));
	}
	
	@Test 
	void shouldThrowIllegalArgumentException_when_IdIsNull_findById() { 
		
		when(this.gameRepositoryMock.findById(null)).thenThrow(InvalidDataAccessApiUsageException.class); 
		
		assertThrows(IllegalArgumentException.class, () -> { 
			this.gameService.findById(null); 
		});
	}
	
	@Test 
	void shouldThrownNoElementFoundException_When_IdNotFoundInDb_findById() { 
		Long id = (long) 1 ;
		
		when(this.gameRepositoryMock.findById(id)).thenReturn(Optional.empty()); 
		
		assertThrows(NoElementFoundException.class, () -> { 
			this.gameService.findById(id); 
		}); 
	}
	
	@Test 
	void shouldUpdateGame() {
		Game existingGame = new Game(10, "solo", "midlane");
		existingGame.setId((long)1);
		
		Game updatedGame = Game.copy(existingGame);
		updatedGame.setChampionId(1);

		ChampionEssentielsDto championGotById = new ChampionEssentielsDto(updatedGame.getChampionId(), "testChamp");
		
		when(this.gameRepositoryMock.findById(updatedGame.getId())).thenReturn(Optional.of(existingGame)); 
		when(this.gameRepositoryMock.save(updatedGame)).thenReturn(updatedGame);
		when(this.championServiceMock.getChampionById(1)).thenReturn(championGotById);
		when(this.roleAndLaneServiceMock.isLane(updatedGame.getLaneName())).thenReturn(true);
		when(this.roleAndLaneServiceMock.isRole(updatedGame.getRoleName())).thenReturn(true);
		assertEquals(updatedGame, this.gameService.updateGame(updatedGame)); 
	}
	
	@Test 
	void shouldThrowIllegalArgumentException_When_GameIsNullOrGameIdIsNull_updateGame() {

		Game game = new Game(10, "solo", "midlane");
		game.setId(null);
		
		when(this.gameRepositoryMock.findById(null)).thenThrow(InvalidDataAccessApiUsageException.class);
		
		assertAll(
				() -> assertThrows(IllegalArgumentException.class, ()-> { 
						this.gameService.updateGame(null); 
					}),
				() -> assertThrows(IllegalArgumentException.class, ()-> { 
						this.gameService.updateGame(game); 
					})
				
				); 
		;
	}
	
	@Test
	void shouldThrowNoElementFoundException_When_IdDoesntExistInDb_updateGame() {
		Game updatedGame = new Game(10, "solo", "midlane");
		updatedGame.setId((long)1);
		updatedGame.setId((long)1);
		
		when(this.gameRepositoryMock.findById(updatedGame.getId())).thenReturn(Optional.empty());
		assertThrows(NoElementFoundException.class, () -> { 
			this.gameService.updateGame(updatedGame);
		});
	}
	
	void shouldThrowNoElementFoundException_When_IdDoesntExistInDb_deleteGame() { 
		Long idGameToDelete = (long) 1;  
		
		when(this.gameRepositoryMock.findById(idGameToDelete)).thenReturn(Optional.empty()); 
		
		assertThrows(NoElementFoundException.class, () -> { 
			this.gameService.deleteGame(idGameToDelete);
		});
	}
	
	void shouldThrowIllegalArgumentException_When_IdIsNull_deleteGame() { 
		
		when(this.gameRepositoryMock.findById(null)).thenThrow(InvalidDataAccessApiUsageException.class);
		
		assertThrows(IllegalArgumentException.class, () -> { 
			this.gameService.deleteGame(null);
		}); 
	}
	
	 
	
}
