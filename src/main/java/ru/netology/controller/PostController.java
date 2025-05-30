package ru.netology.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;


public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;

    @Autowired
    public PostController(PostService service) {
        this.service = service;
    }

    public void all(HttpServletResponse response) throws IOException {
        final var data = service.all();
        sendResponseDataJson(data, response);
    }

    public void getById(long id, HttpServletResponse response) throws IOException {
        try {
            final var data = service.getById(id);
            sendResponseDataJson(data, response);
        } catch (NotFoundException n) {
            sendResponseDataJson("The post with this ID was not found", response);
        }
    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        final var gson = new Gson();
        final var post = gson.fromJson(body, Post.class);
        try {
            final var data = service.save(post);
            sendResponseDataJson(data, response);
        } catch (NotFoundException n) {
            sendResponseDataJson(n.getMessage(), response);
        }
    }

    public void removeById(long id, HttpServletResponse response) throws IOException {
        try {
            service.removeById(id);
            sendResponseDataJson("The post was deleted successfully", response);
        } catch (NotFoundException n) {
            sendResponseDataJson(n.getMessage(), response);
        }
    }

    private void sendResponseDataJson(Object o, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        response.getWriter().print(gson.toJson(o));
    }
}
