import beans.VkGetCitiesAnswer;
import core.VkApiMethods;
import io.restassured.RestAssured;
import org.junit.Test;

import java.util.List;

import static core.VkGetCitiesConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.core.IsEqual.equalTo;

public class TestVkApiGetCities {

    @Test
    public void useBaseRequestAndResponseSpecificationsForCities() {
        RestAssured
                .given(VkApiMethods.baseRequestConfiguration(VK_API_CITIES_URI))
                .param(PARAM_CITIES_COUNTRY_ID, 9)
                .param(PARAM_CITIES_OFFSET, 0)
                .param(PARAM_CITIES_NEED_ALL, 0)
                .param(PARAM_CITIES_COUNT, 7)
                .param(PARAM_CITIES_VERSION, "5.68")
                .get().prettyPeek()
                .then().specification(VkApiMethods.successResponse());
    }

    @Test
    public void reachBuilderUsageForCities1(){
        VkApiMethods.withForCities()
                .country_id(1)
                .search_query("Кемерово")
                .version("5.68")
                .callApi()
                .then().specification(VkApiMethods.successResponse());
    }

    @Test
    public void reachBuilderUsageForCities2(){
        VkApiMethods.withForCities()
                .country_id(1)
                .region_id(2)
                .search_query("Петергоф")
                .version("5.68")
                .callApi()
                .then().specification(VkApiMethods.successResponse());
    }

    @Test
    public void validateGetCitiesAnswerAsAnObject() {
        List<VkGetCitiesAnswer> answers = VkApiMethods.getVkGetCitiesAnswers(
                VkApiMethods.withForCities()
                        .country_id(1)
                        .region_id(1)
                        .offset(0)
                        .count(TEST_CITIES_COUNT)
                        .version(CITIES_VERSION)
                        .callApi());

        assertThat(answers.size(), lessThanOrEqualTo(Integer.parseInt(String.valueOf(TEST_CITIES_COUNT))));
        assertThat(answers.get(0).id, equalTo(1));
        assertThat(answers.get(0).title, equalTo("Москва"));
        assertThat(answers.get(0).important, equalTo(1));
        assertThat(answers.get(1).id, equalTo(1053447));
        assertThat(answers.get(1).title, equalTo("Алабушево"));
        assertThat(answers.get(1).area, equalTo("Зеленоград город"));
        assertThat(answers.get(1).region, equalTo("Москва город"));

    }


}
