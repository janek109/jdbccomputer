package computer.service;

import java.util.List;

import computer.computer.Computer;

public interface IComputerManager {

	public abstract int addComputer(Computer comp);

	public abstract int removeOneById(long id);

	public abstract Computer getComputerByName(String name);

	public abstract void updateComputerById(Long id, Computer comp);

	public abstract List<Computer> getAllComputers();

}