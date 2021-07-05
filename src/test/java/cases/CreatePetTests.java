package cases;

import domain.Category;
import domain.Pet;
import domain.Tag;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.util.List;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_METHOD_NOT_ALLOWED;

public class CreatePetTests extends BaseTest {
    int petId;

    @BeforeEach
    @Override
    @Description("Setting up test data")
    protected void setup() {
        this.petId = Utils.randomId();
    }

    @AfterEach
    @Override
    @Description("Tearing down test data")
    protected void tearDown() {
        petSteps.deletePet(NULL_API_KEY, (long) petId);
    }

    @Test
    @org.junit.jupiter.api.Tag(SMOKE_TAG)
    @Description("Test adding pet with minimum required data and expects success")
    void addPetWithOnlyRequiredData_expectSuccess() {
        //GIVEN
        Pet pet = new Pet.Builder()
                .withName("someTestName")
                .withPhotoUrls(List.of("some.url.com", "some.other.url.com"))
                .build();

        //WHEN
        Response response = petSteps.addPet(pet);

        //THEN
        petSteps.assertResponseContains(response, pet);
    }

    @Test
    @org.junit.jupiter.api.Tag(SMOKE_TAG)
    @Description("Test adding pet with all possible data and expects success")
    void addPetWithAllPossibleData_expectSuccess() {
        //GIVEN
        Pet pet = new Pet.Builder()
                .withId((long) petId)
                .withCategory(new Category(100, "myCategoryName"))
                .withName("someTestName")
                .withPhotoUrls(List.of("some.url.com", "some.other.url.com"))
                .withTags(List.of(new Tag(999, "myTagName")))
                .withStatus("someStatus")
                .build();

        //WHEN
        Response response = petSteps.addPet(pet);

        //THEN
        petSteps.assertResponseContains(response, pet);
    }

    @Test
    @Description("Test adding pet with missing mandatory photoUrl and expects failure")
    void addPetWithMissingRequiredPhotoUrls_expectFailure() {
        //GIVEN
        Pet pet = new Pet.Builder()
                .withName("someTestName")
                .build();

        //WHEN
        Response response = petSteps.addPet(pet);

        //THEN
        petSteps.assertResponseCode(response.statusCode(), SC_BAD_REQUEST);
    }

    @Test
    @Description("Test adding pet with missing mandatory name and expects failure")
    void addPetWithMissingRequiredName_expectFailure() {
        //GIVEN
        Pet pet = new Pet.Builder()
                .withPhotoUrls(List.of("some.url.com", "some.other.url.com"))
                .build();

        //WHEN
        Response response = petSteps.addPet(pet);

        //THEN
        petSteps.assertResponseCode(response.statusCode(), SC_BAD_REQUEST);
    }

    @Test
    @Description("Test adding pet with bad input payload and expects failure")
    void addPetWithBadInput_expectMethodNotAllowed() {
        //WHEN
        Response response = petSteps.addPet("\"input\":\"someBadInput\"");

        //THEN
        petSteps.assertResponseCode(response.statusCode(), SC_METHOD_NOT_ALLOWED);
    }
}
