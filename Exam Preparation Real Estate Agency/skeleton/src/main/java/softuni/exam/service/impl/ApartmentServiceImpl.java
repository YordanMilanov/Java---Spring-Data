package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ApartmentImportDTO;
import softuni.exam.models.dto.ApartmentImportRootDTO;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    private static final Path APARTMENTS_PATH = Path.of("src/main/resources/files/xml/apartments.xml");
    private static final String INVALID_APARTMENT = "Invalid Apartment";

    private static final String SUCCESSFUL_IMPORT_APARTMENT_FORMAT = "Successfully imported apartment %s - %.2f";
    private ApartmentRepository apartmentRepository;
    private TownRepository townRepository;
    private ModelMapper modelMapper;
    private Validator validator;

    private Unmarshaller unmarshaller;

    @Autowired
    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, TownRepository townRepository) throws JAXBException {
        this.apartmentRepository = apartmentRepository;
        this.townRepository = townRepository;
        this.modelMapper = new ModelMapper();
        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        JAXBContext context = JAXBContext.newInstance(ApartmentImportRootDTO.class);
        this.unmarshaller = context.createUnmarshaller();
    }


    @Override
    public boolean areImported() {
        return this.apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(APARTMENTS_PATH);
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        ApartmentImportRootDTO apartmentDTOS = (ApartmentImportRootDTO)
                this.unmarshaller.unmarshal(Files.newInputStream(APARTMENTS_PATH));

        for (ApartmentImportDTO apartmentImportDTO : apartmentDTOS.getApartments()) {
            Set<ConstraintViolation<ApartmentImportDTO>> errors = validator.validate(apartmentImportDTO);
            String currentTownName = apartmentImportDTO.getTown();
            double currentArea = apartmentImportDTO.getArea();
            if(!errors.isEmpty() || this.apartmentRepository.findByTown_TownNameAndArea(currentTownName, currentArea).isPresent()){
                sb.append(INVALID_APARTMENT).append(System.lineSeparator());
            } else {
                Apartment apartment = modelMapper.map(apartmentImportDTO, Apartment.class);
                Town town = this.townRepository.findByTownName(apartmentImportDTO.getTown()).get();
                apartment.setTown(town);
                sb.append(String.format(
                        SUCCESSFUL_IMPORT_APARTMENT_FORMAT,
                        apartment.getApartmentType().toString(),
                        apartment.getArea()))
                        .append(System.lineSeparator());
                this.apartmentRepository.save(apartment);
            }
        }
        return sb.toString().trim();
    }
}
