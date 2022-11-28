package bg.softuni.gamestore.services.game;

import bg.softuni.gamestore.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;


    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public String addGame(String[] args) {
        return null;
    }

    @Override
    public String editGame(String[] args) {
        return null;
    }

    @Override
    public String deleteGame(Long id) {
        return null;
    }
}
