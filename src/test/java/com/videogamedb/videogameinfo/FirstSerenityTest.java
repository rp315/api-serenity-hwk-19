package com.videogamedb.videogameinfo;

import com.videogamedb.constants.EndPoints;
import com.videogamedb.testbase.TestBase;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
public class FirstSerenityTest extends TestBase {

    @Test
    public void getAllVideoGamesData(){
        SerenityRest.rest()
                .given()
                .when()
                .get()
                .then()
                .log().all().statusCode(200);
    }

    @Title("This test will get the information of all videogames data")
    @Test
    public void test01(){
        SerenityRest.rest()
            .given()
                .when()
                .get(EndPoints.GET_ALL_VIDEOGAMES)
                .then()
                .log().all()
                .statusCode(200);
    }
}
