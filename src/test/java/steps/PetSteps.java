package steps;

import domain.Pet;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;

import java.util.Optional;

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

    public Response deletePet(int petId) {
        return super.given()
                .when()
                .basePath(BASE_PATH + petId)
                .log().all()
                .delete()
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
}
