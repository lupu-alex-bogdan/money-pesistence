package service;

import model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.PersonRepository;

import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person getPerson(long cnp) {
       Optional<Person> personOptional = personRepository.findById(cnp);
       return personOptional.orElse(null);
    }

//    public List<Person> getAllPersons() {
//        List<Person> persons = new ArrayList<>();
//        personRepository.findAll().forEach(persons::add);
//        return persons;
//    }

    public void addPerson(Person person) {
        personRepository.save(person);
    }
//
//    public void updatePerson(Person person) {
//        personRepository.save(person);
//    }
//
//    public void deletePerson(long cnp) {
//        personRepository.deleteById(cnp);
//    }
}
