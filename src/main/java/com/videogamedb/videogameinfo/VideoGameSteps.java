package com.videogamedb.videogameinfo;

import com.videogamedb.constants.EndPoints;
import com.videogamedb.model.VideoGamePojo;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasValue;

public class VideoGameSteps {

    static int videogameId;

    @Step("Creating Videogame with name: {0}, releaseDate: {1}, reviewScore: {2}, category: {3}, rating: {4}")
    public ValidatableResponse createVideoGame(String name, String releaseDate, int reviewScore, String category, String rating){

        VideoGamePojo videoGamePojo = new VideoGamePojo();
        videoGamePojo.setName(name);
        videoGamePojo.setReleaseDate(releaseDate);
        videoGamePojo.setReviewScore(reviewScore);
        videoGamePojo.setCategory(category);
        videoGamePojo.setRating(rating);

        return(ValidatableResponse) SerenityRest.rest().given().log().all()
                .header("Content-Type", "application/json")
                .body(videoGamePojo)
                .when()
                .post();
    }

    @Step("verify the data created by name")
    public void verifyCreatedIdByName(String name){

        String p1 = "findAll{it.name =='";
        String p2 = "'}.get(0)";
        HashMap<String,Object> value =

                SerenityRest.rest().given().log().all()
                        .when()
                        .get(EndPoints.GET_SINGLE_VIDEOGAME_BY_ID)
                        .then()
                        .statusCode(200)
                        .extract().path(p1 +name+ p2);

        assertThat(value,hasValue(name));
        System.out.println(value);
        videogameId = (int) value.get("id");
    }


}
