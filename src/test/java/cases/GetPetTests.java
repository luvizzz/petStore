package cases;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.apache.http.HttpStatus.SC_METHOD_NOT_ALLOWED;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;

public class GetPetTests extends BaseTest {
    @Test
    @Tag(SMOKE_TAG)
    @Description("Test getting existing Pet by id expect success")
    void getPetById_expectSuccess() {
        //WHEN
        Response response = petSteps.getPet(pet.getId());

        //THEN
        petSteps.assertResponseContains(response, pet);
    }

    @Test
    @Description("Test getting existing Pet without mandatory id expect not allowed")
    void getPetWithoutMandatoryId_expectMethodNotAllowed() {
        //WHEN
        Response response = petSteps.getPetWithoutId();

        //THEN
        petSteps.assertResponseCode(response.statusCode(), SC_METHOD_NOT_ALLOWED);
    }

    @Test
    @Description("Test getting non-existing Pet by id expect not found")
    void getNonExistantPetById_expectNotFound() {
        //GIVEN
        petSteps.deletePet(NULL_API_KEY, pet.getId());

        //WHEN
        Response response = petSteps.getPet(pet.getId());

        //THEN
        petSteps.assertResponseCode(response.statusCode(), SC_NOT_FOUND);
    }
}
