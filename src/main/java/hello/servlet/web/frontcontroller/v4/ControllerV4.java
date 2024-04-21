package hello.servlet.web.frontcontroller.v4;

import java.util.HashMap;
import java.util.Map;

public interface ControllerV4 {
    /**
     * @param paramMap
     * @param model
     * @return viewName
     */
    String process(Map<String, String> paramMap, Map<String, Object> model); // model 도 넘겨줌.뷰의 이름만 전달.
}
