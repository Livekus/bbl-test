packagecom.bbl.test.adapter.backend.controller;

import com.bbl.test.domain.model.User;
import com.bbl.test.application.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
  @Autowired MockMvc mvc;
  @MockBean UserService service;

  @Test
  void getUsers_ok() throws Exception {
    Mockito.when(service.findAll()).thenReturn(List.of(
        new User(1L, "Leanne Graham", "Bret", "Sincere@april.biz", "1-770-736-8031 x56442", "hildegard.org")
    ));

    mvc.perform(get("/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].username").value("Bret"));
  }

  @Test
  void createUser_201() throws Exception {
    Mockito.when(service.create(any())).thenReturn(
        new User(99L, "Jane Doe", "jdoe", "jane@doe.test", null, null)
    );

    String body = "{" +
        "\"name\":\"Jane Doe\"," +
        "\"username\":\"jdoe\"," +
        "\"email\":\"jane@doe.test\"}";

    mvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "/users/99"))
        .andExpect(jsonPath("$.id").value(99))
        .andExpect(jsonPath("$.email").value("jane@doe.test"));
  }
}