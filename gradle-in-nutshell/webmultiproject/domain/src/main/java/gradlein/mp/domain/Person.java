package gradlein.mp.domain;

public class Person {
	private long id;
	private String firstName;
	private String lastName;

	public Person(long id, String firstName, String lastName) {
		this(firstName, lastName);
		this.id = id;
	}

	public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;

	}

	public Person() {

	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "[" + id + ", " + firstName + ", " + lastName +"]";
	}
}
