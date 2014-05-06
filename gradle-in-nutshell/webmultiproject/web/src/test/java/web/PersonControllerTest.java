package web;

import static org.junit.Assert.assertEquals;
import gradle.in.mp.web.PersonController;
import gradlein.mp.domain.Person;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 
 * @author Maciej Borecki
 *
 */
public class PersonControllerTest {
	private PersonController pContoler;

	@Before
	public void setUp() throws Exception {
		this.pContoler = new PersonController();
		this.pContoler.init();
	}

	@Test
	public void testDefaultLength() {
		assertEquals(2, pContoler.getPersons().size());
	}

	@Test
	public void testAdd(){
		System.out.println("testAdd");
		ResponseEntity<Person> personWrapper = pContoler.addPerson(new Person(0l,
				"Chris", "Christopher"), UriComponentsBuilder.newInstance());
		assertEquals(3, pContoler.getPersons().size());
		Person lastPerson = this.pContoler.getPerson(personWrapper.getBody().getId()).getBody();
		assertEquals("Chris", lastPerson.getFirstName());
		assertEquals("Christopher", lastPerson.getLastName());
	}
	
}
