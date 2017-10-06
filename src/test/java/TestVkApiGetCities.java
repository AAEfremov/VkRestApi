import beans.VkGetCitiesAnswer;
import core.VkApiGetCitiesMethod;
import enums.Error;
import enums.cities.City1;
import enums.cities.City2;
import enums.cities.City3;
import enums.cities.City4;
import io.restassured.RestAssured;
import org.junit.Test;

import java.util.List;

import static core.VkMethodsConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.core.IsEqual.equalTo;

public class TestVkApiGetCities {

    //use base request and response specifications to form request and validate response.
    @Test
    public void useBaseRequestAndResponseSpecificationsForCities() {
        RestAssured
                .given(VkApiGetCitiesMethod.baseRequestConfiguration(VK_API_CITIES_URI))
                .param(PARAM_COUNTRY_ID, 9)
                .param(PARAM_OFFSET, 0)
                .param(PARAM_NEED_ALL, 0)
                .param(PARAM_COUNT, 7)
                .param(PARAM_VERSION, "5.68")
                .get().prettyPeek()
                .then().specification(VkApiGetCitiesMethod.successResponse());
    }

    //check that endpoint is working correctly
    @Test
    public void reachBuilderUsageForCities1(){
        VkApiGetCitiesMethod.with()
                .country_id(1)
                .search_query(City3.CITY_TITLE.getValue())
                .version("5.68")
                .callApi()
                .then()
                .specification(VkApiGetCitiesMethod.successResponse())
                .and()
                .body("response.items.id", hasItem(Integer.parseInt(City3.CITY_ID.getValue())))
                .and()
                .body("response.items.title", hasItem(City3.CITY_TITLE.getValue()))
                .and()
                .body("response.items.region", hasItem(City3.CITY_REGION.getValue()));
    }

    //and with additional parameter region_id too
    @Test
    public void reachBuilderUsageForCities2(){
        VkApiGetCitiesMethod.with()
                .country_id(1)
                .region_id(2)
                .search_query(City4.CITY_TITLE.getValue())
                .version("5.68")
                .callApi()
                .then().specification(VkApiGetCitiesMethod.successResponse())
                .and()
                .body("response.items.id", hasItem(Integer.parseInt(City4.CITY_ID.getValue())))
                .and()
                .body("response.items.title", hasItem(City4.CITY_TITLE.getValue()))
                .and()
                .body("response.items.region", hasItem(City4.CITY_REGION.getValue()));

    }

    //returns error without mandatory parameter
    @Test
    public void reachBuilderUsageForCities2Negative(){
        VkApiGetCitiesMethod.with()
                .region_id(2)
                .search_query(City4.CITY_TITLE.getValue())
                .version("5.68")
                .callApi()
                .then().specification(VkApiGetCitiesMethod.successResponse())
                .and()
                .body("error.error_code", equalTo(Integer.parseInt(Error.ERROR_CODE.getValue())))
                .and()
                .body("error.error_msg", equalTo(Error.ERROR_MSG.getValue()));
    }


    //test method response as an object created from json scheme
    @Test
    public void validateGetCitiesAnswerAsAnObject() {
        List<VkGetCitiesAnswer> answers = VkApiGetCitiesMethod.getVkGetCitiesAnswers(
                VkApiGetCitiesMethod.with()
                        .country_id(1)
                        .region_id(1)
                        .offset(0)
                        .count(TEST_CITIES_COUNT)
                        .version(VERSION)
                        .callApi());
        VkGetCitiesAnswer city1 = answers.get(0);
        VkGetCitiesAnswer city2 = answers.get(1);

        assertThat(answers.size(), lessThanOrEqualTo(Integer.parseInt(String.valueOf(TEST_CITIES_COUNT))));
        assertThat(city1.id, equalTo(Integer.parseInt(City1.CITY_ID.getValue())));
        assertThat(city1.title, equalTo(City1.CITY_TITLE.getValue()));
        assertThat(city1.important, equalTo(Integer.parseInt(City1.CITY_IMPORTANT.getValue())));
        assertThat(city2.id, equalTo(Integer.parseInt(City2.CITY_ID.getValue())));
        assertThat(city2.title, equalTo(City2.CITY_TITLE.getValue()));
        assertThat(city2.area, equalTo(City2.CITY_AREA.getValue()));
        assertThat(city2.region, equalTo(City2.CITY_REGION.getValue()));
    }


}
