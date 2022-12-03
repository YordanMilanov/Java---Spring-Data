package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportTaskDTO;
import softuni.exam.models.dto.ImportTaskRootDTO;
import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.models.entity.Part;
import softuni.exam.models.entity.Task;
import softuni.exam.models.entity.enums.CarType;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.MechanicRepository;
import softuni.exam.repository.PartRepository;
import softuni.exam.repository.TaskRepository;
import softuni.exam.service.TaskService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final MechanicRepository mechanicRepository;
    private final CarRepository carRepository;

    private final PartRepository partRepository;
    private final Unmarshaller unmarshaller;
    private final Validator validator;
    private final ModelMapper modelMapper;

    private static final Path PATH_TASKS = Path.of(("src/main/resources/files/xml/tasks.xml"));
    private static final String INVALID_MASSAGE = "Invalid task";
    private static final String SUCCESSFUL_IMPORT_FORMAT = "Successfully imported task %.2f";

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, MechanicRepository mechanicRepository, CarRepository carRepository, PartRepository partRepository) throws JAXBException {
        this.taskRepository = taskRepository;
        this.mechanicRepository = mechanicRepository;
        this.carRepository = carRepository;
        this.partRepository = partRepository;

        JAXBContext context = JAXBContext.newInstance(ImportTaskRootDTO.class);
        this.unmarshaller = context.createUnmarshaller();

        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();
        this.modelMapper = new ModelMapper();
    }

    @Override
    public boolean areImported() {
        return this.taskRepository.count() > 0;
    }

    @Override
    public String readTasksFileContent() throws IOException {
        return Files.readString(PATH_TASKS);
    }

    @Override
    public String importTasks() throws IOException, JAXBException {
        ImportTaskRootDTO taskDTOS = (ImportTaskRootDTO) this.unmarshaller.unmarshal(Files.newInputStream(PATH_TASKS));
        return taskDTOS.getTasks()
                .stream()
                .map(this::importTask)
                .collect(Collectors.joining("\n"))
                .trim();
    }

    private String importTask(ImportTaskDTO importTaskDTO) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(importTaskDTO.getDate(), formatter);

        Set<ConstraintViolation<ImportTaskDTO>> errors =
                this.validator.validate(importTaskDTO);

        Optional<Mechanic> optMechanic = this.mechanicRepository.findByFirstName(importTaskDTO.getMechanic().getFirstName());
        if(!errors.isEmpty() || !optMechanic.isPresent()){
            return INVALID_MASSAGE;
        }

        Task task = modelMapper.map(importTaskDTO, Task.class);
        task.setDate(dateTime);

        Car car = this.carRepository.findById(importTaskDTO.getCar().getId()).get();
        task.setCar(car);

        Mechanic mechanic = optMechanic.get();
        task.setMechanic(mechanic);

        Part part = this.partRepository.findById(importTaskDTO.getPart().getId()).get();
        task.setPart(part);

        this.taskRepository.save(task);

        return String.format(SUCCESSFUL_IMPORT_FORMAT, task.getPrice());
    }

    @Override
    public String getCoupeCarTasksOrderByPrice() {
        List<Task> tasks = this.taskRepository.ExportDataTasks(CarType.coupe).get();
        return tasks.stream().map(t -> t.toString()).collect(Collectors.joining("\n"));
    }
}
