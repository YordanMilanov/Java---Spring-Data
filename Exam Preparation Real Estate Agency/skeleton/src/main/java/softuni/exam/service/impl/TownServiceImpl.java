package softuni.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TownImportDTO;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TownServiceImpl implements TownService {

    private static final String INVALID_MASSAGE = "Invalid Town";
    private static final String IMPORT_TOWN_FORMAT ="Successfully imported town %s - %d";
    private static final Path PATH_TOWN = Path.of("src/main/resources/files/json/towns.json");
    private TownRepository townRepository;
    private Gson gson;

    private ModelMapper modelMapper;
    private Validator validator;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, Gson gson) {
        this.townRepository = townRepository;
        this.gson = gson;
        this.modelMapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(PATH_TOWN);
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder sb = new StringBuilder();

        TownImportDTO[] townImportDTOS = this.gson.fromJson(readTownsFileContent(), TownImportDTO[].class);


        for (TownImportDTO townImportDTO : townImportDTOS) {
            Set<ConstraintViolation<TownImportDTO>> errors = validator.validate(townImportDTO);

            if(!errors.isEmpty()) {
                sb.append(INVALID_MASSAGE).append(System.lineSeparator());
                continue;
            }

            if(this.townRepository.findByTownName(townImportDTO.getTownName()).isPresent()) {
                sb.append(INVALID_MASSAGE).append(System.lineSeparator());
            }else {
                Town town = modelMapper.map(townImportDTO, Town.class);
                sb.append(String.format(IMPORT_TOWN_FORMAT, town.getTownName(), town.getPopulation())).append(System.lineSeparator());
                this.townRepository.save(town);
            }
        }
        return sb.toString().trim();
    }
}
