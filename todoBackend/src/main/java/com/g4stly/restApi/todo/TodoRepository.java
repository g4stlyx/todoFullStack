package com.g4stly.restApi.todo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Integer>{
	List<Todo> findByUsername(String username);
}
