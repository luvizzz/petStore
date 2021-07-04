package cases;

import domain.Category;
import domain.Pet;
import domain.Tag;
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
    protected void setup() {
        this.petId = Utils.randomId();
    }

    @AfterEach
    @Override
    protected void tearDown() {
        petSteps.deletePet((long) petId);
    }

    @Test
    @org.junit.jupiter.api.Tag("smoke")
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
    @org.junit.jupiter.api.Tag("smoke")
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
    void addPetWithBadInput_expectMethodNotAllowed() {
        //WHEN
        Response response = petSteps.addPet("\"input\":\"someBadInput\"");

        //THEN
        petSteps.assertResponseCode(response.statusCode(), SC_METHOD_NOT_ALLOWED);
    }
}
