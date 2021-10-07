package some;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

class HttpClientGetRequest {

    @Test
    public void test1() throws IOException {

        // Создаем WireMock client для соединения с API
        WireMock wm = new WireMock("https", "jd99e.mocklab.io", 443);

        // Конфигурируем API endpoint для теста

        wm.register(get(urlEqualTo("/api/users/1")).willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("{\n" +
                        "    \"data\": {\n" +
                        "        \"id\": 2,\n" +
                        "        \"email\": \"janet.weaver@reqres.in\",\n" +
                        "        \"first_name\": \"Janet\",\n" +
                        "        \"last_name\": \"Weaver\",\n" +
                        "        \"avatar\": \"https://reqres.in/img/faces/2-image.jpg\"\n" +
                        "    },\n" +
                        "    \"support\": {\n" +
                        "        \"url\": \"https://reqres.in/#support-heading\",\n" +
                        "        \"text\": \"To keep ReqRes free, contributions towards server costs are appreciated!\"\n" +
                        "    }\n" +
                        "}")));

        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {

            // создаем объект клиента
            HttpGet request = new HttpGet("https://jd99e.mocklab.io/api/users/1");

            CloseableHttpResponse response = httpClient.execute(request);

            try {

                // получаем статус ответа
                System.out.println(response.getProtocolVersion());              // HTTP/1.1
                System.out.println(response.getStatusLine().getStatusCode());   // 200
                System.out.println(response.getStatusLine().getReasonPhrase()); // OK
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                // если есть тело ответа
                if (entity != null) {
                    // возвращаем строку
                    String result = EntityUtils.toString(entity);
                    System.out.println(result);
                    assert result.contains("To keep ReqRes free, contributions towards server costs are appreciated!");
                }

            } finally {
                // закрываем соединения
                response.close();
            }
        } finally {
            httpClient.close();
        }
    }
    @Test
    public void test2() throws IOException {
        WireMock wm = new WireMock("https", "jd99e.mocklab.io", 443);

        // Конфигурируем API endpoint для теста

        wm.register(post(urlEqualTo("/api/users")).willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"leader\",\n" +
                        "    \"id\": \"652\",\n" +
                        "    \"createdAt\": \"2021-10-07T09:05:42.682Z\"\n" +
                        "}")));
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {

            // создаем объект клиента
            HttpPost request = new HttpPost("https://jd99e.mocklab.io/api/users");

            CloseableHttpResponse response = httpClient.execute(request);

            try {

                // получаем статус ответа
                System.out.println(response.getProtocolVersion());              // HTTP/1.1
                System.out.println(response.getStatusLine().getStatusCode());   // 200
                System.out.println(response.getStatusLine().getReasonPhrase()); // OK
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 201 OK

                HttpEntity entity = response.getEntity();
                // если есть тело ответа
                if (entity != null) {
                    // возвращаем строку
                    String result = EntityUtils.toString(entity);
                    System.out.println(result);
                    assert result.contains("2021-10-07T09:05:42.682Z") ;
                }

            } finally {
                // закрываем соединения
                response.close();
            }
        } finally {
            httpClient.close();
        }
    }
}