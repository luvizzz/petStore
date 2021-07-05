package cases;

import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.apache.http.HttpStatus.SC_METHOD_NOT_ALLOWED;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;

public class GetPetTests extends BaseTest {
    @Test
    @Tag(SMOKE_TAG)
    void getPetById_expectSuccess() {
        //WHEN
        Response response = petSteps.getPet(pet.getId());

        //THEN
        petSteps.assertResponseContains(response, pet);
    }

    @Test
    void getPetWithoutMandatoryId_expectMethodNotAllowed() {
        //WHEN
        Response response = petSteps.getPetWithoutId();

        //THEN
        petSteps.assertResponseCode(response.statusCode(), SC_METHOD_NOT_ALLOWED);
    }

    @Test
    void getNonExistantPetById_expectNotFound() {
        //GIVEN
        petSteps.deletePet(NULL_API_KEY, pet.getId());

        //WHEN
        Response response = petSteps.getPet(pet.getId());

        //THEN
        petSteps.assertResponseCode(response.statusCode(), SC_NOT_FOUND);
    }
}
