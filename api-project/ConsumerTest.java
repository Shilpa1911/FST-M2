package Project;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.HashMap;
import java.util.Map;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import static io.restassured.RestAssured.given;




@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {
	
	
    //Header object
	Map<String, String> headers=new HashMap<>();
	//Resource path
	String resourcePath="/api/users";
	//Generate a contract
	@Pact(consumer="UserConsumer",provider="UserProvider")
	public RequestResponsePact createPact(PactDslWithProvider builder) {
		//Add the headers
		headers.put("Content-Type", "application/json");
		//Create the JSON body for request and response
		DslPart requestResponseBody=new PactDslJsonBody()
				.numberType("id",123)
				.stringType("firstName","Shilpa")
				.stringType("lastName","karanam")
				.stringType("email","shilpa@example.com");
		
		//write the fragment pact
		return builder.given("A request to create a user")
				.uponReceiving("A request to create a user")
				.method("POST")
				.headers(headers)
				.path(resourcePath)
				.body(requestResponseBody)
				.willRespondWith()
				.status(201)
				.body(requestResponseBody)
				.toPact();
	}
	
	@Test
	@PactTestFor(providerName="UserProvider",port="8282")
	public void consumerTest() {
		//BaseURI
		String requestsURI="http://localhost:8202"+resourcePath;
		
		//request body
		Map<String,Object> reqBody=new HashMap<>();
		reqBody.put("id",123);
		reqBody.put("firstName", "shilpa");
		reqBody.put("lastName","karanam");
		reqBody.put("email","shilpa@example.com");
		
		//generate response
		given().headers(headers).body(reqBody).log().all().
		when().post(requestsURI).
		then().statusCode(201).log().all();
		
	}
	
	
	
	
	
	
	
	
	
	
}
