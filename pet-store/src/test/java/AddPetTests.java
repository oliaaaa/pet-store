import models.Category;
import models.Pet;
import models.Tag;
import org.junit.jupiter.api.Test;
import services.PetStoreService;

import java.util.Arrays;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;

public class AddPetTests {
    @Test
    public void Users_CreateUsers_Success() {

        Category category = new Category(123L, "Domestic");

        Tag tag = new Tag(123L, "Cute");

        Pet pet = new Pet(
                123L,
                category,
                "Bublik",
                Arrays.asList("https://en.wikipedia.org/wiki/Cat#/media/File:Cat_August_2010-4.jpg"),
                Arrays.asList(tag),
                "available");

        PetStoreService.addPet(pet)
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .log()
                .body()
                .body("id", equalTo(pet.getId().intValue()))
                .body("name", equalToCompressingWhiteSpace(pet.getName()))
                .body("status", equalToCompressingWhiteSpace(pet.getStatus()))
                .body("category.id",equalTo(pet.getCategory().getId().intValue()))
                .body("category.name",equalTo(pet.getCategory().getName()))
                .body("tags[0].id",equalTo(pet.getTags().get(0).getId().intValue()))
                .body("tags[0].name",equalTo(pet.getTags().get(0).getName()))
                .body("photoUrls[0]", containsStringIgnoringCase("cat"));
//
//        GoRestService.deleteUser(userId);
    }
}
