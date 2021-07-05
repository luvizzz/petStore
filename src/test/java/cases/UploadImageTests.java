package cases;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;

public class UploadImageTests extends BaseTest {
    private static final String DEFAULT_IMAGE_TYPE = "image/jpeg";

    @Test
    @Tag(SMOKE_TAG)
    @Description("Test uploading image and metadata expect success")
    void uploadPetImageAndMetadata_expectSuccess() {
        //WHEN
        Response response = petSteps.uploadPetImage(pet.getId(), "someMetadata", "src/test/resources/dog.jpeg", DEFAULT_IMAGE_TYPE);

        //THEN
        petSteps.assertResponseCode(response.statusCode(), SC_OK);
    }

    @Test
    @Tag(SMOKE_TAG)
    @Description("Test uploading image expect success")
    void uploadPetImage_expectSuccess() {
        //WHEN
        Response response = petSteps.uploadPetImage(pet.getId(), null, "src/test/resources/dog.jpeg", DEFAULT_IMAGE_TYPE);

        //THEN
        petSteps.assertResponseCode(response.statusCode(), SC_OK);
    }

    @Test
    @Tag(SMOKE_TAG)
    @Description("Test uploading metadata expect success")
    void uploadPetMetadata_expectSuccess() {
        //WHEN
        Response response = petSteps.uploadPetImage(pet.getId(), "someMetadata", null, DEFAULT_IMAGE_TYPE);

        //THEN
        petSteps.assertResponseCode(response.statusCode(), SC_OK);
    }

    @Test
    @Description("Test uploading nothing expect success")
    void uploadNothing_expectSuccess() {
        //WHEN
        Response response = petSteps.uploadPetImage(pet.getId(), null, null, DEFAULT_IMAGE_TYPE);

        //THEN
        petSteps.assertResponseCode(response.statusCode(), SC_OK);
    }

    @Test
    @Description("Test uploading image and metadata for wrong id expect not found")
    void updatePetWithWrongId_expectNotFound() {
        //GIVEN
        petSteps.deletePet(null, pet.getId());

        //WHEN
        Response response = petSteps.uploadPetImage(pet.getId(), "someMetadata", "src/test/resources/dog.jpeg", DEFAULT_IMAGE_TYPE);

        //THEN
        petSteps.assertResponseCode(response.statusCode(), SC_NOT_FOUND);
    }
}
