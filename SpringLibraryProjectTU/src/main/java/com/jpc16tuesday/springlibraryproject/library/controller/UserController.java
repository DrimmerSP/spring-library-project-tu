package com.jpc16tuesday.springlibraryproject.library.controller;


import com.jpc16tuesday.springlibraryproject.library.dto.UserDTO;
import com.jpc16tuesday.springlibraryproject.library.model.User;
import com.jpc16tuesday.springlibraryproject.library.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(name = "Пользователи",
     description = "Контроллер для работы с пользователями библиотеки")
public class UserController
      extends GenericController<User, UserDTO> {
    public UserController(UserService userService) {
        super(userService);
    }
}
