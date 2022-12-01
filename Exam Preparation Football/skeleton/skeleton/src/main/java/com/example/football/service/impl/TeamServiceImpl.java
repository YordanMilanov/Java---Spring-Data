package com.example.football.service.impl;

import com.example.football.models.dto.ImportTeamDTO;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

//ToDo - Implement all methods
@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;
    private final TownRepository townRepository;
    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, TownRepository townRepository) {
        this.teamRepository = teamRepository;
        this.townRepository = townRepository;
        this.gson = new Gson().newBuilder().create();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.modelMapper = new ModelMapper();
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        String json = Files.readString(Path.of("src/main/resources/files/json/teams.json"));
        return json;
    }

    @Override
    public String importTeams() throws IOException {
        String json = this.readTeamsFileContent();
        ImportTeamDTO[] importTeamDTOS = this.gson.fromJson(json, ImportTeamDTO[].class);
        StringBuilder sb = new StringBuilder();

        for (ImportTeamDTO importTeamDTO : importTeamDTOS) {
            if(this.teamRepository.findByName(importTeamDTO.getName()).isPresent()) {
                sb.append("Invalid Team\n");
                continue;
            }
            Set<ConstraintViolation<ImportTeamDTO>> validationErrors = validator.validate(importTeamDTO);
            //check if there is any error with the input
            if (validationErrors.isEmpty()) {
                sb.append(
                        String.format("Successfully imported Team %s - %d\n",
                                importTeamDTO.getName(),
                                importTeamDTO.getFanBase()));
            } else {
                sb.append("Invalid Team\n");
                continue;
            }
            //returns us the town of the team
            Town town = this.townRepository.findByName(importTeamDTO.getTownName()).get();
            //map the info of the DTO to the team.class
            Team team = modelMapper.map(importTeamDTO, Team.class);
            //set the town field to the team object
            team.setTown(town);
            this.teamRepository.save(team);
        }
        return sb.toString();
    }
}
