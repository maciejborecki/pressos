package client;

import static org.junit.Assert.*;

import java.io.IOException;

import gradlein.mp.client.Client;

import org.apache.http.client.ClientProtocolException;
import org.junit.Before;
import org.junit.Test;

public class ClientIntegTest {
	
	Client client = new Client();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testDefaultSize() throws ClientProtocolException, IOException {
		assertEquals(2, client.list().size());
	}
	
	@Test
	public void testAddNewPerson() throws ClientProtocolException, IOException {
		client.add("xx", "yy");
		assertEquals(3, client.list().size());
	}


}
