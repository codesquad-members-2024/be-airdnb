package com.airdnb.member;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("인증이 안됐을때 회원가입 페이지 외에는 접근 불가")
    public void testReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/api/members"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("회원 정보를 입력하지 않으면 예외를 던진다.")
    public void testReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/members/register"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("회원가입을 성공하면 userid 경로를 반환")
    public void testReturnCreated() throws Exception {
        mockMvc.perform(post("/api/members/register")
            .content("{\"id\":\"test\",\"name\":\"test\",\"password\":\"test\",\"role\":\"USER\"}")
            .contentType("application/json"))
            .andExpect(status().isCreated());
    }

}
