package steps;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
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
                .addFilter(new AllureRestAssured())
                .setBaseUri(ROOT_URI)
                .setContentType(ContentType.JSON)
                .build();

        return RestAssured
                .given()
                .spec(spec);
    }

    @Step("Asserting that response status code is {0}")
    public void assertResponseCode(int actual, int expected) {
        Assertions.assertThat(actual)
                .as("Validating response code is {}", expected)
                .isEqualTo(expected);
    }

    public void waitAWhile() {
        waitAWhile(500L);
    }

    @Step("Waiting a while ({0} ms)")
    public void waitAWhile(Long time) {
        LOG.info(String.format("Waiting a bit (%d ms)...", time));
        try {
            Thread.sleep(time); //todo: consider moving time amount to a property file. Can be related to specific env. performance
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
