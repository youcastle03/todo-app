package com.springboot.myfirstwebapp.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface TodoRepository extends JpaRepository<Todo, Integer> {
    //username은 Todo Bean에 있는 속성이기에  SpringdataJpa는 username으로 검색하기 위한
    //메소드를 자동으로 제공한다.
    public List<Todo> findByUsername(String Username);
}
