package at.qe.skeleton.ui.controllers.gameSockets;

import at.qe.skeleton.model.*;
import at.qe.skeleton.model.demo.TeamPlayer;
import at.qe.skeleton.repositories.GameRepository;
import at.qe.skeleton.repositories.TeamRepository;
import at.qe.skeleton.repositories.TimeFlipConfRepository;
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

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import at.qe.skeleton.ui.websockets.WebSocketManager;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)

class GamePlaySocketControllerTest {


    @Mock
    GameRepository gameRepository;

    @Mock
    TimeFlipConfRepository timeFlipConfRepository;

    @Mock
    ScoreManagerController scoreManagerController;

    @Mock
    GameInfoSocketController gameInfoSocketController;

    @InjectMocks
    GamePlaySocketController  gamePlaySocketController;

    @Mock
    TeamRepository teamRepository;

    @Test
    void initFirst(){
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            gamePlaySocketController.initGame(new Game());
        });

    }
    @Test
    void initGame() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            Map<Integer, Queue<Term>> termQueueMap = new ConcurrentHashMap<>();
            Map<Integer, User> nextPlayerMap = new ConcurrentHashMap<>();
            Map<Integer, Queue<TeamPlayer>> teamPlayerMap = new ConcurrentHashMap<>();
            Map<Integer, Integer> runningMap = new ConcurrentHashMap<>();
            Map<Integer, String> typeMap = new ConcurrentHashMap<>();
            Map<Integer, Integer> timeMap = new ConcurrentHashMap<>();
            Map<Integer, Integer> gameFinishedMap = new ConcurrentHashMap<>();
            Game game = new Game();
            Topic topic = game.getTopic();
            List<Term> terms = topic.getTerms();
            Collections.shuffle(terms);
            termQueueMap.put(game.getGameId(), new LinkedList<>(terms));

            System.out.println("init game: " + game);
            scoreManagerController.setupScores(game);

            nextPlayerMap.put(game.getGameId(), teamPlayerMap.get(game.getGameId()).peek().getPlayer());

            runningMap.put(game.getGameId(), 0);
            typeMap.put(game.getGameId(), "");
            timeMap.put(game.getGameId(), 0);
            gameFinishedMap.put(game.getGameId(), 0);

            gameInfoSocketController.setGameMessageToGame(game, "Roll the dice to start the game!");
            assertThat(gamePlaySocketController).isEqualTo(gameInfoSocketController).toString();
        });
        }


    @Test
    void testFlipUpdate() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
        Game activeGame = new Game();
        Game game = new Game();
        gamePlaySocketController.timeFlipUpdate(new Game(),5);

        int faceID = 35;
        Map<Integer,Integer> gameFinishedMap = new ConcurrentHashMap<>();
        Map<Integer, Integer> currentRoundRunning = new ConcurrentHashMap<>();
        Map<Integer, Integer> runningMap = new ConcurrentHashMap<Integer, Integer>();
        when(runningMap.get( runningMap.get(activeGame.getGameId()) == 0 && gameFinishedMap.get(activeGame.getGameId()) != 1));
        Map<Integer,Integer> timeMap = new ConcurrentHashMap<>();
        int time = 4;
        timeMap.put(game.getGameId(),time);
        timeMap.get(35);
        verify(gamePlaySocketController).timeFlipUpdate(game, faceID);
            });
    }


    @Test
    void startTimer(){Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
        gamePlaySocketController.startTimer(new Game(), 6);
    });
    }
    @Test
    void nextTerm() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            gamePlaySocketController.nextTerm(new Game(), 7);
        });
    }

    @Test
    void getNextTerm() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            gamePlaySocketController.getNextTerm(new Game());
        });
    }

    @Test
    void termGuessed() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            gamePlaySocketController.termGuessed(new Game());
        });
    }

    @Test
    void termGuessedWithRulebreak() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            gamePlaySocketController.termGuessedWithRulebreak(new Game());
        });
    }

    @Test
    void termNotGuessed() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            gamePlaySocketController.termNotGuessed(new Game());
            List<Game> expectedList = new ArrayList<>();
            Map<Integer, Integer> currentRoundRunning = new ConcurrentHashMap<Integer, Integer>();
        });        //Integer user1= expectedList.get()
    }

    @Test
    void getNextPlayer() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            gamePlaySocketController.getNextPlayer(new Game());
        });
    }

    @Test
    void onTimerOver() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            gamePlaySocketController.onTimerOver(new Game());
        });
    }

    @Test
    void stopRound() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            gamePlaySocketController.stopRound(new Game());
        });
    }

    @Test
    void getSeperatedRecipients() {
        gamePlaySocketController.getSeperatedRecipients(new Game(), new User());
    }

    @Test
    void getAllRecipients() {
        gamePlaySocketController.getAllRecipients(new Game());
    }

    @Test
    void getType() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            gamePlaySocketController.getType(new Game());
            Map<Integer, Integer> runningMap = new ConcurrentHashMap<Integer, Integer>();
            Game game = new Game();
            int point = game.getGameId();
            assertThat(runningMap.get(point)).isEqualTo(null);
            gamePlaySocketController.getType(game);
        });

    }

    @Test
    void testSetPoints(){
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
        Game game = new Game();
        Integer points = 8;
        Map<Integer, Integer> pointsMap = new ConcurrentHashMap<Integer, Integer>();
        Map<Integer, Integer> msg  = new HashMap<>();
        gamePlaySocketController.setPoints(game, points);
        int punto=game.getGameId();
        pointsMap.put(punto,points);
            });

    }
    @Test
    void getTimeMap() {
        GamePlaySocketController gamePlaySocketController = new GamePlaySocketController();
            Map<Integer, Integer> msg  = new HashMap<>();
            gamePlaySocketController.setTimeMap(msg);
            assertTrue(gamePlaySocketController.getTimeMap() == msg);
        }

    @Test
    void setTimeMap() {
        GamePlaySocketController gamePlaySocketController = new GamePlaySocketController();
        Map<Integer, Integer> msg  = new HashMap<>();
        gamePlaySocketController.setTimeMap(msg);
        assertTrue(gamePlaySocketController.getTimeMap() == msg);
    }


    @Test
    void setTimeInternal() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            Game game = new Game();
            Integer time = 3;
            GamePlaySocketController gamePlaySocketController = new GamePlaySocketController();
            Map<Integer, Integer> msg = new HashMap<>();
            msg.put(game.getGameId(), time);
            gamePlaySocketController.setTimeInternal(game, 3);
            assertTrue(gamePlaySocketController.getTimeMap() == msg);
        });
    }


    @Test
    void testGetPoints() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            gamePlaySocketController.getPoints(new Game());
            Game game = new Game();
            Integer points = 3;
            Map<Integer, Integer> runningMap = new ConcurrentHashMap<Integer, Integer>();
            when(runningMap.get(game.getGameId() == 0)).thenReturn(null);
            Map<Integer, Integer> pointsMap = new ConcurrentHashMap<Integer, Integer>();
            when(pointsMap.get(game.getGameId() == null)).thenReturn(pointsMap.get(game.getGameId()));
            gamePlaySocketController.setPoints(game, points);
            gamePlaySocketController.getPoints(game);
        });


    }



    @Test
    void putTeamPlayerMap()
    {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            gamePlaySocketController.putTeamPlayerMap(new Game(), new Queue<TeamPlayer>() {
                @Override
                public boolean add(TeamPlayer teamPlayer) {
                    return false;
                }

                @Override
                public boolean offer(TeamPlayer teamPlayer) {
                    return false;
                }

                @Override
                public TeamPlayer remove() {
                    return null;
                }

                @Override
                public TeamPlayer poll() {
                    return null;
                }

                @Override
                public TeamPlayer element() {
                    return null;
                }

                @Override
                public TeamPlayer peek() {
                    return null;
                }

                @Override
                public int size() {
                    return 0;
                }

                @Override
                public boolean isEmpty() {
                    return false;
                }

                @Override
                public boolean contains(Object o) {
                    return false;
                }

                @Override
                public Iterator<TeamPlayer> iterator() {
                    return null;
                }

                @Override
                public Object[] toArray() {
                    return new Object[0];
                }

                @Override
                public <T> T[] toArray(T[] a) {
                    return null;
                }

                @Override
                public boolean remove(Object o) {
                    return false;
                }

                @Override
                public boolean containsAll(Collection<?> c) {
                    return false;
                }

                @Override
                public boolean addAll(Collection<? extends TeamPlayer> c) {
                    return false;
                }

                @Override
                public boolean removeAll(Collection<?> c) {
                    return false;
                }

                @Override
                public boolean retainAll(Collection<?> c) {
                    return false;
                }

                @Override
                public void clear() {

                }
            });
            Game game = new Game();
            TeamPlayer teamPlayer = new TeamPlayer();
            Queue<TeamPlayer> orderedPlayerList = new Queue<TeamPlayer>() {
                @Override
                public boolean add(TeamPlayer teamPlayer) {
                    return false;
                }

                @Override
                public boolean offer(TeamPlayer teamPlayer) {
                    return false;
                }

                @Override
                public TeamPlayer remove() {
                    return null;
                }

                @Override
                public TeamPlayer poll() {
                    return null;
                }

                @Override
                public TeamPlayer element() {
                    return null;
                }

                @Override
                public TeamPlayer peek() {
                    return null;
                }

                @Override
                public int size() {
                    return 0;
                }

                @Override
                public boolean isEmpty() {
                    return false;
                }

                @Override
                public boolean contains(Object o) {
                    return false;
                }

                @Override
                public Iterator<TeamPlayer> iterator() {
                    return null;
                }

                @Override
                public Object[] toArray() {
                    return new Object[0];
                }

                @Override
                public <T> T[] toArray(T[] a) {
                    return null;
                }

                @Override
                public boolean remove(Object o) {
                    return false;
                }

                @Override
                public boolean containsAll(Collection<?> c) {
                    return false;
                }

                @Override
                public boolean addAll(Collection<? extends TeamPlayer> c) {
                    return false;
                }

                @Override
                public boolean removeAll(Collection<?> c) {
                    return false;
                }

                @Override
                public boolean retainAll(Collection<?> c) {
                    return false;
                }

                @Override
                public void clear() {

                }
            };

            GamePlaySocketController gamePlaySocketController = new GamePlaySocketController();
            User user = new User();
            Map<Integer,Queue<TeamPlayer>> teamPlayerMap = new ConcurrentHashMap<>();
            teamPlayerMap.put(game.getGameId(), orderedPlayerList);
            gamePlaySocketController.putTeamPlayerMap(game,orderedPlayerList);
        });
    }

    @Test
    void getNextRoundPlayer() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            GamePlaySocketController gamePlaySocketController = new GamePlaySocketController();
            User user = new User();
            Game game = new Game();
            Map<Integer, User> players = new HashMap<>();
            gamePlaySocketController.getNextRoundPlayer(game);
        });
    }
}