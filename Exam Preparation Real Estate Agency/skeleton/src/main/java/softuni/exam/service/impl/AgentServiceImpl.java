package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AgentImportDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.AgentService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

@Service
public class AgentServiceImpl implements AgentService {

    private static final String AGENT_FILE_PATH = "src/main/resources/files/json/agents.json";
    private static final String INVALID_MASSAGE = "Invalid Agent";
    private static final String SUCCESSFUL_IMPORTED_FORMAT = "Successfully imported agent - %s %s";
    private TownRepository townRepository;
    private AgentRepository agentRepository;
    private ModelMapper modelMapper;
    private Validator validator;
    private Gson gson;

    @Autowired
    public AgentServiceImpl(TownRepository townRepository, AgentRepository agentRepository, Gson gson) {
        this.townRepository = townRepository;
        this.agentRepository = agentRepository;
        this.gson = gson;
        this.modelMapper = new ModelMapper();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Override
    public boolean areImported() {
        return this.agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(Path.of(AGENT_FILE_PATH));
    }

    @Override
    public String importAgents() throws IOException {

        StringBuilder sb = new StringBuilder();

        AgentImportDTO[] agentImportDTOS = this.gson.fromJson(readAgentsFromFile(), AgentImportDTO[].class);
        for (AgentImportDTO agentImportDTO : agentImportDTOS) {

            Set<ConstraintViolation<AgentImportDTO>> errors = validator.validate(agentImportDTO);

            if (!errors.isEmpty() || this.agentRepository.findAgentByFirstName(agentImportDTO.getFirstName()).isPresent()) {
                sb.append(INVALID_MASSAGE).append(System.lineSeparator());
            } else {
                Agent agent = modelMapper.map(agentImportDTO, Agent.class);
                Town town = this.townRepository.findByTownName(agentImportDTO.getTown()).get();
                agent.setTown(town);
                sb.append(String.format(SUCCESSFUL_IMPORTED_FORMAT,
                                agent.getFirstName(),
                                agent.getLastName()))
                        .append(System.lineSeparator());
                this.agentRepository.save(agent);
            }
        }

        return sb.toString().trim();
    }
}
