package computer.service;

import java.util.List;

import computer.computer.Person;

public interface IPersonManager {

	public abstract int addPerson(Person person);

	public abstract int removeOneById(long id);

	public abstract Person getPersonByName(String name);

	public abstract void updatePersonById(Long id, Person comp);

	public abstract List<Person> getAllPerson();

}