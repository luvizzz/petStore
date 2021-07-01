package cases;

import domain.Category;
import domain.Pet;
import domain.Tag;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

public class CreatePetTests extends BaseTest {
    int petId;

    @BeforeEach
    private void setup() {
        this.petId = UUID.randomUUID().variant();
    }

    @AfterEach
    private void tearDown() {
        petSteps.deletePet(petId);
    }

    @Test
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
    void addPetWithAllPossibleData_expectSuccess() {
        //GIVEN
        Category myCategory = new Category(100, "myCategoryName");
        Pet pet = new Pet.Builder()
                .withId(1000L)
                .withCategory(myCategory)
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
}
