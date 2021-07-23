package com.videogamedb.videogameinfo;

import com.videogamedb.constants.EndPoints;
import com.videogamedb.model.VideoGamePojo;
import com.videogamedb.testbase.TestBase;
import com.videogamedb.utils.TestUtils;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;
import static org.junit.Assert.assertThat;

@RunWith(SerenityRunner.class)
public class SerenityCRUDTest extends TestBase {

    static String name = "rakesh"+ TestUtils.getRandomValue();
    static String releaseDate =TestUtils.getRandomValueInt()+"-03-10";
    static int reviewScore = TestUtils.getRandomValueInt();
    static String category = "Shooter"+ TestUtils.getRandomValue();
    static String rating = "Unviersal" + TestUtils.getRandomValue();
    static int videogameId;

    @Title("This is to create a new videogame data")
    @Test
    public void test01(){
        VideoGamePojo videoGamePojo = new VideoGamePojo();
        videoGamePojo.setName(name);
        videoGamePojo.setReleaseDate(releaseDate);
        videoGamePojo.setCategory(category);
        videoGamePojo.setReviewScore(reviewScore);
        videoGamePojo.setRating(rating);

        SerenityRest.rest().given()
                .header("Content-Type", "application/json")
                .body(videoGamePojo)
                .when()
                .post()
                .then().log().all().statusCode(200);
    }

    @Title("verify if the student was added to the application")
    @Test
    public void test02(){
        String s1 ="findAll{it.name =='";
        String s2 = "'}.get(0)";

        HashMap<String,Object> value =
        SerenityRest.rest().given().log().all()
                .header("Content-Type", "application/json")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract().path(s1+name+s2);

        assertThat(value,hasValue(name));
        System.out.println(value);
        videogameId = (int) value.get("id");
    }

    @Title("update the user informationa and verify the update information")
    @Test
    public void test03(){
        String s1 ="findAll{it.name =='";
        String s2 = "'}.get(0)";

        VideoGamePojo videoGamePojo = new VideoGamePojo();
        videoGamePojo.setName(name);
        videoGamePojo.setReleaseDate(releaseDate);
        videoGamePojo.setCategory(category);
        videoGamePojo.setReviewScore(reviewScore);
        videoGamePojo.setRating(rating);

        SerenityRest.rest().given().log().all()
                .header("Content-Type", "application/json")
                .pathParam("videogameID",videogameId)
                .body(videoGamePojo)
                .when()
                .put(EndPoints.UPDATE_VIDEOGAME_BY_ID)
                .then().log().all().statusCode(200);

        HashMap<String,Object> value =
                SerenityRest.rest().given().log().all()
                        .header("Content-Type", "application/json")
                        .when()
                        .get()
                        .then()
                        .statusCode(200)
                        .extract().path(s1+name+s2);

        assertThat(value,hasValue(name));

    }

    @Title("Delete the videogame data and verify if the data is deleted")
    @Test
    public void test04(){
        SerenityRest.rest().given()
                .pathParam("videogameID",videogameId)
                .when()
                .delete(EndPoints.DELETE_VIDEOGAME_BY_ID)
                .then().log().all().statusCode(200);

        SerenityRest.rest().given()
                .pathParam("videogameID",videogameId)
                .when()
                .get()
                .then()
                .statusCode(200);



    }
}
