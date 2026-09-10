package com.restful.todos.service;

import com.restful.todos.entity.Todo;
import com.restful.todos.entity.User;
import com.restful.todos.repository.TodoRepository;
import com.restful.todos.request.TodoRequest;
import com.restful.todos.response.TodoResponse;
import com.restful.todos.utils.FindAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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

    @Override
    @Transactional
    public TodoResponse toggleTodoService(long id) {
        User user = findAuthenticatedUser.getAuthenticatedUser();
        Todo todo = todoRepository.findByIdAndOwner(id, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Todo not found."));
        todo.setComplete(!todo.isComplete());
        Todo updatedTodo = todoRepository.save(todo);
        return entityToResponseMapping(updatedTodo);
    }

    @Override
    @Transactional
    public void deleteTodo(long id){
        User user = findAuthenticatedUser.getAuthenticatedUser();
        Todo todo = todoRepository.findByIdAndOwner(id, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Todo not found."));
        todoRepository.delete(todo);
    }

    private Todo requestToEntityMapping(TodoRequest todoRequest, User user){
        return new Todo(todoRequest.getTitle(), todoRequest.getDescription(), todoRequest.getPriority(), false, user);
    }

    private TodoResponse entityToResponseMapping(Todo todo){
        return new TodoResponse(todo.getId(), todo.getTitle(), todo.getDescription(), todo.getPriority(), todo.isComplete());
    }

}
