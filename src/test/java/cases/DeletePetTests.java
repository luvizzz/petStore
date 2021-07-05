package cases;

import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_METHOD_NOT_ALLOWED;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;

public class DeletePetTests extends BaseTest {
    @Test
    @Tag(SMOKE_TAG)
    void deletePetsUsingNullApiKeyAndValidPetId_expectSuccess() {
        //WHEN
        Response response = petSteps.deletePet(null, pet.getId());

        //THEN
        petSteps.assertResponseCode(response.statusCode(), SC_OK);

        //WHEN
        response = petSteps.getPet(pet.getId());

        //THEN
        petSteps.assertResponseCode(response.statusCode(), SC_NOT_FOUND);
    }

    @Test
    void deletePetsUsingNullApiKeyAndNullPetId_expectMethodNotAllowed() {
        //WHEN
        Response response = petSteps.deletePet(null);

        //THEN
        petSteps.assertResponseCode(response.statusCode(), SC_METHOD_NOT_ALLOWED);

        //WHEN
        response = petSteps.getPet(pet.getId());

        //THEN
        petSteps.assertResponseContains(response, pet);
    }

    @Test
    void deletePetsUsingValidApiKeyAndValidPetId_expectSuccess() {
        //WHEN
        Response response = petSteps.deletePet("someApiKey", pet.getId());

        //THEN
        petSteps.assertResponseCode(response.statusCode(), SC_OK);

        //WHEN
        response = petSteps.getPet(pet.getId());

        //THEN
        petSteps.assertResponseCode(response.statusCode(), SC_NOT_FOUND);
    }

    @Test
    void deletePetsUsingValidApiKeyAndNullPetId_expectMethodNotAllowed() {
        //WHEN
        Response response = petSteps.deletePet("someApiKey");

        //THEN
        petSteps.assertResponseCode(response.statusCode(), SC_METHOD_NOT_ALLOWED);

        //WHEN
        response = petSteps.getPet(pet.getId());

        //THEN
        petSteps.assertResponseContains(response, pet);
    }

    @Test
    void deletePetsUsingInvalidPetId_expectBadRequest() {
        //WHEN
        Response response = petSteps.deletePet(null, "someInvalidPetId");

        //THEN
        petSteps.assertResponseCode(response.statusCode(), SC_BAD_REQUEST);

        //WHEN
        response = petSteps.getPet(pet.getId());

        //THEN
        petSteps.assertResponseContains(response, pet);
    }

    @Test
    void deleteNonexistantPet_expectNotFound() {
        //GIVEN
        petSteps.deletePet(null, pet.getId());

        //WHEN
        Response response = petSteps.deletePet(null, pet.getId());

        //THEN
        petSteps.assertResponseCode(response.statusCode(), SC_NOT_FOUND);
    }
}
