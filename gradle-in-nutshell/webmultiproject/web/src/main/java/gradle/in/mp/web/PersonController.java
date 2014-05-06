package gradle.in.mp.web;


import gradlein.mp.domain.Person;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 
 * @author Maciej Borecki
 *
 */
@Controller
public class PersonController {

	private AtomicLong idCounter = new AtomicLong();
	private Map<Long, Person> persons = new ConcurrentHashMap<Long, Person>();

	@PostConstruct
	public void init() {
		long id = idCounter.get();
		this.persons.put(id, new Person(id, "John", "Doe"));
		id = idCounter.incrementAndGet();
		this.persons.put(id, new Person(id, "Jane", "Roe"));
	}
	
	@RequestMapping("/*")
	@ResponseBody
	public String defaultResponse(){
		return "default mapping";
	}

	@RequestMapping("/persons/{id}")
	public @ResponseBody ResponseEntity<Person> getPerson(
			@PathVariable("id") Long id) {
		Person result = this.persons.get(id);
		if (result != null) {
			return new ResponseEntity<Person>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value="/persons")
	@ResponseBody
	public  Collection<Person> getPersons() {
		return this.persons.values();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/persons")
	public ResponseEntity<Person> addPerson(@RequestBody Person person, UriComponentsBuilder builder) {
		System.out.println("Accepting post request with:" + person);
		long id = this.idCounter.incrementAndGet();
		person.setId(id);
		this.persons.put(id, person);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/aggregators/orders/{id}")
				.buildAndExpand(id).toUri());
		
		return new ResponseEntity<Person>(person, headers, HttpStatus.CREATED);
	}
}
