package api;

import Specifications.Specifications;
import auth.AuthData;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class AuthTest {
    private final static String URL = "http://activ-admin-test.akb-it.ru/administration/api/v1";

    @Test
    public void checkAuth(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        AuthData credentials = new AuthData("admin", "123123");
        String authorization =
                given()
                        .contentType(ContentType.URLENC)
                        .formParams("username", credentials.getUsername())
                        .formParams("password", credentials.getPassword())
                .when()
                        .post("/authorization/login")
                .then()
                        .log().all().
                        extract().response().getHeader("Authorization");

        Assert.assertNotNull(authorization);
    }
}
