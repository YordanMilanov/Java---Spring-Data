package com.example.football.service.impl;

import com.example.football.models.dto.ImportTownDTO;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


//ToDo - Implement all methods
@Service
public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;
    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public TownServiceImpl(TownRepository townRepository) {
        this.townRepository = townRepository;
        this.gson = new GsonBuilder().create();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.modelMapper = new ModelMapper();
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        String json = Files.readString(Path.of("src/main/resources/files/json/towns.json"));
        return json;
    }

    @Override
    public String importTowns() throws IOException {
        String json = this.readTownsFileContent();
        ImportTownDTO[] importTownDTOS = this.gson.fromJson(json, ImportTownDTO[].class);
        List<String> result = new ArrayList<>();

        for (ImportTownDTO dto : importTownDTOS) {
            //if valid?
            Set<ConstraintViolation<Object>> validationErrors = this.validator.validate(dto);

            if(validationErrors.isEmpty()) {
                Optional<Town> otpTown = this.townRepository.findByName(dto.getName());
                //valid

                if(otpTown.isEmpty()) {
                    Town town = this.modelMapper.map(dto, Town.class);

                    this.townRepository.save(town);

                    result.add(String.format("Successfully imported town %s - %d",town.getName(), town.getPopulation()));
                } else {
                    result.add("Invalid town");
                }
            } else {
                //inValid
                result.add("Invalid town!");
            }
        }


        return String.join("\n", result);
    }
}
