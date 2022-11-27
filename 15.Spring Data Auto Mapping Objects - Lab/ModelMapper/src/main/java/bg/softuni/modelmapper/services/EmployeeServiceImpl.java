package bg.softuni.modelmapper.services;

import bg.softuni.modelmapper.entities.Address;
import bg.softuni.modelmapper.entities.Employee;
import bg.softuni.modelmapper.entities.dtos.CreateEmployeeDTO;
import bg.softuni.modelmapper.entities.dtos.EmployeeDTO;
import bg.softuni.modelmapper.repositories.AddressRepository;
import bg.softuni.modelmapper.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final AddressRepository addressRepository;
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(AddressRepository addressRepository, EmployeeRepository employeeRepository) {
        this.addressRepository = addressRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public Employee create(CreateEmployeeDTO employeeDTO) {
        ModelMapper mapper = new ModelMapper();

        Employee employee = mapper.map(employeeDTO, Employee.class);

        Optional<Address> address = this.addressRepository.findByCountryAndCity(
                employee.getAddress().getCountry(),
                employee.getAddress().getCity());

        address.ifPresent(employee::setAddress);
        return this.employeeRepository.save(employee);
    }

    @Override
    public List<EmployeeDTO> findAll() {
        ModelMapper mapper = new ModelMapper();

       return this.employeeRepository.findAll()
               .stream()
               .map(e -> mapper.map(e, EmployeeDTO.class))
               .collect(Collectors.toList());
    }
}
