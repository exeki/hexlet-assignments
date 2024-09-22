package exercise.controller;

import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

import org.instancio.Instancio;
import org.instancio.Select;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import exercise.repository.TaskRepository;
import exercise.model.Task;

import java.math.BigDecimal;
import java.util.HashMap;

// BEGIN
@SpringBootTest
@AutoConfigureMockMvc
// END
class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskRepository taskRepository;


    @Test
    public void testWelcomePage() throws Exception {
        var result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThat(body).contains("Welcome to Spring!");
    }

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }


    // BEGIN

    private Task getTaskProto() {
        return Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .ignore(Select.field(Task::getUpdatedAt))
                .ignore(Select.field(Task::getCreatedAt))
                .supply(Select.field(Task::getTitle), () -> faker.lorem().word())
                .supply(Select.field(Task::getDescription), () -> faker.lorem().paragraph())
                .create();
    }

    @Test
    public void getTaskTest() throws Exception {
        var task = taskRepository.save(getTaskProto());
        var result = mockMvc.perform(get("/tasks/" + task.getId()))
                .andExpect(status().isOk())
                .andReturn();
        var json = result.getResponse().getContentAsString();
        assertThatJson(json).node("id").isNumber().isEqualTo(BigDecimal.valueOf(task.getId()));
        assertThatJson(json).node("description").isString().isEqualTo(task.getDescription());
        assertThatJson(json).node("title").isString().isEqualTo(task.getTitle());
    }

    @Test
    public void createTaskTest() throws Exception {
        var task = getTaskProto();
        var result = mockMvc.perform(
                        post("/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(om.writeValueAsString(task))
                )
                .andExpect(status().isCreated())
                .andReturn();
        var json = result.getResponse().getContentAsString();
        assertThatJson(json).node("description").isString().isEqualTo(task.getDescription());
        assertThatJson(json).node("title").isString().isEqualTo(task.getTitle());
    }

    @Test
    public void editTaskTest() throws Exception {
        var existedTask = taskRepository.save(getTaskProto());
        System.out.println("Existed task id: " + existedTask.getId());
        var taskProto = new HashMap<>();
        taskProto.put("description", faker.lorem().paragraph());
        taskProto.put("title", faker.lorem().word());
        var result = mockMvc.perform(
                        put("/tasks/" + existedTask.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(om.writeValueAsString(taskProto))
                )
                .andExpect(status().isOk())
                .andReturn();
        var json = result.getResponse().getContentAsString();
        assertThatJson(json).node("id").isNumber().isEqualTo(BigDecimal.valueOf(existedTask.getId()));
        assertThatJson(json).node("description").isString().isEqualTo(taskProto.get("description"));
        assertThatJson(json).node("title").isString().isEqualTo(taskProto.get("title"));
    }

    @Test
    public void deleteTaskTest() throws Exception {
        var existedTask = taskRepository.save(getTaskProto());
        mockMvc.perform(delete("/tasks/" + existedTask.getId())).andExpect(status().isOk());
        mockMvc.perform(get("/tasks/" + existedTask.getId())).andExpect(status().isNotFound());
    }

    // END
}
