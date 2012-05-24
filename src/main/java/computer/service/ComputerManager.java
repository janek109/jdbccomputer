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

public class ComputerManager implements IComputerManager {
	
	private Connection connection;
	
	private String url = "jdbc:hsqldb:hsql://localhost/workdb";
	
	private String createTableComputer = 
			"CREATE TABLE Computer(id bigint GENERATED BY DEFAULT AS IDENTITY, " +
			"name varchar(20), " +
			"processor integer, " +
			"idperson bigint)";
	
	private Statement statement;
	
	private PreparedStatement addComputerStmt;
	private PreparedStatement deleteAllComputerStmt;
	private PreparedStatement deleteComputerByIdStmt;
	private PreparedStatement getAllComputersStmt;
	private PreparedStatement getComputerByName;
	private PreparedStatement updateComputerById;
	
	private PreparedStatement addComputerToPersonStmt;
	
	public ComputerManager() {
		try {
			connection = DriverManager.getConnection(url);
			statement = connection.createStatement();
			
			ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
			
			boolean tableExists = false;
			
			while (rs.next()) {
				if ("Computer".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableExists = true;
					break;
				}
			}
			
			if (!tableExists) {
				statement.executeUpdate(createTableComputer);
			}
			
			addComputerStmt = connection
					.prepareStatement("INSERT INTO Computer (name, processor) VALUES (?, ?)");
			deleteAllComputerStmt = connection
					.prepareStatement("DELETE FROM Computer");
			getAllComputersStmt = connection
					.prepareStatement("SELECT id, name, processor, idperson FROM Computer");
			deleteComputerByIdStmt = connection
					.prepareStatement("DELETE FROM Computer WHERE id = ?");
			getComputerByName = connection
					.prepareStatement("SELECT id, name, processor FROM Computer WHERE name LIKE ?");
			updateComputerById = connection
					.prepareStatement("UPDATE Computer SET name = ?, processor = ? WHERE id = ?");
			
			addComputerToPersonStmt = connection
					.prepareStatement("INSERT INTO Computer (name, processor, idperson) VALUES (?, ?, ?)");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	Connection getConnection() {
		return connection;
	}
	
	/* (non-Javadoc)
	 * @see computer.service.IComputerManager#addComputer(computer.computer.Computer)
	 */
	public int addComputer(Computer comp) {
		int count = 0;
		
		try {
			addComputerStmt.setString(1, comp.getName());
			addComputerStmt.setInt(2, comp.getProcessor());
			
			count = addComputerStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
	void clearComputers () {
		try {
			deleteAllComputerStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see computer.service.IComputerManager#removeOneById(long)
	 */
	public int removeOneById(long id) {
		int count = 0;
		
		try {
			deleteComputerByIdStmt.setLong(1, id);
			
			count = deleteComputerByIdStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
	/* (non-Javadoc)
	 * @see computer.service.IComputerManager#getComputerByName(java.lang.String)
	 */
	public Computer getComputerByName(String name) {
		try {
			getComputerByName.setString(1, name);
			
			ResultSet rs = getComputerByName.executeQuery();
			
			Computer comp = new Computer();
			
			while (rs.next()) {
				comp.setId(rs.getInt("id"));
				comp.setName(rs.getString("name"));
				comp.setProcessor(rs.getInt("processor"));
			}
			
			return comp;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see computer.service.IComputerManager#updateComputerById(java.lang.Long, computer.computer.Computer)
	 */
	public void updateComputerById(Long id, Computer comp) {
		try {
			updateComputerById.setString(1, (String)comp.getName());
			updateComputerById.setInt(2, (int)comp.getProcessor());
			updateComputerById.setLong(3, id);
			
			updateComputerById.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see computer.service.IComputerManager#getAllComputers()
	 */
	public List<Computer> getAllComputers() {
		List<Computer> computers = new ArrayList<Computer>();
		
		try {
			ResultSet rs = getAllComputersStmt.executeQuery();
			
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
	
	public int addComputerToPerson(Computer comp, Person person) throws SQLException
	{
		int count = 0;
		
		try {
			addComputerToPersonStmt.setString(1, comp.getName());
			addComputerToPersonStmt.setInt(2, comp.getProcessor());
			addComputerToPersonStmt.setLong(3, person.getId());
			
			count = addComputerToPersonStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
	public void addComputersToPerson(List<Computer> compList, Person person)
	{
		for (Computer computer : compList) {
			try {
				connection.setAutoCommit(false);
				
				addComputerToPerson(computer, person);
				
				connection.commit();
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
				if (connection != null) {
		            try {
		                System.err.print("Tranzakcja będzie odwołana!");
		                connection.rollback();
		            } catch(SQLException ex) {
		            	ex.printStackTrace();
		            }
				}
			}
		}
	}
	
}