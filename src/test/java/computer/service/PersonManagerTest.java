package computer.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import computer.computer.Computer;
import computer.computer.Person;

public class PersonManagerTest {
	
	PersonManager personManager = new PersonManager();
	ComputerManager computerManager = new ComputerManager();
	
	private final static String NAME_1 = "Jan";
	
	private final static String NAME_2 = "Nowy Jan";
	
	private final static String KOMP_NAME_1 = "KompJana";
	private final static int KOMP_PROCESSOR_1 = 1;
	
	private final static String KOMP_NAME_2 = "NowyKompJana";
	private final static int KOMP_PROCESSOR_2 = 2;
	
	// person
	private final static String PERSON_NAME_1 = "Janek";
	
	@Before
	//@Test
	public void testZaJedenUsmiech() {
		//System.out.println(":D");
	}
	
	@After
	public void testZaJeden() {
		//System.out.println(":/");
	}
	
	@Test
	public void testConnection() {
		assertNotNull(personManager.getConnection());
	}
	
	@Test
	public void testAdding() {
		Person comp = new Person(NAME_1);
		
		personManager.clearPersons();
		
		assertEquals(1, personManager.addPerson(comp));
		
		List<Person> persons = personManager.getAllPerson();
		Person personRetrivet = persons.get(0);
		
		assertEquals(NAME_1, personRetrivet.getName());
	}
	
	@Test
	public void testGetByName() {
		Person person = new Person(NAME_1);
		personManager.clearPersons();
		personManager.addPerson(person);
		person = new Person(NAME_2);
		personManager.addPerson(person);
		
		Person personRetrivet = personManager.getPersonByName(NAME_1);
		assertEquals(NAME_1, personRetrivet.getName());
		
		personRetrivet = personManager.getPersonByName(NAME_2);
		assertEquals(NAME_2, personRetrivet.getName());
	}
	
	@Test
	public void testDeleteById() {
		Person person = new Person(NAME_1);
		
		personManager.clearPersons();
		personManager.addPerson(person);
		
		person = personManager.getPersonByName(NAME_1);
		assertEquals(1, personManager.removeOneById(person.getId()));
	}
	
	@Test
	public void testUpdateById() {
		Person person = new Person(NAME_1);
		
		personManager.clearPersons();
		personManager.addPerson(person);
		
		person = personManager.getPersonByName(NAME_1);
		
		person.setName(NAME_2);
		
		personManager.updatePersonById(person.getId(), person);
		
		person = personManager.getPersonByName(NAME_2);
		
		assertEquals(NAME_2, person.getName());
	}
	
	@Test
	public void testGetPresonComputers() {
		personManager.clearPersons();
		computerManager.clearComputers();
		
		Person person = new Person(PERSON_NAME_1);
		personManager.addPerson(person);
		person = personManager.getPersonByName(PERSON_NAME_1);
		
		List<Computer> compList = new ArrayList<Computer>();
		
		compList.add(new Computer(KOMP_NAME_1, KOMP_PROCESSOR_1));
		compList.add(new Computer(KOMP_NAME_2, KOMP_PROCESSOR_2));
		
		computerManager.addComputersToPerson(compList, person);
		
		compList = personManager.getPresonComputers(person);
		
		for (Computer computer : compList) {
			assertEquals(person.getId(), computer.getIdperson());
		}
	}
}
