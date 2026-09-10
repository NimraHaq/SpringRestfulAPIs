package com.restful.todos.controller;

import com.restful.todos.request.TodoRequest;
import com.restful.todos.response.TodoResponse;
import com.restful.todos.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
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

    @Operation(summary = "Toggle todo completion status", description = "Toggles the completion status of a specific todo item by its ID for the currently authenticated user. The ID must be a positive integer.")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public TodoResponse toggleTodoCompletion(@PathVariable @Min(1) long id){
        return todoService.toggleTodoService(id);
    }

    @Operation(summary = "Delete a todo", description = "Deletes a specific todo item by its ID for the currently authenticated user. The ID must be a positive integer.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    public void deleteTodo(@PathVariable @Min(1) long id){
        todoService.deleteTodo(id);
    }

    /*
    is @Valid applied on @PathVariable in spring boot?
    no @Valid is for beans only.
    @Valid is designed to trigger validation on Java Beans (complex objects),
    it does not evaluate single primitive or simple type parameters—such as
    a String or Long—passed through a URL path.
     */
}
