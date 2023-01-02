package com.gorest.gorestinfo;

import com.gorest.info.UserSteps;
import com.gorest.testbase.TestBase;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Random;

import static org.hamcrest.Matchers.hasValue;
@RunWith(SerenityRunner.class)
public class UserCRUDTestWithSteps extends TestBase {

    public static String getRandomValue(){
        Random random = new Random();
        int randomInt = random.nextInt(100000);
        return Integer.toString(randomInt);
    }

    static String name = "vi"+getRandomValue() ;
    static String email= "abc"+getRandomValue()+"@gmail.com";
    static String gender = "male";
    static String status = "active";
    static int userId;




    @Steps
    UserSteps userSteps;
    @Title("This will create new user")
    @Test
    public void test001(){
         ValidatableResponse response = userSteps.createUser( name,  email,  gender,  status);
         response.log().all().statusCode(201);
    }

    @Title("Verify if the user was added to the application")
    @Test
    public void test002(){
        HashMap<String, Object> studentMap = userSteps.getUserInfoByName(name);
        Assert.assertThat(studentMap,hasValue(name));
        userId = (int) studentMap.get("id");

    }
    @Title("Update the user information and verify the updated information")
    @Test
    public void test003(){
        name = name+ "_updated";
       ValidatableResponse  response =userSteps.updateUser(userId,name,email,gender,status);
      response.log().all().statusCode(200);

        HashMap<String, Object> studentMap = userSteps.getUserInfoByName(name);
        Assert.assertThat(studentMap,hasValue(name));


    }
    @Title("Delete the product and verify if the product is deleted!")
    @Test
    public void test004() {
        userSteps.deleteStore(userId).statusCode(204);
        userSteps.getStoreById(userId) .statusCode(404);

    }






}
