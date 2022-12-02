package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.CarSeedDTO;
import softuni.exam.models.entities.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class CarServiceImpl implements CarService {

    private static final String CAR_FILE_PATH = "src/main/resources/files/json/cars.json";
    private final CarRepository carRepository;

    private final Gson gson;

    private final ValidationUtil validationUtil;

    private final ModelMapper modelMapper;

    public CarServiceImpl(CarRepository carRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFileContent() throws IOException {

        return Files.readString(Path.of(CAR_FILE_PATH));
    }

    @Override
    public String importCars() throws IOException {
        StringBuilder sb = new StringBuilder();


        Arrays.stream(gson.fromJson(readCarsFileContent(), CarSeedDTO[].class))
                .filter(carSeedDTO -> {
                    boolean isValid = validationUtil.isValid(carSeedDTO);
                    sb.append(isValid ? String.format("Successfully imported %s - %s",
                                    carSeedDTO.getMake(), carSeedDTO.getModel())
                                    : "Invalid car")
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(carSeedDTO -> modelMapper.map(carSeedDTO, Car.class))
                .forEach(carRepository::save);

        return sb.toString();

    }

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {
        return null;
    }
}
