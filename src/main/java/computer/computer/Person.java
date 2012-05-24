package computer.computer;

public class Person {

	private long id;
	private String name;
	
	public Person() {
	}
	
	public Person(String name) {
		super();
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Osoba: id = " + this.id + " | name = " + this.name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
