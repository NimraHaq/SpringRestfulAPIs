package com.restful.todos.service;

import com.restful.todos.request.TodoRequest;
import com.restful.todos.response.TodoResponse;

import java.util.List;

public interface TodoService {
    TodoResponse createTodo(TodoRequest todoRequest);
    List<TodoResponse> getTodosByUser();
    TodoResponse toggleTodoService(long id);
    void deleteTodo(long id);
}
