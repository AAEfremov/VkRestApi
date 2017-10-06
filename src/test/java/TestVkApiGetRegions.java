import beans.VkGetRegionsAnswer;
import core.VkApiGetRegionsMethod;
import enums.Error;
import enums.regions.Region1;
import enums.regions.Region2;
import enums.regions.Region3;
import io.restassured.RestAssured;
import org.junit.Test;

import java.util.List;

import static core.VkMethodsConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.core.IsEqual.equalTo;

public class TestVkApiGetRegions {

    //use base request and response specifications to form request and validate response.
    @Test
    public void useBaseRequestAndResponseSpecificationsForRegions() {
        RestAssured
                .given(VkApiGetRegionsMethod.baseRequestConfiguration(VK_API_REGIONS_URI))
                .param(PARAM_COUNTRY_ID, 10)
                .param(PARAM_OFFSET, 3)
                .param(PARAM_COUNT, 5)
                .param(PARAM_VERSION, "5.68")
                .get().prettyPeek()
                .then().specification(VkApiGetRegionsMethod.successResponse());
    }

    //check that endpoint is working correctly
    @Test
    public void reachBuilderUsageForRegionsPositive(){
        VkApiGetRegionsMethod.with()
                .country_id(10)
                .search_query(Region3.REGION_TITLE.getValue())
                .version("5.68")
                .callApi()
                .then()
                .specification(VkApiGetRegionsMethod.successResponse())
                .and()
                .body("response.items.id", hasItem(Integer.parseInt(Region3.REGION_ID.getValue())))
                .and()
                .body("response.items.title", hasItem(Region3.REGION_TITLE.getValue()));
    }

    //returns error without mandatory parameter
    @Test
    public void reachBuilderUsageForRegionsNegative(){
        VkApiGetRegionsMethod.with()
                .search_query(Region3.REGION_TITLE.getValue())
                .offset(0)
                .count(1)
                .version("5.68")
                .callApi()
                .then()
                .specification(VkApiGetRegionsMethod.successResponse())
                .and()
                .body("error.error_code", equalTo(Integer.parseInt(Error.ERROR_CODE.getValue())))
                .and()
                .body("error.error_msg", equalTo(Error.ERROR_MSG.getValue()));
    }

    //test method response as an object created from json scheme
    @Test
    public void validateGetRegionsAnswerAsAnObject() {
        List<VkGetRegionsAnswer> answers = VkApiGetRegionsMethod.getVkGetRegionsAnswers(
                VkApiGetRegionsMethod.with()
                        .country_id(TEST_REGIONS_COUNTRY_ID)
                        .offset(TEST_REGIONS_OFFSET)
                        .count(TEST_REGIONS_COUNT)
                        .version(VERSION)
                        .callApi());

        VkGetRegionsAnswer region1 = answers.get(0);
        VkGetRegionsAnswer region2 = answers.get(3);

        assertThat(answers.size(), lessThanOrEqualTo(Integer.parseInt(String.valueOf(TEST_REGIONS_COUNT))));
        assertThat(region1.id, equalTo(Integer.parseInt(Region1.REGION_ID.getValue())));
        assertThat(region1.title, equalTo(Region1.REGION_TITLE.getValue()));
        assertThat(region2.id, equalTo(Integer.parseInt(Region2.REGION_ID.getValue())));
        assertThat(region2.title, equalTo(Region2.REGION_TITLE.getValue()));
    }
}
