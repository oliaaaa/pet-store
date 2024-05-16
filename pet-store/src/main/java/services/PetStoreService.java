package services;

import io.restassured.response.Response;
import models.Pet;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PetStoreService extends BaseService {

    public static Response addPet(final Pet petModel) {
        return defaultRequestSpecification()
                .body(petModel)
                .when()
                .post("/pet");
    }

    public static Response getPet(Long petId) {
        return defaultRequestSpecification()
                .when()
                .post("/pet/" + petId);
    }

    public static Response findPetsByStatus(String... petStatus) {
        return defaultRequestSpecification()
                .when()
                .param("status", concatStatuses(petStatus))
                .get("/pet/findByStatus/");
    }

    public static Response deletePet(Long petId) {
        return defaultRequestSpecification()
                .when()
                .delete("/pet/" + petId);
    }

    public static String concatStatuses(String... petStatus) {
        StringBuilder statusesConcat = new StringBuilder();

        for (int i = 0; i < petStatus.length; i++) {
            if (petStatus.length == 1 || i == petStatus.length - 1) {
                statusesConcat.append(Arrays.stream(petStatus).collect(Collectors.toList()).get(i));
            } else {
                statusesConcat.append(Arrays.stream(petStatus).collect(Collectors.toList()).get(i)).append(",");
            }
        }
        return statusesConcat.toString();
    }
}
