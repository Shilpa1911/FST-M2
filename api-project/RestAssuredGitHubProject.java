package Project;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class RestAssuredGitHubProject {
	RequestSpecification requestSpec;
	 ResponseSpecification responseSpec;
	String sshKey="ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQClEkumKTxkWT7LkVq8/M3pRwSCdud87OTQosZPFEsWTlhtq1tMpCud9fHgkfa36a28AVaYhugYSbTSsNTjxORPOd+hZ56NPgJZR2rvP4Vi+3ZldZ/jF3WULxYIlpeiKhfBDjY05v1z6ivTPW5T0P1jRlT5ttqhyKq7z2nh/wJSgfn60szFXwPytMODm+dTgtuY5IPC3SSSS3ovXntOF5TzdAWLJfhOnfCe07lum1BZu6IlNJxLxqXWn5fplvcU3Q/IB+Q5sApGRnE4aFkgk85V4rPw0XrWY9fxwyEOufFSdANoDBENWPrF7JWMvHBUQmCeKn0JRBh5QQnXo5SQkpzn";
	int sshId;
	@BeforeClass
	 public void setUp() {
	  requestSpec=new RequestSpecBuilder()
			 .setBaseUri("https://api.github.com")
			 .addHeader("Content_Type","application/json")
			 .addHeader("Authorization","token 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").build();
		 
	 }
	@Test(priority=1)
	public void postTest() {
		//Request body
		 
		 Map<String,String> reqBody=new java.util.HashMap<>();
		 reqBody.put("title","TestAPIKey");
		 reqBody.put("key",sshKey);		
		 
		 //Generate Response
		 Response response= given().spec(requestSpec).body(reqBody).when().post("/user/keys");
		 System.out.println("response body:"+response.getBody().asPrettyString());
		 //extract petId
		 sshId=response.then().extract().path("id");
		 //Assertions
		 response.then().spec(responseSpec);
		 response.then().statusCode(201).body("key",equalTo(sshKey));
		 }
	
	@Test(priority=2)
	public void getTest() {
		
		Response rsp=given().spec(requestSpec).pathParam("sshId",sshId)
				.when().get("/user/keys/{sshId}");
		System.out.println("Response:"+rsp);
		 rsp.then().statusCode(200).body("key",equalTo(sshKey));
			
	}
	@Test(priority=3)
	public void deleteTest() {
		Response rsp=given().spec(requestSpec).pathParam("sshId",sshId)
				.when().delete("/user/keys/{sshId}");
		System.out.println("Response:"+rsp.getBody().asPrettyString());
		 rsp.then().statusCode(204);
	}
}
