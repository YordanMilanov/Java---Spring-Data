package bg.softuni.gamestore.services.game;

public interface GameService {
    String addGame(String [] args);
    String editGame(String [] args);
    String deleteGame(Long id);
}
