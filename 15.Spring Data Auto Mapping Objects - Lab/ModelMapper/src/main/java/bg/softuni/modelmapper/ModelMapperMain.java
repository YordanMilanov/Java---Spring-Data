package bg.softuni.modelmapper;


import bg.softuni.modelmapper.entities.Address;
import bg.softuni.modelmapper.entities.Employee;
import bg.softuni.modelmapper.entities.dtos.EmployeeDTO;
import org.modelmapper.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;


//@Component
public class ModelMapperMain implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        ModelMapper modelMapper = new ModelMapper();

        Address address = new Address("Bulgaria", "Sofia");
        Employee employee = new Employee(1, "First", "last", BigDecimal.TEN, LocalDate.now(), address);
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);

        //Property Map Info - FIRST WAY TO DO IT
        //когато имената на field-овете в class-a и DTO-то не съвпадат използваме custom mapping, което става чрез PropertyMap-a.
        //PropertyMap<Класа от който вземаме, класа на който подаваме>, след което трябва да имплементираме configure метода.
        //което става чрез map(). метода.
        //map().setCity()(source.getAddress().getCity());
        // setCity() - setter of the DTO city field
        //source - the class of the source(Employee)
        //getAddress() - getter of the address of the source(employee)
        //getCity() - getter of the city field of the address class(); - nested
        //custom mapping-a се ползва само за различните полета, всички останали, които съвпадат и modelMapper-a може да ги направи не се дефинират.

        /*
        PropertyMap<Employee, EmployeeDTO> propertyMap = new PropertyMap<Employee, EmployeeDTO>() {
            @Override
            protected void configure() {
                map().setCity(source.getAddress().getCity());
            }
        };
        */

        //TYPE MAP - SECOND WAY TO DO IT
        //TypeMap<Employee, EmployeeDTO> typeMap = modelMapper.createTypeMap(Employee.class, EmployeeDTO.class);
        //typeMap.addMappings(mapping -> mapping.map(source -> source.getAddress().getCity(), //source
        //        (destination, value) -> destination.setCity(value.toString()))); //destination
        // the destination can be defined with a shortcut : EmployeeDTO::setCity;

        // и двата начина имат mapper.Validate() метод, който валидира дали сме оспяли да мапнем всички неща, и хвърля ексепшън ако не сме, полезен е.

        System.out.println(employeeDTO);
    }
}
