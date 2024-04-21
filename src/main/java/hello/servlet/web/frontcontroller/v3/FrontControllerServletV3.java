package hello.servlet.web.frontcontroller.v3;


import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*") // * -> v1 하위 모든 요청
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMap = new HashMap<>(); // 요청 URI에 해당하는 controller를 찾기 위한 맵

    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestURI(); // 요청 URI -> new-form

        // get으로 해당하는 controller 찾기
        ControllerV3 controller = controllerMap.get(request.getRequestURI()); // 요청 URI에 해당하는 controller 찾기 -> new form 된다.
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);// 404 처리
            return;
        }

//        request.getParameterNames().asIterator()
//                .forEachRemaining(paramName -> System.out.println(paramName + "=" + request.getParameter(paramName)));
        //paramMap
        // form 에 값을 넣었을때.
        Map<String, String> paramMap = createParamMap(request); // 파라미터 값을 다 넣음


        ModelView mv = controller.process(paramMap); // controller 호출 -> model view 반환
        String viewName = mv.getViewName(); // 논리이름 new-form

        MyView view = viewResolver(viewName);// 물리이름 new-form.jsp


        view.render(mv.getModel(), request,response); // view 호출 jsp 호출
    }

    private static MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private static Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName))); // 파라미터에 있는 값을 다 꺼내서 paramMap에 저장
        return paramMap;
    }
}
