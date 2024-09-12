package org.example.expert.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.expert.config.JwtFilter;
import org.example.expert.config.MockTestFilter;
import org.example.expert.domain.user.dto.request.UserChangePasswordRequest;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.service.UserService;
import org.example.expert.global.common.DataSetting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(
        controllers = {UserController.class},
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = JwtFilter.class
                )
        }
)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() {
        // mockMvc에 사용할 Filter를 MockTestFilter로 지정
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilters(new MockTestFilter())
                .build();
    }

    @Test
    @DisplayName("success : 유저 비밀번호 변경")
    public void test1 () throws Exception {
        // given
        User user = DataSetting.createUser();
        ReflectionTestUtils.setField(user,"id",1L);

        UserChangePasswordRequest userChangePasswordRequest = DataSetting.userChangePasswordRequest();
        String changePassword = new ObjectMapper().writeValueAsString(userChangePasswordRequest); // 이게 스트링을 json 만들어주는거임

        // when - then
        mockMvc.perform(put("/users").contentType(MediaType.APPLICATION_JSON)
                        .content(changePassword)  // 요청 본문 JSON 데이터
                        .requestAttr("userId",1L)
                        .requestAttr("email","test@test.test")
                        .requestAttr("userRole","USER")) // 어트리뷰터 넣는 법
                .andExpect(status().isOk())
                .andDo(print()); // 디테일하게 콘솔창에 뜸

    }

}
