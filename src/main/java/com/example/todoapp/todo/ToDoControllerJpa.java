package com.example.todoapp.todo;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@Controller
@SessionAttributes("name")
public class ToDoControllerJpa {


    private TodoRepository todoRepository;

    public ToDoControllerJpa(ToDoService toDoService, TodoRepository todoRepository) {

        this.todoRepository = todoRepository;
    }

    @RequestMapping("list-todos")
    public String toDoList(ModelMap modelMap) {
        String username = getLoggedinUserName(modelMap);

        List<Todo> todos = todoRepository.findByUsername(username);
        modelMap.addAttribute("todos", todos);
        return "listToDos";
    }

    private static String getLoggedinUserName(ModelMap modelMap) {
        return (String) modelMap.get("name");
    }

    @RequestMapping(value = "add-todo", method = RequestMethod.GET)
    public String showNewTodoPage(ModelMap modelMap) {
        String username = getLoggedinUserName(modelMap);
        Todo toDo = new Todo(0, username, "", LocalDate.now().plusYears(1), false);
        modelMap.put("todo", toDo);
        return "todo";
    }

    @RequestMapping(value = "add-todo", method = RequestMethod.POST)
    public String addNewTodo(ModelMap modelMap, @Valid @ModelAttribute("todo") Todo toDo, BindingResult result) {
        if (result.hasErrors()) {
            return "todo";
        }
        String username = getLoggedinUserName(modelMap);
        toDo.setUsername(username);
        todoRepository.save(toDo);
        return "redirect:list-todos";
    }

    @RequestMapping("delete-todo")
    public String deleteToDo(@RequestParam int id) {
        todoRepository.deleteById(id);
        return "redirect:list-todos";

    }

    @RequestMapping(value = "update-todo", method = RequestMethod.GET)
    public String showUpdateToDoPage(@RequestParam int id, ModelMap modelMap) {
        Todo toDo = todoRepository.findById(id).get();
        modelMap.addAttribute("todo", toDo);
        return "todo";

    }

    @RequestMapping(value = "update-todo", method = RequestMethod.POST)
    public String updateTodo(ModelMap modelMap, @Valid @ModelAttribute("todo") Todo toDo, BindingResult result) {
        if (result.hasErrors()) {
            return "todo";
        }
        String username = getLoggedinUserName(modelMap);
        toDo.setUsername(username);
        todoRepository.save(toDo);
        return "redirect:list-todos";
    }

}
