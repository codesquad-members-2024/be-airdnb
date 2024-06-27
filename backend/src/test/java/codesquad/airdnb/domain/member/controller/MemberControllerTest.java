package codesquad.airdnb.domain.member.controller;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import codesquad.airdnb.domain.member.dto.request.LoginRequest;
import codesquad.airdnb.domain.member.dto.request.RegisterRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)) // REST Docs 설정 적용
                .alwaysDo(document("{method-name}", preprocessRequest(), preprocessResponse())) // 기본 문서화 설정
                .build();
    }

    @Test
    public void register() throws Exception {
        RegisterRequest request = new RegisterRequest("testAccount", "testPassword", "testNickname");

        this.mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(document("register",
                        requestFields(
                                fieldWithPath("accountName").description("계정명"),
                                fieldWithPath("password").description("비밀번호"),
                                fieldWithPath("nickname").description("닉네임")
                        ),
                        responseFields(
                                fieldWithPath("nickname").description("가입에 성공해 로그인된 사용자의 닉네임"),
                                fieldWithPath("accessToken").description("가입에 성공해 로그인된 사용자의 액세스 토큰"),
                                fieldWithPath("refreshToken").description("가입에 성공해 로그인된 사용자의 리프레시 토큰"),
                                fieldWithPath("memberId").description("가입에 성공해 로그인된 사용자의 멤버ID")
                        )
                ));
    }

    @Test
    public void login() throws Exception {
        LoginRequest request = new LoginRequest("testAccount", "testPassword");

        this.mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(document("login",
                        requestFields(
                                fieldWithPath("accountName").description("계정명"),
                                fieldWithPath("password").description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("nickname").description("로그인된 사용자의 닉네임"),
                                fieldWithPath("accessToken").description("로그인된 사용자의 액세스 토큰"),
                                fieldWithPath("refreshToken").description("로그인된 사용자의 리프레시 토큰"),
                                fieldWithPath("memberId").description("로그인된 사용자의 멤버ID")
                        )
                ));
    }

    @Test
    public void logout() throws Exception {
        String testToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNzE5NDkwNjc5LCJleHAiOjE3MjcyNjY2Nzl9.s954VGe6g1W-xAmg_JCgs-xUioieHxkrELpdnOEFMks";
        this.mockMvc.perform(post("/api/auth/logout")
                        .header("Authorization", "Bearer " + testToken))
                .andExpect(status().isOk())
                .andDo(document("logout", requestHeaders(
                        headerWithName("Authorization").description("JWT 액세스 토큰"))));
    }
}