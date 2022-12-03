package exam.service.impl;

import exam.model.Town;
import exam.model.dtos.ImportTownDTO;
import exam.model.dtos.ImportTownRootDTO;
import exam.repository.TownRepository;
import exam.service.TownService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TownServiceImpl implements TownService {

    private static final Path PATH_TOWN_FILE = Path.of("C:\\Users\\danch\\IdeaProjects\\Java---Spring-Data\\Exam Preparation Laptop Shop\\skeleton\\src\\main\\resources\\files\\xml\\towns.xml");
    private static final String INVALID_MASSAGE = "Invalid town";
    private static final String SUCCESSFUL_FORMAT = "Successfully imported Town %s";
    private final TownRepository townRepository;
    private final Unmarshaller unmarshaller;
    private final Validator validator;
    private final ModelMapper modelMapper;
    public TownServiceImpl(TownRepository townRepository) throws JAXBException {
        this.townRepository = townRepository;
        this.modelMapper = new ModelMapper();

        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        JAXBContext context = JAXBContext.newInstance(ImportTownRootDTO.class);
        this.unmarshaller = context.createUnmarshaller();

    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(PATH_TOWN_FILE);
    }

    @Override
    public String importTowns() throws JAXBException, IOException {

        StringBuilder sb = new StringBuilder();

        ImportTownRootDTO townDTOS =
                (ImportTownRootDTO) this.unmarshaller.unmarshal(Files.newInputStream(PATH_TOWN_FILE));

        for (ImportTownDTO dto : townDTOS.getTowns()) {
            Set<ConstraintViolation<ImportTownDTO>> errors = validator.validate(dto);
            if(!errors.isEmpty()) {
                sb.append(INVALID_MASSAGE).append(System.lineSeparator());
            } else {
                Town town = modelMapper.map(dto, Town.class);
                this.townRepository.save(town);
                sb.append(String.format(SUCCESSFUL_FORMAT, town.getName())).append(System.lineSeparator());
            }
        }

        return sb.toString().trim();
    }

}
