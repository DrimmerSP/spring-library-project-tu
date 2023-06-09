package com.jpc16tuesday.springlibraryproject.library.controllers.mvc;

import com.jpc16tuesday.springlibraryproject.library.dto.AuthorDTO;
import com.jpc16tuesday.springlibraryproject.library.service.AuthorService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Slf4j
@Transactional
@Rollback(value = false)
public class MVCAuthorControllerTest
        extends CommonTestMVC {

    @Autowired
    private AuthorService authorService;

    //Создаем нового автора для создания через контроллер (тест дата)
    private final AuthorDTO authorDTO = new AuthorDTO("MVC_TestAuthorFio", LocalDate.now(), "Test Description", new ArrayList<>());
    private final AuthorDTO authorDTOUpdated = new AuthorDTO("MVC_TestAuthorFio_UPDATED", LocalDate.now(), "Test Description", new ArrayList<>());

    @Override
    @Test
    @DisplayName("Просмотр всех авторов через MVC контроллер")
    @Order(0)
    @WithAnonymousUser
    protected void listAll() throws Exception {
        log.info("Тест просмотра авторов MVC начат!");
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/authors")
                        .param("page", "1")
                        .param("size", "5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("authors/viewAllAuthors"))
                .andExpect(model().attributeExists("authors"))
                .andReturn();
    }



    /**
     * Метод, тестирующий создание автора через MVC-контроллер.
     * Авторизуемся под пользователем admin (можно выбрать любого),
     * создаем шаблон данных и вызываем MVC-контроллер с соответствующим маппингом и методом.
     * flashAttr - используется, чтобы передать ModelAttribute в метод контроллера
     * Ожидаем, что будет статус redirect (как у нас в контроллере) при успешном создании
     *
     * @throws Exception - любая ошибка
     */
    @Override
    @Test
    @Order(1)
    @DisplayName("Создание автора через MVC контроллер")
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    protected void createObject() throws Exception {
        log.info("Тест по созданию автора через MVC начат");
        mvc.perform(MockMvcRequestBuilders.post("/authors/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .flashAttr("authorForm", authorDTO)
                        .accept(MediaType.APPLICATION_JSON)
                       // .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/authors"))
                .andExpect(redirectedUrlTemplate("/authors"))
                .andExpect(redirectedUrl("/authors"));
        log.info("Тест по созданию автора через MVC закончен!");
    }

    @Order(2)
    @Test
    @DisplayName("Обновление автора через MVC контроллер")
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    //@WithUserDetails(userDetailsServiceBeanName = "customUserDetailsService", value = "andy_user")
    protected void updateObject() throws Exception {
        log.info("Тест по обновлению автора через MVC начат успешно");
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "authorFIO"));
        AuthorDTO foundAuthorForUpdate = authorService.searchAuthors(authorDTO.getAuthorFIO(), pageRequest).getContent().get(0);
        foundAuthorForUpdate.setAuthorFIO(authorDTOUpdated.getAuthorFIO());
        mvc.perform(post("/authors/update")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .flashAttr("authorForm", foundAuthorForUpdate)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/authors"))
                .andExpect(redirectedUrl("/authors"));
        log.info("Тест по обновлению автора через MVC закончен успешно");
    }

    @Order(3)
    @Test
    @DisplayName("Софт удаление автора через MVC контроллер, тестирование 'authors/delete'")
    @WithMockUser(username = "admin", roles = "ADMIN", password = "admin")
    @Override
    protected void deleteObject() throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "authorFIO"));
        AuthorDTO foundAuthorForDelete = authorService.searchAuthors(authorDTOUpdated.getAuthorFIO(), pageRequest).getContent().get(0);
        foundAuthorForDelete.setDeleted(true);
        mvc.perform(get("/authors/delete/{id}", foundAuthorForDelete.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/authors"))
                .andExpect(redirectedUrl("/authors"));

        AuthorDTO deletedAuthor = authorService.getOne(foundAuthorForDelete.getId());
        assertTrue(deletedAuthor.isDeleted());
        log.info("Тест по soft удалению автора через MVC закончен успешно!");
//        authorService.delete(foundAuthorForDelete.getId());
    }

}
