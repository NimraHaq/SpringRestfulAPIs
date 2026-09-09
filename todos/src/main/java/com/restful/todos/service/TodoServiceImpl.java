package com.restful.todos.service;

import com.restful.todos.entity.Todo;
import com.restful.todos.entity.User;
import com.restful.todos.repository.TodoRepository;
import com.restful.todos.request.TodoRequest;
import com.restful.todos.response.TodoResponse;
import com.restful.todos.utils.FindAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final FindAuthenticatedUser findAuthenticatedUser;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository, FindAuthenticatedUser findAuthenticatedUser) {
        this.todoRepository = todoRepository;
        this.findAuthenticatedUser = findAuthenticatedUser;
    }

    @Override
    @Transactional
    public TodoResponse createTodo(TodoRequest todoRequest) {
        User user = findAuthenticatedUser.getAuthenticatedUser();
        Todo todo = requestToEntityMapping(todoRequest, user);
        Todo savedTodo = todoRepository.save(todo);
        return entityToResponseMapping(savedTodo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoResponse> getTodosByUser() {
        User user = findAuthenticatedUser.getAuthenticatedUser();
        return todoRepository.findTodosByOwner(user).stream().map(this::entityToResponseMapping).toList();
    }

    private Todo requestToEntityMapping(TodoRequest todoRequest, User user){
        return new Todo(todoRequest.getTitle(), todoRequest.getDescription(), todoRequest.getPriority(), false, user);
    }

    private TodoResponse entityToResponseMapping(Todo todo){
        return new TodoResponse(todo.getId(), todo.getTitle(), todo.getDescription(), todo.getPriority(), todo.isComplete());
    }

}
