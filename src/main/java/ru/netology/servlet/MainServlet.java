package ru.netology.servlet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.common.RequestMethods;
import ru.netology.config.JavaConfig;
import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private final String REGEX = "/api/posts/\\d+";
    private PostController controller;

    @Override
    public void init() {
        final var context = new AnnotationConfigApplicationContext(JavaConfig.class);
        controller = context.getBean(PostController.class);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        // если деплоились в root context, то достаточно этого
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();
            // primitive routing
            if (method.equals(RequestMethods.METHOD_GET.get()) && path.equals("/api/posts")) {
                controller.all(resp);
                return;
            }
            if (method.equals(RequestMethods.METHOD_GET.get()) && path.matches(REGEX)) {
                // easy way
                final var id = parseStringToLong(path);
                controller.getById(id, resp);
                return;
            }
            if (method.equals(RequestMethods.METHOD_POST.get()) && path.equals("/api/posts")) {
                controller.save(req.getReader(), resp);
                return;
            }
            if (method.equals(RequestMethods.METHOD_DELETE.get()) && path.matches(REGEX)) {
                // easy way
                final var id = parseStringToLong(path);
                controller.removeById(id, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private Long parseStringToLong(String path) {
        return Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
    }
}

