package com.medkha.lol_notes.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.medkha.lol_notes.dto.ChampionEssentielsDto;
import com.medkha.lol_notes.dto.GameDTO;
import com.medkha.lol_notes.dto.QueueDTO;
import com.medkha.lol_notes.entities.Game;
import com.medkha.lol_notes.exceptions.NoElementFoundException;
import com.medkha.lol_notes.mapper.MapperService;
import com.medkha.lol_notes.repositories.GameRepository;
import com.medkha.lol_notes.services.filters.DeathFilterService;
import com.medkha.lol_notes.services.impl.GameServiceImpl;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class GameServiceTest {
	private GameService gameService;
	private ChampionService championServiceMock;
	private RoleAndLaneService roleAndLaneServiceMock;
	private QueueService queueServiceMock;
	private GameRepository gameRepositoryMock;
	private DeathService deathServiceMock;
	private DeathFilterService deathFilterService;
	private MapperService mapperServiceMock;

	@BeforeEach
	public void setupMock(){
		gameRepositoryMock = mock(GameRepository.class);
		championServiceMock = mock(ChampionService.class);
		roleAndLaneServiceMock = mock(RoleAndLaneService.class);
		mapperServiceMock = mock(MapperService.class);
		queueServiceMock = mock(QueueService.class);
		gameService = new GameServiceImpl(gameRepositoryMock, championServiceMock, roleAndLaneServiceMock, queueServiceMock, deathServiceMock, deathFilterService, mapperServiceMock);
	}

	private GameDTO sampleGameDTOWithoutId(){
		GameDTO game = new GameDTO();
		game.setChampionId(10);
		game.setRoleName("SOLO");
		game.setLaneName("MIDLANE");
		game.setQueueId(11);
		return game;
	}

	private GameDTO sampleGameDTOWithId(){
		GameDTO game = new GameDTO();
		game.setChampionId(10);
		game.setRoleName("SOLO");
		game.setLaneName("MIDLANE");
		game.setQueueId(11);
		game.setId((long) 1);
		return game;
	}

	private Game sampleGameWithId() {
		Game game = new Game();
		game.setChampionId(10);
		game.setRoleName("SOLO");
		game.setLaneName("MIDLANE");
		game.setQueueId(11);
		game.setId((long) 1);
		return game;
	}

	private Game sampleGameWithoutId() {
		Game game = new Game();
		game.setChampionId(10);
		game.setRoleName("SOLO");
		game.setLaneName("MIDLANE");
		game.setQueueId(11);
		return game;
	}

	private ChampionEssentielsDto sampleChampionEssentiels() {
		return new ChampionEssentielsDto(10, "testChamp", "url image");
	}

	private QueueDTO sampleQueueDto() {
		return new QueueDTO(11, "sample queue");
	}
	@Test
	public void shouldCreateGame() {
		// given
		GameDTO expectedResult = GameDTO.copy(sampleGameDTOWithoutId());
		expectedResult.setId((long) 1);

		when(this.queueServiceMock.getQueueById(sampleGameDTOWithoutId().getQueueId())).thenReturn(Optional.of(sampleQueueDto()));
		when(this.championServiceMock.getChampionById(10)).thenReturn(sampleChampionEssentiels());
		when(this.mapperServiceMock.convert(sampleGameWithId(), GameDTO.class)).thenReturn(sampleGameDTOWithId());
		when(this.gameRepositoryMock.save(sampleGameWithoutId())).thenReturn(sampleGameWithId());
		when(this.mapperServiceMock.convert(sampleGameDTOWithoutId(), Game.class)).thenReturn(sampleGameWithoutId());
		// when
		GameDTO createdGame = this.gameService.createGame(sampleGameDTOWithoutId());

		// then
		assertEquals(expectedResult, createdGame);
	}
	
	@Test
	public void shouldThrowIllegalArgumentException_when_GameIsNull() {
		assertThrows(IllegalArgumentException.class, () -> this.gameService.createGame(null));
	}

	@Test
	void shouldThrowIllegalArgumentException_When_ChampionIdIsNull_createGame(){
		GameDTO gameWithChampionId = sampleGameDTOWithoutId();
		gameWithChampionId.setChampionId(null);
		when(this.championServiceMock.getChampionById(null)).thenThrow(IllegalArgumentException.class);
		assertThrows(IllegalArgumentException.class, () -> this.gameService.createGame(gameWithChampionId));
	}

	@Test
	@Disabled("We no longer obligate to have a role or lane, so aram could be also taken in count. ")
	void shouldThrowIllegalArgumentException_When_RoleOrLaneIsNull_createGame(){
		GameDTO gameWithoutRoleId = sampleGameDTOWithoutId();
		gameWithoutRoleId.setRoleName(null);
		GameDTO gameWithoutLaneId = sampleGameDTOWithoutId();
		gameWithoutLaneId.setLaneName(null);

		when(roleAndLaneServiceMock.isLane(gameWithoutRoleId.getLaneName())).thenReturn(true);
		assertAll(
				() -> assertThrows(IllegalArgumentException.class, () -> this.gameService.createGame(gameWithoutRoleId)),
				() -> assertThrows(IllegalArgumentException.class, () -> this.gameService.createGame(gameWithoutLaneId))
		);
	}

	@Test
	void shouldThrowIllegalArgumentException_When_QueueIdIsNullOrDoesntExist_createGame() {
		GameDTO gameWithoutQueueId = sampleGameDTOWithoutId();
		gameWithoutQueueId.setQueueId(null);

		GameDTO gameWithQueueIdDoesntExist = sampleGameDTOWithoutId();
		gameWithQueueIdDoesntExist.setQueueId(1);

		when(this.queueServiceMock.getQueueById(null)).thenThrow(IllegalArgumentException.class);
		when(this.queueServiceMock.getQueueById(gameWithQueueIdDoesntExist.getQueueId())).thenReturn(Optional.empty());

		assertAll(
				() -> assertThrows(IllegalArgumentException.class, () -> {
					this.gameService.createGame(gameWithoutQueueId);
				}),
				() -> assertThrows(IllegalArgumentException.class, () -> {
					this.gameService.createGame(gameWithQueueIdDoesntExist);
				})
		);
	}

	@Test 
	public void shouldGetGameById() {
		when(this.gameRepositoryMock.findById(sampleGameDTOWithId().getId())).thenReturn(Optional.of(sampleGameWithId()));
		when(this.mapperServiceMock.convert(sampleGameWithId(), GameDTO.class)).thenReturn(sampleGameDTOWithId());
		assertEquals(sampleGameDTOWithId(), this.gameService.findById(sampleGameDTOWithId().getId()));
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
		// given
		GameDTO updatedGameDTO = GameDTO.copy(sampleGameDTOWithId());
		updatedGameDTO.setRoleName("newRole");

		Game updatedGame = Game.copy(sampleGameWithId());
		updatedGame.setRoleName("newRole");

		when(mapperServiceMock.convert(updatedGameDTO, Game.class)).thenReturn(updatedGame);
		when(this.gameRepositoryMock.findById(updatedGameDTO.getId())).thenReturn(Optional.of(sampleGameWithId()));
		when(this.championServiceMock.getChampionById(any())).thenReturn(sampleChampionEssentiels());
		when(this.queueServiceMock.getQueueById(any())).thenReturn(Optional.of(sampleQueueDto()));
		when(this.gameRepositoryMock.save(mapperServiceMock.convert(updatedGameDTO, Game.class))).thenReturn(updatedGame);
		when(mapperServiceMock.convert(updatedGame, GameDTO.class)).thenReturn(updatedGameDTO);

		// when
		GameDTO result = this.gameService.updateGame(updatedGameDTO);

		// then
		assertEquals(updatedGameDTO, result);
	}
	
	@Test 
	void shouldThrowIllegalArgumentException_When_GameIsNullOrGameIdIsNull_updateGame() {
		when(this.gameRepositoryMock.findById(null)).thenThrow(InvalidDataAccessApiUsageException.class);
		assertAll(
				() -> assertThrows(IllegalArgumentException.class, ()-> {
						this.gameService.updateGame(null);
					}),
				() -> assertThrows(IllegalArgumentException.class, ()-> {
						this.gameService.updateGame(sampleGameDTOWithoutId());
					})
				);
	}
	
	@Test
	void shouldThrowNoElementFoundException_When_IdDoesntExistInDb_updateGame() {
		when(this.gameRepositoryMock.findById(sampleGameWithId().getId())).thenReturn(Optional.empty());
		assertThrows(NoElementFoundException.class, () -> {
			this.gameService.updateGame(sampleGameDTOWithId());
		});
	}
	@Test
	void shouldThrowIllegalArgumentException_When_ChampionIdIsNull_updateGame(){
		GameDTO gameWithChampionId = sampleGameDTOWithId();
		gameWithChampionId.setChampionId(null);
		when(this.championServiceMock.getChampionById(null)).thenThrow(IllegalArgumentException.class);
		assertThrows(IllegalArgumentException.class, () -> this.gameService.createGame(gameWithChampionId));
	}
	@Test
	@Disabled("No need for role or lane for this current version.")
	void shouldThrowIllegalArgumentException_When_RoleOrLaneIsNull_updateGame(){
		GameDTO gameWithoutRoleId = sampleGameDTOWithId();
		gameWithoutRoleId.setRoleName(null);
		GameDTO gameWithoutLaneId = sampleGameDTOWithId();
		gameWithoutLaneId.setLaneName(null);

		when(roleAndLaneServiceMock.isLane(gameWithoutRoleId.getLaneName())).thenReturn(true);
		assertAll(
				() -> assertThrows(IllegalArgumentException.class, () -> this.gameService.createGame(gameWithoutRoleId)),
				() -> assertThrows(IllegalArgumentException.class, () -> this.gameService.createGame(gameWithoutLaneId))
		);
	}

	@Test
	void shouldThrowIllegalArgumentException_When_QueueIdIsNullOrDoesntExist_updateGame() {
		GameDTO gameWithoutQueueId = sampleGameDTOWithId();
		gameWithoutQueueId.setQueueId(null);

		GameDTO gameWithQueueIdDoesntExist = sampleGameDTOWithId();
		gameWithQueueIdDoesntExist.setQueueId(1);

		when(this.gameRepositoryMock.findById(any())).thenReturn(Optional.of(new Game()));
		when(this.queueServiceMock.getQueueById(null)).thenThrow(IllegalArgumentException.class);
		when(this.queueServiceMock.getQueueById(gameWithQueueIdDoesntExist.getQueueId())).thenReturn(Optional.empty());

		assertAll(
				() -> assertThrows(IllegalArgumentException.class, () -> {
					this.gameService.updateGame(gameWithoutQueueId);
				}),
				() -> assertThrows(IllegalArgumentException.class, () -> {
					this.gameService.updateGame(gameWithQueueIdDoesntExist);
				})
		);
	}

	@Test
	void shouldThrowNoElementFoundException_When_IdDoesntExistInDb_deleteGame() { 
		when(this.gameRepositoryMock.findById(sampleGameWithId().getId())).thenReturn(Optional.empty());
		assertThrows(NoElementFoundException.class, () -> { 
			this.gameService.deleteGame(sampleGameDTOWithId().getId());
		});
	}

	@Test
	void shouldThrowIllegalArgumentException_When_IdIsNull_deleteGame() {
		when(this.gameRepositoryMock.findById(null)).thenThrow(InvalidDataAccessApiUsageException.class);
		assertThrows(IllegalArgumentException.class, () -> { 
			this.gameService.deleteGame(null);
		}); 
	}

}
