package com.springboot.myfirstwebapp.todo;

import jakarta.validation.Valid;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.time.LocalDate;
import java.util.List;

//@Controller
@SessionAttributes("name") //이값을 사용하려는 모든 컨트롤러에 선언해야한다.
public class TodoController {
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    //todo를 사용하기 위한 todoService
    private TodoService todoService;


    @RequestMapping("list-todos")
    public String listAllTodos(ModelMap model){
        String username= (String)model.get("name");
        List<Todo> todos=todoService.findByUsername(username);
        model.addAttribute("todos",todos);
        return "listTodos";
    }

    private String getLoggedInUsername(ModelMap model){
        return (String)model.get("name");  //로그인된사용자를 security로 받으려고함
    }

    @RequestMapping(value="add-todo",method= RequestMethod.GET)
    public String showNewTodoPage(ModelMap model){
        String username = (String)model.get("name");
        Todo todo = new Todo(0,username, "",LocalDate.now().plusYears(1),false);
        model.put("todo",todo);
        return "todo";
    }
    @RequestMapping(value="add-todo", method = RequestMethod.POST)
    public String addNewTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
        //그냥 에러페이지 출력되지 않게 BindingResult를 활용해 준다.
        if(result.hasErrors()){ //에러가 일어나면
            return "todo"; //todo로 돌아가라
        }
        //modelMap에 바인딩 하는 대신에 직접 Todo Bean에 바인딩
        String username = (String)model.get("name");
        todoService.addTodo(username, todo.getDescription(),
                todo.getTargetDate(), false);
        //로직 중복을 막기 위한 리다이렉션
        return "redirect:list-todos";
    }
    @RequestMapping("delete-todo")
    public String deleteTodo(@RequestParam int id){//쿼리 파라미터로 받아오므로 RequestParam
        todoService.deleteById(id);
        return "redirect:list-todos";

    }
    //그 id에 대한 Todo 세부정보를 받아야 하고, 그걸 여기에 표시해야 한다.
    @RequestMapping(value="update-todo", method=RequestMethod.GET)
    public String showUpdateTodoPage(@RequestParam int id,ModelMap model){//쿼리 파라미터로 받아오므로 RequestParam
        Todo todo = todoService.findById(id);
        model.addAttribute("todo",todo);
        return "todo";
        //그냥 return todo 하면 에러남

    }
    @RequestMapping(value="update-todo", method = RequestMethod.POST)
    public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
        //그냥 에러페이지 출력되지 않게 BindingResult를 활용해 준다.
        if(result.hasErrors()){ //에러가 일어나면
            return "todo"; //todo로 돌아가라
        }
        //modelMap에 바인딩 하는 대신에 직접 Todo Bean에 바인딩
        String username = (String)model.get("name");
        todo.setUsername(username);
        todoService.updateTodo(todo);
        //로직 중복을 막기 위한 리다이렉션
        return "redirect:list-todos";
    }

}
