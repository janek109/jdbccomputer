package computer.service;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import computer.computer.Computer;
import computer.computer.Person;
import computer.service.ComputerManager;

public class ComputerManagerTest {
	
	ComputerManager computerManager = new ComputerManager();
	PersonManager personManager = new PersonManager();
	
	private final static String NAME_1 = "KompJana";
	private final static int PROCESSOR_1 = 1;
	
	private final static String NAME_2 = "NowyKompJana";
	private final static int PROCESSOR_2 = 2;
	
	// person
	private final static String PERSON_NAME_1 = "Jan";
	//private final static String PERSON_NAME_2 = "Paulina";
	
	@Before
	//@Test
	public void testZaJedenUsmiech() {
		//System.out.println(":)");
	}
	
	@After
	public void testZaJeden() {
		//System.out.println(":D");
	}
	
	@Test
	public void testConnection() {
		assertNotNull(computerManager.getConnection());
	}
	
	@Test
	public void testAdding() {
		Computer comp = new Computer(NAME_1, PROCESSOR_1);
		
		computerManager.clearComputers();
		
		assertEquals(1, computerManager.addComputer(comp));
		
		List<Computer> computers = computerManager.getAllComputers();
		Computer computerRetrivet = computers.get(0);		
		
		assertEquals(NAME_1, computerRetrivet.getName());
		assertEquals(PROCESSOR_1, computerRetrivet.getProcessor());
	}
	
	@Test
	public void testGetByName() {
		Computer comp = new Computer(NAME_1, PROCESSOR_1);
		computerManager.clearComputers();
		computerManager.addComputer(comp);
		
		Computer computerRetrivet = computerManager.getComputerByName(NAME_1);
		
		assertEquals(NAME_1, computerRetrivet.getName());
		assertEquals(PROCESSOR_1, computerRetrivet.getProcessor());
	}
	
	@Test
	public void testDeleteById() {
		Computer comp = new Computer(NAME_1, PROCESSOR_1);
		
		computerManager.clearComputers();
		computerManager.addComputer(comp);
		
		comp = computerManager.getComputerByName(NAME_1);
		assertEquals(1, computerManager.removeOneById(comp.getId()));
	}
	
	@Test
	public void testUpdateById() {
		Computer comp = new Computer(NAME_1, PROCESSOR_1);
		
		computerManager.clearComputers();
		computerManager.addComputer(comp);
		
		comp = computerManager.getComputerByName(NAME_1);
		
		comp.setName(NAME_2);
		comp.setProcessor(PROCESSOR_2);
		
		computerManager.updateComputerById(comp.getId(), comp);
		
		comp = computerManager.getComputerByName(NAME_2);
		
		assertEquals(NAME_2, comp.getName());
		assertEquals(PROCESSOR_2, comp.getProcessor());
	}
	
	@Test
	public void testAddComputerToPerson() {
		computerManager.clearComputers();
		personManager.clearPersons();
		
		Computer comp = new Computer(NAME_1, PROCESSOR_1);
		
		Person person = new Person(PERSON_NAME_1);
		
		personManager.addPerson(person);
		
		person = personManager.getPersonByName(PERSON_NAME_1);
		
		try {
			assertEquals(1, computerManager.addComputerToPerson(comp, person));
			
			List<Computer> computers = computerManager.getAllComputers();
			Computer computerRetrivet = computers.get(0);
			
			assertEquals(NAME_1, computerRetrivet.getName());
			assertEquals(PROCESSOR_1, computerRetrivet.getProcessor());
			assertEquals(person.getId(), computerRetrivet.getIdperson());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAddComputersToPerson() {
		computerManager.clearComputers();
		personManager.clearPersons();
		
		Person person = new Person(PERSON_NAME_1);
		personManager.addPerson(person);
		person = personManager.getPersonByName(PERSON_NAME_1);
		
		List<Computer> compList = new ArrayList<Computer>();
		
		compList.add(new Computer(NAME_1, PROCESSOR_1));
		compList.add(new Computer(NAME_2, PROCESSOR_2));
		
		computerManager.addComputersToPerson(compList, person);
		
		compList = computerManager.getAllComputers();

		for (Computer computer : compList) {
			assertEquals(person.getId(), computer.getIdperson());
		}
	}
}
