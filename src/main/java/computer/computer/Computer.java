package computer.computer;

public class Computer {
	
	private long id;
	private long idperson;
	
	private int processor;
	private String name;
	
	public Computer() {
	}
	
	public Computer(String name, int processor) {
		super();
		this.name = name;
		this.processor = processor;
	}
	
	public Computer(int id, String name, int processor) {
		super();
		this.id = id;
		this.name = name;
		this.processor = processor;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getProcessor() {
		return processor;
	}

	public void setProcessor(int processor) {
		this.processor = processor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Komp: id = " + this.id + " | name = " + this.name + " | processor = " + this.processor;
	}

	public long getIdperson() {
		return idperson;
	}

	public void setIdperson(long idperson) {
		this.idperson = idperson;
	}

}
