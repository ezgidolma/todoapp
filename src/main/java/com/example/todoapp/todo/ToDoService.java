package com.example.todoapp.todo;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class ToDoService {

    private static List<Todo> toDos = new ArrayList<>();
    private static int todosCount = 0;

    static {
        toDos.add(new Todo(++todosCount, "ezgi", "Learn AWS1", LocalDate.now().plusYears(1), false));
        toDos.add(new Todo(++todosCount, "ezgi", "Learn Java1", LocalDate.now().plusYears(2), false));
        toDos.add(new Todo(++todosCount, "ezgi", "Learn React1", LocalDate.now().plusYears(3), false));

    }

    public List<Todo> findByUserName(String username) {
        Predicate<? super Todo> predicate = toDo -> toDo.getUsername().equalsIgnoreCase(username);
        return toDos.stream().filter(predicate).toList();
    }

    public void addToDo(String username, String description, LocalDate targetDate, boolean done) {
        Todo todo = new Todo(++todosCount, username, description, targetDate, done);
        toDos.add(todo);
    }

    public void deleteById(int id) {
        Predicate<? super Todo> predicate = toDo -> toDo.getId() == id;
        toDos.removeIf(predicate);
    }

    public Todo findById(int id) {
        Predicate<? super Todo> predicate = toDo -> toDo.getId() == id;
        Todo toDo = toDos.stream().filter(predicate).findFirst().get();
        return toDo;
    }

    public void updateToDo(@Valid Todo toDo) {
        deleteById(toDo.getId());
        toDos.add(toDo);
    }
}
