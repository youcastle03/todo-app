package com.springboot.myfirstwebapp.todo;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class TodoService {
    private static List<Todo> todos=new ArrayList<>(); //일단 정적리스트에 관해 작업해보자
    private static int todosCount=0; //Todo의 개수
    static{  //정적변수 초기화를 위한 static block
        todos.add(new Todo(++todosCount,"castle","Learn Springboot",
                LocalDate.now().plusYears(1),false));
        todos.add(new Todo(++todosCount,"castle","GET AWS Certified",
                LocalDate.now().plusYears(2),false));
        todos.add(new Todo(++todosCount,"castle","Learn DevOps",
                LocalDate.now().plusYears(3),false));
    }
    //todos가 private이므로 반환할거 필요
    public List<Todo> findByUsername(String username){
        Predicate<? super Todo>predicate =todo->todo.getUsername().equalsIgnoreCase(username); //username이 매칭되면 그걸로 찾음
        return todos.stream().filter(predicate).toList();
    }

    //todo를 추가하기 위한 로직
    public void addTodo(String username, String description, LocalDate targetDate, boolean done){
        Todo todo =new Todo(++todosCount,username,description,targetDate,done);
        todos.add(todo);
    }

    public void deleteById(int id){
        //Todo에 매칭되는 id가 있는지 묻는 predicate를 정의
        //removeif()는 predicate를 모든 Todo에 실행하는 일을 한다.
        //리스트에 10개의 Todo가 있다면 모든 Todo의 id가 이 조건에 매칭되는지
        //확인하게 되고 그 조건에 매칭되면 그 Todo를 삭제할 것이다.
        Predicate<? super Todo>predicate =todo->todo.getId()==id;
        todos.removeIf(predicate);
    }

    public Todo findById(int id) {
        Predicate<? super Todo>predicate =todo->todo.getId()==id;
        Todo todo = todos.stream().filter(predicate).findFirst().get();
        return todo;
    }

    public void updateTodo(@Valid Todo todo) {
        deleteById(todo.getId()); //특정한 id의 Todo를 삭제
        todos.add(todo); //거기에 todo를 추가
    }
}
