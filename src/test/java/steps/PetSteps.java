package steps;

import domain.Pet;
import domain.Tag;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.SoftAssertions;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.apache.http.HttpStatus.SC_OK;

public class PetSteps extends BaseSteps {
    final String BASE_PATH = "pet/";

    @Step("Adding a new Pet")
    public Response addPet(Pet body) {
        return super.given()
                .when()
                .basePath(BASE_PATH)
                .log().all()
                .body(body)
                .post()
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Adding a new Pet")
    public Response addPet(String body) {
        return super.given()
                .when()
                .basePath(BASE_PATH)
                .log().all()
                .body(body)
                .post()
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Updating Pet using PUT")
    public Response updatePetByPut(Pet body) {
        return super.given()
                .when()
                .basePath(BASE_PATH)
                .log().all()
                .body(body)
                .put()
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Updating Pet using PUT")
    public Response updatePetByPut(String body) {
        return super.given()
                .when()
                .basePath(BASE_PATH)
                .log().all()
                .body(body)
                .put()
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Updating Pet using POST")
    public Response updatePetByPost(Long petId, String name, String status) {
        String body = createUpdatePostBody(name, status);
        return super.given()
                .when()
                .basePath(BASE_PATH)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .config(RestAssured.config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .log().all()
                .body(body)
                .post(String.valueOf(petId))
                .then()
                .log().all()
                .extract().response();
    }

    private String createUpdatePostBody(String name, String status) {
        String body = "";
        if (name != null && !name.equals("")) {
            body = "name=" + name;
        }
        if (status != null && !status.equals("")) {
            if (!body.equals("")) {
                body += "&";
            }
            body += "status=" + status;
        }
        return body;
    }

    @Step("Uploading image for Pet {0}")
    public Response uploadPetImage(Long petId, String metadata, String filePath, String fileType) {
        RequestSpecification request =  super.given()
                .when()
                .basePath(BASE_PATH)
                .header("Content-Type", "multipart/form-data")
                .config(RestAssured.config().encoderConfig(encoderConfig().encodeContentTypeAs("multipart/form-data", ContentType.TEXT)))
                .log().all();

        if (metadata != null) {
            request.multiPart("additionalMetadata", metadata);
        }

        if (filePath != null) {
            request.multiPart("file", filePath, fileType);
        }

        return request
                .accept("application/json")
                .post(petId + "/uploadImage")
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Getting Pet {0}")
    public Response getPet(Long id) {
        return super.given()
                .when()
                .basePath(BASE_PATH)
                .log().all()
                .get(String.valueOf(id))
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Getting Pet without providing ID")
    public Response getPetWithoutId() {
        return super.given()
                .when()
                .basePath(BASE_PATH)
                .log().all()
                .get()
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Finding Pet by Status")
    public Response findPetByStatus() {
        return super.given()
                .when()
                .basePath(BASE_PATH)
                .log().all()
                .get("findByStatus")
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Finding Pet by Statuses {0}")
    public Response findPetByStatus(String... statuses) {
        RequestSpecification request = super.given()
                .when()
                .basePath(BASE_PATH);
        Arrays.stream(statuses).forEach(status -> request.queryParam("status", status));
        return request.log().all()
                .get("findByStatus")
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Finding Pet by Tags")
    public Response findPetByTags(List<Tag> tags) {
        RequestSpecification request = super.given()
                .when()
                .basePath(BASE_PATH);
        tags.forEach(tag -> request.queryParam("tags", tag.getName()));
        return request.log().all()
                .get("findByTags")
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Deleting Pet without providing ID")
    public Response deletePet(String apiKey) {
        RequestSpecification request = super.given()
                .when()
                .basePath(BASE_PATH);
        if (apiKey != null) {
            request.header("apiKey", apiKey);
        }
        return request
                .log().all()
                .delete()
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Deleting Pet with ID {0}")
    public Response deletePet(String apiKey, Long petId) {
        return deletePet(apiKey, String.valueOf(petId));
    }

    public Response deletePet(String apiKey, String petId) {
        RequestSpecification request = super.given()
                .when()
                .basePath(BASE_PATH);
        if (apiKey != null) {
            request.header("apiKey", apiKey);
        }
        return request
                .log().all()
                .delete(petId)
                .then()
                .log().all()
                .extract().response();
    }

    @Step("Asserting response contains expected Pet")
    public void assertResponseContains(Response response, Pet expected) {
        assertResponseCode(response.statusCode(), SC_OK);
        Pet actual = response.body().as(Pet.class);

        SoftAssertions.assertSoftly(softly -> {
            Optional.ofNullable(expected.getId()).ifPresentOrElse(value -> softly.assertThat(actual.getId()).isEqualTo(value), () -> softly.assertThat(actual.getId()).isNotNull());
            Optional.ofNullable(expected.getName()).ifPresentOrElse(value -> softly.assertThat(actual.getName()).isEqualTo(value), () -> softly.assertThat(actual.getName()).isNotNull());
            Optional.ofNullable(expected.getPhotoUrls()).ifPresentOrElse(value -> softly.assertThat(actual.getPhotoUrls()).isEqualTo(value), () -> softly.assertThat(actual.getPhotoUrls()).isNotNull());

            Optional.ofNullable(expected.getCategory()).ifPresent(value -> softly.assertThat(actual.getCategory()).isEqualTo(value));
            Optional.ofNullable(expected.getTags()).ifPresent(value -> softly.assertThat(actual.getTags()).isEqualTo(value));
            Optional.ofNullable(expected.getStatus()).ifPresent(value -> softly.assertThat(actual.getStatus()).isEqualTo(value));
        });
    }

    @Step("Asserting response contains list of expected Pets")
    public void assertResponseContainsList(Response response, List<Long> expectedListOfIds) {
        assertResponseCode(response.statusCode(), SC_OK);
        List<Map<String, Object>> actual = response.body().as(List.class);

        List<Long> actualIds = actual.stream()
                .map(entry -> ((Number) entry.get("id")).longValue())
                .collect(Collectors.toList());

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(actual.size()).isEqualTo(expectedListOfIds.size());
            softly.assertThat(actualIds).containsExactlyInAnyOrderElementsOf(expectedListOfIds);
        });
    }
}
