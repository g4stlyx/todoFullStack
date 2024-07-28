package com.g4stly.restApi.todo;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;


//* probably there was no need for this, but here we go
public class TodoTest {

    @Test
    public void testDefaultConstructor() {
        Todo todo = new Todo();
        assertNull(todo.getId());
        assertNull(todo.getUsername());
        assertNull(todo.getDescription());
        assertNull(todo.getTargetDate());
        assertFalse(todo.isDone());
    }

    @Test
    public void testParameterizedConstructor() {
        Integer id = 1;
        String username = "user1";
        String description = "Test Description";
        LocalDate targetDate = LocalDate.of(2024, 7, 28);
        boolean done = true;

        Todo todo = new Todo(id, username, description, targetDate, done);

        assertEquals(id, todo.getId());
        assertEquals(username, todo.getUsername());
        assertEquals(description, todo.getDescription());
        assertEquals(targetDate, todo.getTargetDate());
        assertTrue(todo.isDone());
    }

    @Test
    public void testGettersAndSetters() {
        Todo todo = new Todo();

        Integer id = 1;
        String username = "user1";
        String description = "Test Description";
        LocalDate targetDate = LocalDate.of(2024, 7, 28);
        boolean done = true;

        todo.setId(id);
        todo.setUsername(username);
        todo.setDescription(description);
        todo.setTargetDate(targetDate);
        todo.setDone(done);

        assertEquals(id, todo.getId());
        assertEquals(username, todo.getUsername());
        assertEquals(description, todo.getDescription());
        assertEquals(targetDate, todo.getTargetDate());
        assertTrue(todo.isDone());
    }

    @Test
    public void testToString() {
        Integer id = 1;
        String username = "user1";
        String description = "Test Description";
        LocalDate targetDate = LocalDate.of(2024, 7, 28);
        boolean done = true;

        Todo todo = new Todo(id, username, description, targetDate, done);
        String expected = "Todo [id=" + id + ", username=" + username + ", description=" + description + ", targetDate="
                + targetDate + ", done=" + done + "]";
        
        assertEquals(expected, todo.toString());
    }
}
