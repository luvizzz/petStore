package steps;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;

public abstract class BaseSteps {
    protected final static String ROOT_URI = "https://petstore.swagger.io/v2/"; //todo: consider moving to property file

    protected RequestSpecification given() {
        RequestSpecification spec = new RequestSpecBuilder()
                .setBaseUri(ROOT_URI)
                .setContentType(ContentType.JSON)
                .build();

        return RestAssured
                .given()
                .spec(spec);
    }

    public void assertResponseCode(int actual, int expected) {
        Assertions.assertThat(actual)
                .as("Validating response code is {}", expected)
                .isEqualTo(expected);
    }
}
