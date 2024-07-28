package com.g4stly.restApi.todo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TodoJpaResourceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoJpaResource todoJpaResource;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveTodos() {
        String username = "user1";
        List<Todo> todos = Arrays.asList(new Todo(), new Todo());
        when(todoRepository.findByUsername(username)).thenReturn(todos);

        List<Todo> result = todoJpaResource.retrieveTodos(username);

        assertEquals(todos, result);
        verify(todoRepository, times(1)).findByUsername(username);
    }

    @Test
    public void testRetrieveTodo_Success() {
        String username = "user1";
        int id = 1;
        Todo todo = new Todo();
        todo.setUsername(username);
        Optional<Todo> todoOptional = Optional.of(todo);

        when(todoRepository.findById(id)).thenReturn(todoOptional);

        ResponseEntity<Todo> response = todoJpaResource.retrieveTodo(username, id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(todo, response.getBody());
        verify(todoRepository, times(1)).findById(id);
    }

    @Test
    public void testRetrieveTodo_NotFound() {
        String username = "user1";
        int id = 1;

        when(todoRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Todo> response = todoJpaResource.retrieveTodo(username, id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(todoRepository, times(1)).findById(id);
    }

    @Test
    public void testRetrieveTodo_Forbidden() {
        String username = "user1";
        int id = 1;
        Todo todo = new Todo();
        todo.setUsername("otherUser");
        Optional<Todo> todoOptional = Optional.of(todo);

        when(todoRepository.findById(id)).thenReturn(todoOptional);

        ResponseEntity<Todo> response = todoJpaResource.retrieveTodo(username, id);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNull(response.getBody());
        verify(todoRepository, times(1)).findById(id);
    }

    @Test
    public void testDeleteTodo() {
        String username = "user1";
        int id = 1;

        ResponseEntity<Void> response = todoJpaResource.deleteTodo(username, id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(todoRepository, times(1)).deleteById(id);
    }

    @Test
    public void testUpdateTodo_Success() {
        String username = "user1";
        int id = 1;
        Todo existingTodo = new Todo();
        existingTodo.setUsername(username);
        Todo updatedTodo = new Todo();
        updatedTodo.setUsername(username);

        when(todoRepository.findById(id)).thenReturn(Optional.of(existingTodo));
        when(todoRepository.save(updatedTodo)).thenReturn(updatedTodo);

        ResponseEntity<Todo> response = todoJpaResource.updateTodo(username, id, updatedTodo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedTodo, response.getBody());
        verify(todoRepository, times(1)).findById(id);
        verify(todoRepository, times(1)).save(updatedTodo);
    }

    @Test
    public void testUpdateTodo_NotFound() {
        String username = "user1";
        int id = 1;
        Todo updatedTodo = new Todo();
        updatedTodo.setUsername(username);

        when(todoRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Todo> response = todoJpaResource.updateTodo(username, id, updatedTodo);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(todoRepository, times(1)).findById(id);
        verify(todoRepository, times(0)).save(updatedTodo);
    }

    @Test
    public void testUpdateTodo_Forbidden() {
        String username = "user1";
        int id = 1;
        Todo existingTodo = new Todo();
        existingTodo.setUsername("otherUser");
        Todo updatedTodo = new Todo();
        updatedTodo.setUsername(username);

        when(todoRepository.findById(id)).thenReturn(Optional.of(existingTodo));

        ResponseEntity<Todo> response = todoJpaResource.updateTodo(username, id, updatedTodo);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNull(response.getBody());
        verify(todoRepository, times(1)).findById(id);
        verify(todoRepository, times(0)).save(updatedTodo);
    }

    @Test
    public void testCreateTodo() {
        String username = "user1";
        Todo newTodo = new Todo();
        newTodo.setUsername(username);
        newTodo.setId(null);

        when(todoRepository.save(newTodo)).thenReturn(newTodo);

        Todo result = todoJpaResource.createTodo(username, newTodo);

        assertEquals(newTodo, result);
        verify(todoRepository, times(1)).save(newTodo);
    }
}
