package com.example.football.service.impl;

import com.example.football.models.dto.ImportStatDTO;
import com.example.football.models.dto.ImportStatRootDTO;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

//ToDo - Implement all methods
@Service
public class StatServiceImpl implements StatService {

    private final Path path = Path.of("src/main/resources/files/xml/stats.xml");

    private final StatRepository statRepository;
    private final Unmarshaller unmarshaller;
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public StatServiceImpl(StatRepository statRepository) throws JAXBException {
        this.statRepository = statRepository;

        JAXBContext context = JAXBContext.newInstance(ImportStatRootDTO.class);
        this.unmarshaller = context.createUnmarshaller();

        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.modelMapper = new ModelMapper();
    }


    @Override
    public boolean areImported() {
        return this.statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        String xml = Files.readString(Path.of("src/main/resources/files/xml/stats.xml"));
        return xml;
    }

    @Override
    public String importStats() throws IOException, JAXBException {
        // creating ImportStatRootDTO which is List of ImportStatDTOS
        ImportStatRootDTO statDTOS = (ImportStatRootDTO)
                this.unmarshaller.unmarshal(Files.newInputStream(path));

        //this is the ImportStatRootDTO
        String output = statDTOS
                //getStats -> returns the List of every single ImportStatDTO
                .getStats()
                //stream it
                .stream()
                //and now we import and map every ImportStatDTO to String using the importStat method which we created and implemented below.
                .map(this::importStat)
                //at the end collect everything to String using joiner
                .collect(Collectors.joining("\n"));

        return output;
    }

    private String importStat (ImportStatDTO dto) {
        Set<ConstraintViolation<ImportStatDTO>> errors = this.validator.validate(dto);

        if(!errors.isEmpty()) {
            return "Invalid Stat";
        }

        Optional<Stat> optStat = this.statRepository.findByPassingAndEnduranceAndShooting(dto.getPassing(), dto.getEndurance(), dto.getShooting());
        if (optStat.isPresent()) {
            return "Invalid Stat";
        } else {
            Stat stat = this.modelMapper.map(dto, Stat.class);
            this.statRepository.save(stat);
            return String.format("Successfully imported Stat %.2f - %.2f - %.2f",dto.getShooting(), dto.getPassing(), dto.getEndurance());
        }
    }
}
