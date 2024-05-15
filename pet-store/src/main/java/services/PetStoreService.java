package services;

import io.restassured.response.Response;
import models.Pet;

public class PetStoreService extends BaseService {

    public static Response addPet(final Pet petModel) {
        return defaultRequestSpecification()
                .body(petModel)
                .when()
                .post("/pet");
    }

    public static Response getPet(int petId) {
        return defaultRequestSpecification()
                .when()
                .post("/pet/" + petId);
    }
}
