package com.restful.todos.controller;

import com.restful.todos.request.TodoRequest;
import com.restful.todos.response.TodoResponse;
import com.restful.todos.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Todo REST Api Endpoints.", description = "User's Todo management operations")
@RequestMapping("/api/todos")
@RestController
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @Operation(summary = "Create a new todo", description = "Creates a new todo item for the currently authenticated user. Requires the title, description, and priority of the todo.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/addTodo")
    public TodoResponse createTodo(TodoRequest todoRequest){
        return todoService.createTodo(todoRequest);
    }

    @Operation(summary = "Get todos for the authenticated user", description = "Retrieves all todo items associated with the currently authenticated user.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<TodoResponse> getTodosByUser(){
        return todoService.getTodosByUser();
    }
}
