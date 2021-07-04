package steps;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;
import java.util.logging.Logger;

public abstract class BaseSteps {
    private final static Logger LOG = Logger.getLogger(BaseSteps.class.getSimpleName());

    protected final static String ROOT_URI = "https://petstore.swagger.io/v2/"; //todo: consider moving to property file

    protected RequestSpecification given() {

        waitAWhile(); //there are some performance issues in the response. Waiting before each request allows DB to persist properly.

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

    private void waitAWhile() {
        LOG.info("Waiting a bit...");
        try {
            Thread.sleep(500); //todo: consider moving to a property file. Can be related to specific env. performance
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
