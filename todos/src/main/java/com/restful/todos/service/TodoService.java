package com.restful.todos.service;

import com.restful.todos.request.TodoRequest;
import com.restful.todos.response.TodoResponse;

public interface TodoService {
    TodoResponse createTodo(TodoRequest todoRequest);
}
