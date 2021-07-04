package cases;

import domain.Category;
import domain.Pet;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.util.List;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_METHOD_NOT_ALLOWED;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;

public class UpdatePetByPutTests extends BaseTest {
    @Test
    @Tag(SMOKE_TAG)
    void updatePet_expectSuccess() {
        //GIVEN
        Pet updatedPet = new Pet.Builder()
                .withId(pet.getId())
                .withCategory(new Category(999, "myUpdatedCategoryName"))
                .withName("someUpdatedTestName")
                .withPhotoUrls(List.of("some.updated.url.com", "some.other.updated.url.com"))
                .withTags(List.of(Utils.randomTag()))
                .withStatus(Utils.randomStatus())
                .build();

        //WHEN
        Response response = petSteps.updatePet(updatedPet);

        //THEN
        petSteps.assertResponseCode(response.statusCode(), SC_OK);

        //WHEN
        response = petSteps.getPet(updatedPet.getId());

        //THEN
        petSteps.assertResponseContains(response, updatedPet);
    }

    @Test
    void updatePetUsingInvalidId_expectBadRequest() {
        //GIVEN
        String petBody = "{\"id\" : \"someBadlyFormedId\"}";

        //WHEN
        Response response = petSteps.updatePet(petBody);

        //THEN
        petSteps.assertResponseCode(response.statusCode(), SC_BAD_REQUEST);
    }

    @Test
    void updateNonexistantPet_expectNotFound() {
        //GIVEN
        petSteps.deletePet(NULL_API_KEY, pet.getId());

        //WHEN
        Response response = petSteps.updatePet(pet);

        //THEN
        petSteps.assertResponseCode(response.statusCode(), SC_NOT_FOUND);
    }

    @Test
    void updatePetUsingBadlyFormattedBody_expectMethodNotAllowed() {
        //GIVEN
        String petBody = String.format("{\"id\" : \"%s\", \"someBadProperty\":\"someBadValue\" }", pet.getId());

        //WHEN
        Response response = petSteps.updatePet(petBody);

        //THEN
        petSteps.assertResponseCode(response.statusCode(), SC_METHOD_NOT_ALLOWED);
    }
}
