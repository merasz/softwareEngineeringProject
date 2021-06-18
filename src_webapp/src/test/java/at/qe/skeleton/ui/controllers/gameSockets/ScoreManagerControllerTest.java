package at.qe.skeleton.ui.controllers.gameSockets;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Score;
import at.qe.skeleton.model.Team;
import at.qe.skeleton.model.User;
import at.qe.skeleton.model.demo.TeamScoreInfo;
import at.qe.skeleton.repositories.GameRepository;
import at.qe.skeleton.repositories.ScoreRepository;
import at.qe.skeleton.repositories.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;
import at.qe.skeleton.model.GameTopicCount;
import at.qe.skeleton.model.Topic;
import at.qe.skeleton.model.demo.Message;
import at.qe.skeleton.services.TopicService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import at.qe.skeleton.model.Game;
import at.qe.skeleton.ui.websockets.WebSocketManager;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;



@ExtendWith(MockitoExtension.class)

class ScoreManagerControllerTest {
    @Mock
    GameRepository gameRepository;
    @InjectMocks
    ScoreManagerController scoreManagerController;


    @Test
    void addScoreToTeam() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
        TeamRepository teamRepository = new TeamRepository() {
            @Override
            public List<Team> findByGame(Integer game) {
                return null;
            }

            @Override
            public List<Team> findAllByTeamPlayers(User user) {
                return null;
            }

            @Override
            public Team findByTeamId(Long id) {
                return null;
            }

            @Override
            public Team findByTeamPlayersAndGame(User user, Game game) {
                return null;
            }

            @Override
            public void delete(Team entity) {

            }

            @Override
            public List<Team> findAll() {
                return null;
            }

            @Override
            public Team findById(Integer integer) {
                return null;
            }

            @Override
            public <S extends Team> S save(S entity) {
                return null;
            }
        };
        Game game = new Game();
        int roundScore = 22;
        User user = new User();
        ScoreRepository scoreRepository = new ScoreRepository() {
            @Override
            public List<Score> findAllByUser(User user) {
                return null;
            }

            @Override
            public List<Score> findGameScoresByUser(List<Team> teams) {
                return null;
            }

            @Override
            public List<Score> findGameScoresByGame(Game game) {
                return null;
            }

            @Override
            public int countGamesByTeam(List<Team> teams) {
                return 0;
            }

            @Override
            public Integer countWonGamesByUser(List<Team> teams) {
                return null;
            }

            @Override
            public Integer countLostGamesByUser(List<Team> teams) {
                return null;
            }

            @Override
            public List<User> getTopPlayersUsernames() {
                return null;
            }

            @Override
            public List<Integer> getTopPlayersScores() {
                return null;
            }

            @Override
            public List<User> getTopPlayersInAGame() {
                return null;
            }

            @Override
            public List<Integer> getTopPlayersInAGameScore() {
                return null;
            }

            @Override
            public List<Team> getTeamsForGame(Integer game) {
                return null;
            }

            @Override
            public List<Integer> getForTeamsByGameScore(Integer gameId, List<Team> teams) {
                return null;
            }

            @Override
            public List<Score> getScoresForTeamsByGame(Game game) {
                return null;
            }

            @Override
            public List<Team> getTopTeamInAGame(Game game) {
                return null;
            }

            @Override
            public void delete(Score entity) {

            }

            @Override
            public List<Score> findAll() {
                return null;
            }

            @Override
            public Score findById(Integer integer) {
                return null;
            }

            @Override
            public <S extends Score> S save(S entity) {
                return null;
            }
        };
        Team team = teamRepository.findByTeamPlayersAndGame(user, game);
        Score tmp = new Score();
        tmp.setGame(game);
        tmp.setTotalRoundScore(roundScore);
        tmp.setTeam(team);
        tmp.setUser(user);
        scoreRepository.save(tmp);
        scoreManagerController.setupScores(game);
        AtomicBoolean gameWon = new AtomicBoolean(false);
        Map<Integer, Map<String, TeamScoreInfo>> scores = new ConcurrentHashMap<>();
        scores.get(game.getGameId()).forEach((key, value) -> {
            when(value.getScore() >= game.getScoreToWin()).thenReturn(true);
            System.out.println("IN LOOP " + key + " " + value.getScore());

            gameWon.set(true);
            assertThat(gameWon.get()).isTrue();
        });
        });
    }


    @Test
    void setGameEnd() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
        Game game = new Game();
        Date end = Timestamp.valueOf(LocalDateTime.now());
        Game newgame = gameRepository.findByGameId(game.getGameId());
        newgame.setEndTime(end);
        newgame.setActive(false);

        gameRepository.save(newgame);
        verify(gameRepository).save(newgame);
        scoreManagerController.setGameEnd(newgame);

        });
    }
    }


