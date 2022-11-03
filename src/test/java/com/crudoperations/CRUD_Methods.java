package com.crudoperations;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import reusable_bodycontents.reusablesbody;

public class CRUD_Methods {

	public static String Userid;

	@Test
	public void CallingCRUDMethods() {

		GET_Call();
		POST_Call();
		PUT_Call();
		Delete_Call();
	}

//	@Test
	// RequestSpecification Way Implementation
	public void GET_Call() {

		RequestSpecification request = RestAssured.given();
		request.baseUri("https://reqres.in/api");
		request.header("Content-Type", "Application/Json");
		Response response = request.get("/users?page=2");

		System.out.println("\n" + "*************");
		System.out.println(response.asPrettyString());

		ValidatableResponse ResponseOutput = response.then();

		System.out.println("Printing the log data of the GET request");
//		ResponseOutput.log().all();
		System.out.println("Printing GET request log data completed");
		ResponseOutput.statusCode(200);

	}

	// BDD Way Implementation of Get call
//	public void GET() {
//
//		RestAssured.baseURI="https://reqres.in/api";
//		
//		//given
//		given().header("Content-Type","Application/Json")
//		
//		//when
//		.when().get("/users?page=2")
//		
//		//then
//		.then().assertThat().statusCode(200).log().all();
//		
//		System.out.println("\n"+"Getting the list of users in Page 2");
//		
//		String Prettyprint = 
//				//given
//				given().header("Content-Type","Application/Json")
//				//when
//				.when().get("/users?page=2")
//				//then
//				.then().extract().response().asPrettyString();
//		
//		System.out.println("\n"+"***************");
//		
//		System.out.println("\n"+Prettyprint);
//		
//	}

//	@Test
	// BDD Way Implementation
	public void POST_Call() {

		Object Fname = "Saikumar";
		Object Lname = "D";

		String Postresponse = given().baseUri("https://reqres.in/api").contentType(ContentType.JSON)
				.body(reusablesbody.postcreateuserbody())

				.when().post("/users")

				.then().assertThat().statusCode(201).extract().response().asPrettyString();

		System.out.println("Printing the log data of the POST request");
		System.out.println("\n" + "*************");
		System.out.println(Postresponse);

		JsonPath js = new JsonPath(Postresponse);

		String firstname = js.getString("first_name");
		Assert.assertEquals(firstname, Fname);
		System.out.println("The created first name is same as given. i.e., - " + firstname);

		String lastname = js.getString("last_name");
		Assert.assertEquals(lastname, Lname);
		System.out.println("The created last name is same as given. i.e., - " + lastname);

		Userid = js.getString("id");
		System.out.println("The created user id is - " + Userid);

		System.out.println("Printing POST request log data completed");
	}

//	@Test
	// BDD Way Implementation
	public void PUT_Call() {

		Object ModifiedLname = "Deekonda";
		System.out.println("Created Userid in Post Request is "+Userid);
		System.out.println("The PathURL for PUT Request is : /users/"+Userid);
		
		String Putresponse = RestAssured.given().baseUri("https://reqres.in/api").contentType(ContentType.JSON)
				.body(reusablesbody.putuserbody())

				.when().put("/users/" + Userid)

				.then().assertThat().statusCode(200).extract().response().asPrettyString();

		System.out.println("\n" + "*************");
		System.out.println(Putresponse);

		JsonPath js = new JsonPath(Putresponse);

		String lastname = js.getString("last_name");
		Assert.assertEquals(lastname, ModifiedLname);
		System.out.println("The modified last name is same as now given. i.e., - " + lastname);

	}

//	@Test
	public void Delete_Call() {

		System.out.println("\n" + "Printing DELETE request log data");
		System.out.println("The PathURL for Delete Request is : /users/"+Userid);

		String DeleteRes = RestAssured.given().baseUri("https://reqres.in/api").contentType(ContentType.JSON)

				.when().delete("/users/" + Userid)

				.then().extract().response().asPrettyString();

		System.out.println("Printing DELETE request log data completed");

		System.out.println("\n" + "*************");
		System.out.println("---"+DeleteRes+"---");
		RestAssured.given().baseUri("https://reqres.in/api").contentType(ContentType.JSON)

				.when().delete("/users/" + Userid)

				.then().assertThat().statusCode(204);

		System.out.println("The User data got deleted successfully");

	}

}
