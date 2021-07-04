package steps;

import domain.Pet;
import domain.Tag;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.SoftAssertions;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.http.HttpStatus.SC_OK;

public class PetSteps extends BaseSteps {
    final String BASE_PATH = "pet/";

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

    public Response deletePet(String apiKey) {
        RequestSpecification request = super.given()
                .when()
                .basePath(BASE_PATH);
        if(apiKey != null) {
            request.header("apiKey", apiKey);
        }
        return request
                .log().all()
                .delete()
                .then()
                .log().all()
                .extract().response();
    }

    public Response deletePet(String apiKey, Long petId) {
        return deletePet(apiKey, String.valueOf(petId));
    }

    public Response deletePet(String apiKey, String petId) {
        RequestSpecification request = super.given()
                .when()
                .basePath(BASE_PATH);
        if(apiKey != null) {
            request.header("apiKey", apiKey);
        }
        return request
                .log().all()
                .delete(petId)
                .then()
                .log().all()
                .extract().response();
    }

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
