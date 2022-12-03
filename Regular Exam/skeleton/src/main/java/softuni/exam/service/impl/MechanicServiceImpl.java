package softuni.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportMechanicDTO;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.repository.MechanicRepository;
import softuni.exam.service.MechanicService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MechanicServiceImpl implements MechanicService {

    private final MechanicRepository mechanicRepository;
    private static final Path PATH_MECHANICS = Path.of(("src/main/resources/files/json/mechanics.json"));
    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;
    private static final String INVALID_MASSAGE = "Invalid mechanic";
    private static final String SUCCESSFUL_IMPORT_FORMAT = "Successfully imported mechanic %s %s";

    public MechanicServiceImpl(MechanicRepository mechanicRepository) {
        this.mechanicRepository = mechanicRepository;

        this.gson = new GsonBuilder().create();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.modelMapper = new ModelMapper();
    }

    @Override
    public boolean areImported() {
        return this.mechanicRepository.count() > 0;
    }

    @Override
    public String readMechanicsFromFile() throws IOException {
        return Files.readString(PATH_MECHANICS);
    }

    @Override
    public String importMechanics() throws IOException {
        ImportMechanicDTO[] mechanicDTOS = gson.fromJson(readMechanicsFromFile(), ImportMechanicDTO[].class);

       return Arrays.stream(mechanicDTOS)
                .map(this::importMechanic)
                .collect(Collectors.joining("\n")).trim();
    }

    private String importMechanic(ImportMechanicDTO importMechanicDTO) {
        Set<ConstraintViolation<ImportMechanicDTO>> errors =
                validator.validate(importMechanicDTO);

        if (!errors.isEmpty()
                || this.mechanicRepository.findByEmail(importMechanicDTO.getEmail()).isPresent()) {
            return INVALID_MASSAGE;
        }

        Mechanic mechanic = modelMapper.map(importMechanicDTO, Mechanic.class);

        this.mechanicRepository.save(mechanic);

        return String.format(SUCCESSFUL_IMPORT_FORMAT,
                mechanic.getFirstName(),
                mechanic.getLastName());
    }
}
