package softuni.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportPartDTO;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.PartRepository;
import softuni.exam.service.PartService;

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
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;
    private static final Path PATH_PARTS = Path.of(("src/main/resources/files/json/parts.json"));
    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;

    private static final String INVALID_MASSAGE = "Invalid part";
    private static final String SUCCESSFUL_IMPORT_FORMAT = "Successfully imported part %s - %.2f";

    @Autowired
    public PartServiceImpl(PartRepository partRepository) {
        this.partRepository = partRepository;

        this.gson = new GsonBuilder().create();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.modelMapper = new ModelMapper();
    }

    @Override
    public boolean areImported() {
        return this.partRepository.count() > 0;
    }

    @Override
    public String readPartsFileContent() throws IOException {
        return Files.readString(PATH_PARTS);
    }

    @Override
    public String importParts() throws IOException {
        ImportPartDTO[] partDTOS = gson.fromJson(readPartsFileContent(), ImportPartDTO[].class);

        return Arrays.stream(partDTOS)
                .map(this::importPart)
                .collect(Collectors.joining("\n"))
                .trim();
    }

    private String importPart(ImportPartDTO importPartDTO) {
        Set<ConstraintViolation<ImportPartDTO>> errors = validator.validate(importPartDTO);

        if(errors.size() > 0 || this.partRepository.findByPartName(importPartDTO.getPartName()).isPresent()) {
            return INVALID_MASSAGE;
        }
        Part part = modelMapper.map(importPartDTO, Part.class);

        this.partRepository.save(part);

        return String.format(SUCCESSFUL_IMPORT_FORMAT,part.getPartName(), part.getPrice());
    }
}
