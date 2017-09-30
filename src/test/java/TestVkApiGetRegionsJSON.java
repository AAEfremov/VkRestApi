import beans.VkGetRegionsAnswer;
import core.VkGetRegionsApi;
import io.restassured.RestAssured;
import org.junit.Test;

import java.util.List;

import static core.VkGetRegionsConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.core.IsEqual.equalTo;

public class TestVkApiGetRegionsJSON {

    // use base request and response specifications to form request and validate response.
    @Test
    public void useBaseRequestAndResponseSpecifications() {
        RestAssured
                .given(VkGetRegionsApi.baseRequestConfiguration())
                .param(PARAM_COUNTRY_ID, 10)
                .param(PARAM_OFFSET, 3)
                .param(PARAM_COUNT, 5)
                .get().prettyPeek()
                .then().specification(VkGetRegionsApi.successResponse());
    }

    @Test
    public void reachBuilderUsage(){
        VkGetRegionsApi.with()
                .country_id(10)
                .search_query("Nunavut")
                .callApi()
                .then().specification(VkGetRegionsApi.successResponse());
    }

    @Test
    public void validateGetRegionsAnswerAsAnObject() {
        List<VkGetRegionsAnswer> answers = VkGetRegionsApi.getVkGetRegionsAnswers(
                VkGetRegionsApi.with()
                        .country_id(test_country_id)
                        .offset(test_offset)
                        .count(test_count)
                        .version(version)
                        .callApi());

        assertThat(answers.size(), lessThanOrEqualTo(Integer.parseInt(String.valueOf(test_count))));
        assertThat(answers.get(0).id, equalTo(1500595));
        assertThat(answers.get(0).title, equalTo("Винницкая область"));
        assertThat(answers.get(3).id, equalTo(1502709));
        assertThat(answers.get(3).title, equalTo("Донецкая область"));
    }
}
