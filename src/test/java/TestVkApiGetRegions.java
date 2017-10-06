import beans.VkGetRegionsAnswer;
import core.VkApiMethods;
import io.restassured.RestAssured;
import org.junit.Test;

import java.util.List;

import static core.VkGetRegionsConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.core.IsEqual.equalTo;

public class TestVkApiGetRegions {

    // use base request and response specifications to form request and validate response.
    @Test
    public void useBaseRequestAndResponseSpecificationsForRegions() {
        RestAssured
                .given(VkApiMethods.baseRequestConfiguration(VK_API_REGIONS_URI))
                .param(PARAM_REGIONS_COUNTRY_ID, 10)
                .param(PARAM_REGIONS_OFFSET, 3)
                .param(PARAM_REGIONS_COUNT, 5)
                .param(PARAM_REGIONS_VERSION, "5.68")
                .get().prettyPeek()
                .then().specification(VkApiMethods.successResponse());
    }

    @Test
    public void reachBuilderUsageForRegions(){
        VkApiMethods.withForRegions()
                .country_id(10)
                .search_query("Nunavut")
                .version("5.68")
                .callApi()
                .then().specification(VkApiMethods.successResponse());
    }

    @Test
    public void validateGetRegionsAnswerAsAnObject() {
        List<VkGetRegionsAnswer> answers = VkApiMethods.getVkGetRegionsAnswers(
                VkApiMethods.withForRegions()
                        .country_id(TEST_REGIONS_COUNTRY_ID)
                        .offset(TEST_REGIONS_OFFSET)
                        .count(TEST_REGIONS_COUNT)
                        .version(REGIONS_VERSION)
                        .callApi());

        assertThat(answers.size(), lessThanOrEqualTo(Integer.parseInt(String.valueOf(TEST_REGIONS_COUNT))));
        assertThat(answers.get(0).id, equalTo(1500595));
        assertThat(answers.get(0).title, equalTo("Винницкая область"));
        assertThat(answers.get(3).id, equalTo(1502709));
        assertThat(answers.get(3).title, equalTo("Донецкая область"));
    }
}
