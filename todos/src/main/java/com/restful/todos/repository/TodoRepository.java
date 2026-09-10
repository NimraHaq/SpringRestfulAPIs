package com.restful.todos.repository;

import com.restful.todos.entity.Todo;
import com.restful.todos.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {
    List<Todo> findTodosByOwner(User owner);
    Optional<Todo> findByIdAndOwner(Long id, User owner);
}
