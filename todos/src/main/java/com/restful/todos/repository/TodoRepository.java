package com.restful.todos.repository;

import com.restful.todos.entity.Todo;
import com.restful.todos.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {
    List<Todo> findTodosByOwner(User owner);
}
