package com.g4stly.restApi.todo;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoJpaResource {

	private TodoRepository todoRepository;

	public TodoJpaResource(TodoRepository todoRepository) {
		this.todoRepository = todoRepository;
	}

	@GetMapping("/users/{username}/todos")
	public List<Todo> retrieveTodos(@PathVariable String username) {
		return todoRepository.findByUsername(username);
	}

	@GetMapping("/users/{username}/todos/{id}")
	public ResponseEntity<Todo> retrieveTodo(
			@PathVariable String username,
			@PathVariable int id) {

		Optional<Todo> todoOptional = todoRepository.findById(id);

		if (todoOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Todo todo = todoOptional.get();

		// * if todo doesnt belong to user logged in, throw 403
		if (!todo.getUsername().equals(username)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		return ResponseEntity.ok(todo);
	}

	@DeleteMapping("/users/{username}/todos/{id}")
	public ResponseEntity<Void> deleteTodo(@PathVariable String username,
			@PathVariable int id) {
		todoRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/users/{username}/todos/{id}")
	public ResponseEntity<Todo> updateTodo(
			@PathVariable String username,
			@PathVariable int id,
			@RequestBody Todo todo) {

		Todo existingTodo = todoRepository.findById(id).orElse(null);

		if (existingTodo == null) {
			return ResponseEntity.notFound().build();
		}

		// * if todo doesnt belong to user logged in, throw 403
		if (!existingTodo.getUsername().equals(username)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		todo.setUsername(username);
		Todo updatedTodo = todoRepository.save(todo);

		return ResponseEntity.ok(updatedTodo);
	}

	@PostMapping("/users/{username}/todos")
	public Todo createTodo(@PathVariable String username,
			@RequestBody Todo todo) {
		todo.setUsername(username);
		todo.setId(null);
		return todoRepository.save(todo);
	}

}
