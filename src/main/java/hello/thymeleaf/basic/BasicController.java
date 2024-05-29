package hello.thymeleaf.basic;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/basic")
public class BasicController {

    // 타임리프 escape th:text="${data}" or [[${data}]] = <b> 태그가 랜더링 적용 안됨.
    @GetMapping("/text-basic")
    public String textBasic(Model model) {
        model.addAttribute("data", "<b>Hello Spring!</b>");

        return "basic/text-basic";
    }

    // 타임리프 unescaped th:utext=${data} or [(${data})] = <b> 태그가 랜더링 적용 됨.
    @GetMapping("/text-unescaped")
    public String textUnescaped(Model model) {
        model.addAttribute("data", "<b>Hello Spring!</b>");

        return "basic/text-unescaped";
    }

    @GetMapping("/variable")
    public String variable(Model model) {

        User userA = new User("userA", 10);
        User userB = new User("userB", 20);

        ArrayList<User> list = new ArrayList<>();
        list.add(userA);
        list.add(userB);

        Map<String, User> map = new HashMap<>();
        map.put("userA", userA);
        map.put("userB", userB);

        model.addAttribute("user", userA);
        model.addAttribute("users", list);
        model.addAttribute("userMap", map);

        return "/basic/variable";
    }

    @GetMapping("/basic-objects")
    public String basicObjects(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) { // HttpSession : 사용자가 웹브라우저 종료 전까지 데이터가 남아있음
        session.setAttribute("sessionData", "Hello Session");
        model.addAttribute("request", request);
        model.addAttribute("response", response);
        model.addAttribute("servletContext",request.getServletContext());
        return "/basic/basic-objects";
    }

    @Component("helloBean") // 스프링 빈의 이름을 지정 "helloBean" = 타임리프에서 호출 할 수 있다.
    static class HelloBean {
        public String hello(String data) {
            return "Hello" + data;
        }
    }

    @GetMapping("/date")
    public String date(Model model) {
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "/basic/date";
    }

    @GetMapping("link")
    public String link(Model model) {
        model.addAttribute("param1", "data1");
        model.addAttribute("param2", "data2");
        return "/basic/link";
    }

    @GetMapping("/literal")
    public String literal(Model model) {
        model.addAttribute("data", "Spring!");
        return "/basic/literal";
    }

    @Data
    static class User {
        private String username;
        private int age;

        public User(String username, int age) {
            this.username = username;
            this.age = age;
        }
    }
}
