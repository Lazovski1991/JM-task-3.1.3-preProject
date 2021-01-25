package my.company.jmresttemplate;

import my.company.jmresttemplate.model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class JmRestTemplateApplication {

    public static void main(String[] args) {

        String url = "http://91.241.64.178:7081/api/users";
        User user = new User(3L, "James", "Brown", (byte) 44);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<User>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {
                });

        List<User> users = response.getBody();

        users.forEach(name -> System.out.println(name.getName()));

        String first = response.getHeaders().get("set-cookie").stream().findFirst().get();

        HttpHeaders headers = new HttpHeaders();

        headers.set("cookie", first);

        HttpEntity<User> entity = new HttpEntity<User>(user, headers);

        ResponseEntity<String> respPost =  restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        System.out.println(respPost.getBody());

        User updateInfo = new User(3L, "Thomas", "Shelby", (byte) 44);

        HttpEntity<User> requestBody = new HttpEntity<>(updateInfo, headers);

        ResponseEntity<String> respPut =  restTemplate.exchange(url, HttpMethod.PUT, requestBody, String.class);
        System.out.println(respPut.getBody());

        HttpEntity<User> requestDel = new HttpEntity<>(headers);

        ResponseEntity<String> respDel =  restTemplate.exchange(url + "/3" , HttpMethod.DELETE, requestDel, String.class);
        System.out.println(respDel.getBody());
    }
}
