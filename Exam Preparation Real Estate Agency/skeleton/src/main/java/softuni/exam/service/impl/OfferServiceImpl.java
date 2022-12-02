package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.OfferImportDTO;
import softuni.exam.models.dto.OfferImportRootDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Offer;
import softuni.exam.models.entity.enums.ApartmentType;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Service
public class OfferServiceImpl implements OfferService {

    private static final Path OFFERS_PATH = Path.of("src/main/resources/files/xml/offers.xml");
    private static final String INVALID_OFFER = "Invalid offer";

    private static final String SUCCESSFUL_IMPORT_OFFER_FORMAT = "Successfully imported offer - %.2f";
    private AgentRepository agentRepository;
    private ApartmentRepository apartmentRepository;

    private OfferRepository offerRepository;
    private ModelMapper modelMapper;
    private Validator validator;
    private Unmarshaller unmarshaller;

    public OfferServiceImpl(AgentRepository agentRepository, ApartmentRepository apartmentRepository, OfferRepository offerRepository) throws JAXBException {
        this.agentRepository = agentRepository;
        this.apartmentRepository = apartmentRepository;
        this.offerRepository = offerRepository;
        this.modelMapper = new ModelMapper();
        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        JAXBContext context = JAXBContext.newInstance(OfferImportRootDTO.class);
        this.unmarshaller = context.createUnmarshaller();
    }


    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(OFFERS_PATH);
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        OfferImportRootDTO offerDTOS = (OfferImportRootDTO)
                this.unmarshaller.unmarshal(Files.newInputStream(OFFERS_PATH));

        for (OfferImportDTO dto : offerDTOS.getOffers()) {
            if(agentRepository.findAgentByFirstName(dto.getAgent().getName()).isEmpty()) {
                sb.append(INVALID_OFFER)
                        .append(System.lineSeparator());
                continue;
            }

            Set<ConstraintViolation<OfferImportDTO>> errors = validator.validate(dto);
            if(!errors.isEmpty()) {
                sb.append(INVALID_OFFER)
                        .append(System.lineSeparator());
            } else {
                Offer offer = modelMapper.map(dto, Offer.class);

                Agent agent = this.agentRepository.findAgentByFirstName(dto.getAgent().getName()).get();
                offer.setAgent(agent);

                Apartment apartment = this.apartmentRepository.findById(dto.getApartment().getId()).get();
                offer.setApartment(apartment);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate publishedOn = LocalDate.parse(dto.getPublishedOn(), formatter);
                offer.setPublishedOn(publishedOn);

                this.offerRepository.save(offer);

                sb.append(String.format(SUCCESSFUL_IMPORT_OFFER_FORMAT,offer.getPrice()))
                        .append(System.lineSeparator());
            }
        }
        return sb.toString().trim();
    }

    @Override
    public String exportOffers() {
        StringBuilder sb = new StringBuilder();

        List<Offer> offers = this.offerRepository.filterExportOffers(ApartmentType.three_rooms).get();

        offers.forEach(o -> {
            sb.append(o.toString()).append(System.lineSeparator());
        });

        return sb.toString().trim();
    }
}
