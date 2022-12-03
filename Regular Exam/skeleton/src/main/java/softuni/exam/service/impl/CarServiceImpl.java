package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportCarDTO;
import softuni.exam.models.dto.ImportCarRootDTO;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final Unmarshaller unmarshaller;
    private final Validator validator;
    private final ModelMapper modelMapper;

    private static final Path PATH_CARS = Path.of(("src/main/resources/files/xml/cars.xml"));
    private static final String INVALID_MASSAGE = "Invalid car";
    private static final String SUCCESSFUL_IMPORT_FORMAT = "Successfully imported car %s - %s";
    @Autowired
    public CarServiceImpl(CarRepository carRepository) throws JAXBException {
        this.carRepository = carRepository;

        JAXBContext context = JAXBContext.newInstance(ImportCarRootDTO.class);
        this.unmarshaller = context.createUnmarshaller();

        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();
        this.modelMapper = new ModelMapper();
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFromFile() throws IOException {
        return Files.readString(PATH_CARS);
    }

    @Override
    public String importCars() throws IOException, JAXBException {
        ImportCarRootDTO importCarDTOS =
                (ImportCarRootDTO) this.unmarshaller.unmarshal(Files.newInputStream(PATH_CARS));

        return importCarDTOS.getCars()
                .stream()
                .map(this::importCar)
                .collect(Collectors.joining("\n"))
                .trim();
    }

    private String importCar(ImportCarDTO importCarDTO) {
        Set<ConstraintViolation<ImportCarDTO>> errors =
                this.validator.validate(importCarDTO);

        if(!errors.isEmpty() ||
                this.carRepository.findByPlateNumber(importCarDTO.getPlateNumber()).isPresent()) {
            return INVALID_MASSAGE;
        }

        Car car = modelMapper.map(importCarDTO, Car.class);
        this.carRepository.save(car);
        return String.format(SUCCESSFUL_IMPORT_FORMAT, car.getCarMake(), car.getCarModel());
    }
}
