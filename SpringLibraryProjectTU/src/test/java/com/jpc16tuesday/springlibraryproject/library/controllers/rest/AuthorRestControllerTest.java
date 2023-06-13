package com.jpc16tuesday.springlibraryproject.library.controllers.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jpc16tuesday.springlibraryproject.library.dto.AddBookDTO;
import com.jpc16tuesday.springlibraryproject.library.dto.AuthorDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class AuthorRestControllerTest
      extends CommonTestREST {
    private static Long createdTestAuthorId;
    
    @Test
    @Order(0)
    @Override
    protected void listAll() throws Exception {
        log.info("Тест по просмотру всех авторов через REST начат");
        String result = mvc.perform(
                    MockMvcRequestBuilders.get("/rest/authors/getAll")
                          .headers(super.headers)
                          .contentType(MediaType.APPLICATION_JSON)
                          .accept(MediaType.APPLICATION_JSON)
                                   )
              .andDo(print())
              .andExpect(status().is2xxSuccessful())
              .andExpect(jsonPath("$.*", hasSize(greaterThan(0))))
              .andReturn()
              .getResponse()
              .getContentAsString();
        List<AuthorDTO> authorDTOS = objectMapper.readValue(result, new TypeReference<List<AuthorDTO>>() {});
        authorDTOS.forEach(a -> log.info(a.toString()));
        log.info("Тест по просмотру всех авторов через REST закончен");
    }
    
    @Test
    @Order(1)
    @Override
    protected void createObject() throws Exception {
        log.info("Тест по созданию автора через REST начат");
        //Создаем нового автора через REST-контроллер
        AuthorDTO authorDTO = new AuthorDTO("REST_TestAuthorFIO", LocalDate.now(), "Test Author Description", new ArrayList<>());
        
        /*
        Вызываем метод создания (POST) в контроллере, передаем ссылку на REST API в MOCK.
        В headers передаем токен для авторизации (под админом, смотри родительский класс).
        Ожидаем, что статус ответа будет успешным и что в ответе есть поле ID, а далее возвращаем контент как строку
        Все это мы конвертируем в AuthorDTO при помощи ObjectMapper от библиотеки Jackson.
        Присваиваем в статическое поле ID созданного автора, чтобы далее с ним работать.
         */
        AuthorDTO result = objectMapper.readValue(
              mvc.perform(
                          MockMvcRequestBuilders.post("/rest/authors/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(super.headers)
                                .content(asJsonString(authorDTO))
                                .accept(MediaType.APPLICATION_JSON)
                         )
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                    .andReturn()
                    .getResponse()
                    .getContentAsString(),
              AuthorDTO.class);
        createdTestAuthorId = result.getId();
        log.info("Тест по созданию автора через REST закончен");
    }
    
    @Test
    @Order(2)
    @Override
    protected void updateObject() throws Exception {
        log.info("Тест по обновлению автора через REST начат");
        AuthorDTO existingTestAuthor = objectMapper.readValue(
              mvc.perform(
                          MockMvcRequestBuilders.get("/rest/authors/getOneById")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(super.headers)
                                .param("id", String.valueOf(createdTestAuthorId))
                                .accept(MediaType.APPLICATION_JSON)
                         )
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                    .andReturn()
                    .getResponse()
                    .getContentAsString(),
              AuthorDTO.class);
        //обновляем поля
        existingTestAuthor.setAuthorFIO("REST_TestAuthorFIO_UPDATED");
        existingTestAuthor.setDescription("REST_TestAuthorDescription_UPDATED");
        
        //вызываем update по REST API
        mvc.perform(
                    MockMvcRequestBuilders.put("/rest/authors/update")
                          .contentType(MediaType.APPLICATION_JSON)
                          .headers(super.headers)
                          .content(asJsonString(existingTestAuthor))
                          .param("id", String.valueOf(createdTestAuthorId))
                          .accept(MediaType.APPLICATION_JSON)
                   )
              .andDo(print())
              .andExpect(status().is2xxSuccessful());
        log.info("Тест по обновлению автора через REST закончен");
    }
    
    @Test
    @Order(3)
    void addBook() throws Exception {
        log.info("Тест по добавлению книги к автору через REST начат");
        AddBookDTO addBookDTO = new AddBookDTO(1L, createdTestAuthorId);
        String result = mvc.perform(
                    MockMvcRequestBuilders.post("/rest/authors/addBook")
                          .contentType(MediaType.APPLICATION_JSON)
                          .headers(super.headers)
                          .content(asJsonString(addBookDTO))
                          .accept(MediaType.APPLICATION_JSON)
                                   )
              .andExpect(status().is2xxSuccessful())
              .andReturn()
              .getResponse()
              .getContentAsString();
        
        AuthorDTO authorDTO = objectMapper.readValue(result, AuthorDTO.class);
        log.info("Тест по добавлению книги к автору через REST закончен. Результат: {}", authorDTO);
    }
    
    @Test
    @Order(4)
    @Override
    protected void deleteObject() throws Exception {
        log.info("Тест по удалению автора через REST начат");
        mvc.perform(
                    MockMvcRequestBuilders.delete("/rest/authors/delete/{id}", createdTestAuthorId)
                          .contentType(MediaType.APPLICATION_JSON)
                          .headers(super.headers)
                          .accept(MediaType.APPLICATION_JSON)
                   )
              .andDo(print())
              .andExpect(status().is2xxSuccessful());
        
        AuthorDTO existingTestAuthor = objectMapper.readValue(
              mvc.perform(
                          MockMvcRequestBuilders.get("/rest/authors/getOneById")
                                .contentType(MediaType.APPLICATION_JSON)
                                .headers(super.headers)
                                .param("id", String.valueOf(createdTestAuthorId))
                                .accept(MediaType.APPLICATION_JSON)
                         )
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                    .andReturn()
                    .getResponse()
                    .getContentAsString(),
              AuthorDTO.class);
//        assertTrue(existingTestAuthor.isDeleted());
        log.info("Тест по удалению автора через REST закончен");
        mvc.perform(
                    MockMvcRequestBuilders.delete("/rest/authors/delete/hard/{id}", createdTestAuthorId)
                          .contentType(MediaType.APPLICATION_JSON)
                          .headers(super.headers)
                          .accept(MediaType.APPLICATION_JSON)
                   )
              .andDo(print())
              .andExpect(status().is2xxSuccessful());
        log.info("Данные очищены!");
    }
}
