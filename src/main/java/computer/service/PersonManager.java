package computer.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import computer.computer.Computer;
import computer.computer.Person;

public class PersonManager implements IPersonManager {
	
	private Connection connection;
	
	private String url = "jdbc:hsqldb:hsql://localhost/workdb";
	
	private String createTablePerson = "CREATE TABLE Person(id bigint GENERATED BY DEFAULT AS IDENTITY, name varchar(20))";
	
	private Statement statement;
	
	private PreparedStatement addPersonStmt;
	private PreparedStatement deleteAllPersonStmt;
	private PreparedStatement deletePersonByIdStmt;
	private PreparedStatement getAllPersonStmt;
	private PreparedStatement getPersonByName;
	private PreparedStatement updatePersonById;
	
	private PreparedStatement getPresonComputers;
	
	public PersonManager() {
		try {
			connection = DriverManager.getConnection(url);
			statement = connection.createStatement();
			
			ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
			
			boolean tableExists = false;
			
			while (rs.next()) {
				if ("Person".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableExists = true;
					break;
				}
			}
			
			if (!tableExists) {
				statement.executeUpdate(createTablePerson);
			}
			
			addPersonStmt = connection
					.prepareStatement("INSERT INTO Person (name) VALUES (?)");
			deleteAllPersonStmt = connection
					.prepareStatement("DELETE FROM Person");
			getAllPersonStmt = connection
					.prepareStatement("SELECT id, name FROM Person");
			deletePersonByIdStmt = connection
					.prepareStatement("DELETE FROM Person WHERE id = ?");
			getPersonByName = connection
					.prepareStatement("SELECT id, name FROM Person WHERE name LIKE ?");
			updatePersonById = connection
					.prepareStatement("UPDATE Person SET name = ? WHERE id = ?");
			
			getPresonComputers = connection
					.prepareStatement("SELECT * FROM Computer WHERE idperson = ?");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	Connection getConnection() {
		return connection;
	}
	
	/* (non-Javadoc)
	 * @see computer.service.IPersonManager#addPerson(computer.computer.Person)
	 */
	public int addPerson(Person person) {
		int count = 0;
		
		try {
			addPersonStmt.setString(1, person.getName());
			
			count = addPersonStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
	void clearPersons () {
		try {
			deleteAllPersonStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see computer.service.IPersonManager#removeOneById(long)
	 */
	public int removeOneById(long id) {
		int count = 0;
		
		try {
			deletePersonByIdStmt.setLong(1, id);
			
			count = deletePersonByIdStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
	/* (non-Javadoc)
	 * @see computer.service.IPersonManager#getPersonByName(java.lang.String)
	 */
	public Person getPersonByName(String name) {
		try {
			getPersonByName.setString(1, name);
			
			ResultSet rs = getPersonByName.executeQuery();
			
			Person person = new Person();
			
			while (rs.next()) {
				person.setId(rs.getInt("id"));
				person.setName(rs.getString("name"));
			}
			
			return person;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see computer.service.IPersonManager#updatePersonById(java.lang.Long, computer.computer.Person)
	 */
	public void updatePersonById(Long id, Person comp) {		
		try {
			updatePersonById.setString(1, (String)comp.getName());
			updatePersonById.setLong(2, id);
			
			updatePersonById.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see computer.service.IPersonManager#getAllPerson()
	 */
	public List<Person> getAllPerson() {
		List<Person> persons = new ArrayList<Person>();
		
		try {
			ResultSet rs = getAllPersonStmt.executeQuery();
			
			while (rs.next()) {
				Person c = new Person();
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				persons.add(c);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return persons;
	}
	
	public List<Computer> getPresonComputers(Person person) {
		List<Computer> computers = new ArrayList<Computer>();
		
		try {
			getPresonComputers.setLong(1, person.getId());
			
			ResultSet rs = getPresonComputers.executeQuery();
			
			while (rs.next()) {
				Computer c = new Computer();
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				c.setProcessor(rs.getInt("processor"));
				c.setIdperson(rs.getInt("idperson"));
				computers.add(c);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return computers;
	}

}
