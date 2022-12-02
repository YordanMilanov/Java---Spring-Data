package com.example.football.service.impl;

import com.example.football.models.dto.ImportPlayerDTO;
import com.example.football.models.dto.ImportPlayerRootDTO;
import com.example.football.models.entity.Player;
import com.example.football.models.entity.Stat;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

//ToDo - Implement all methods
@Service
public class PlayerServiceImpl implements PlayerService {

    private final Path path = Path.of("src/main/resources/files/xml/players.xml");
    private final PlayerRepository playerRepository;
    private final Unmarshaller unmarshaller;
    private final Validator validator;
    private final ModelMapper modelMapper;
    private final TeamRepository teamRepository;
    private final StatRepository statRepository;
    private final TownRepository townRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, TeamRepository teamRepository, StatRepository statRepository, TownRepository townRepository) throws JAXBException {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.statRepository = statRepository;
        this.townRepository = townRepository;

        JAXBContext context = JAXBContext.newInstance(ImportPlayerRootDTO.class);
        this.unmarshaller = context.createUnmarshaller();

        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        this.modelMapper = new ModelMapper();
    }


    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        String xml = Files.readString(Path.of("src/main/resources/files/xml/players.xml"));
        return xml;
    }

    @Override
    public String importPlayers() throws IOException, JAXBException {

        ImportPlayerRootDTO playerDTOS = (ImportPlayerRootDTO)
                this.unmarshaller.unmarshal(Files.newInputStream(path));

        String collect = playerDTOS.getPlayers()
                .stream()
                .map(this::importPlayer)
                .collect(Collectors.joining("\n"));

        return collect;

    }

    private String importPlayer(ImportPlayerDTO dto) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateTime = LocalDate.parse(dto.getBirthDate(), formatter);

        Set<ConstraintViolation<ImportPlayerDTO>> errors =
                this.validator.validate(dto);

        if(!errors.isEmpty()) {
            return "Invalid Player";
        }

        Optional<Player> otpPlayer = this.playerRepository.findByEmail(dto.getEmail());

        if(otpPlayer.isPresent()) {
            return "Invalid Player";
        }

        Town town = this.townRepository.findByName(dto.getTown().getName()).get();
        Team team = this.teamRepository.findByName(dto.getTeam().getName()).get();
        Stat stat = this.statRepository.findById(dto.getStat().getId()).get();
        Player player = modelMapper.map(dto, Player.class);
        player.setBirthDate(dateTime);
        player.setStat(stat);
        player.setTeam(team);
        player.setTown(town);

        this.playerRepository.save(player);
        return String.format
                ("Successfully imported Player Talbert %s %s - %s",
                        player.getFirstName(), player.getLastName(), player.getPosition().toString());
    }

    @Override
    public String exportBestPlayers() {
        StringBuilder sb = new StringBuilder();
        List<Player> bestPlayers = this.playerRepository.findBestPlayers(LocalDate.of(1995, 1, 1), LocalDate.of(2003,01,01)).get();
        bestPlayers.forEach(p ->sb.append(p.BestPlayerExport()));
        return sb.toString();
    }
}
