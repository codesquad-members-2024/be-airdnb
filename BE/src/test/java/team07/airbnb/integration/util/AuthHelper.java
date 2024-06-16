package team07.airbnb.integration.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import team07.airbnb.common.auth.jwt.JwtUserDetails;
import team07.airbnb.common.auth.jwt.JwtUtil;
import team07.airbnb.data.user.dto.response.TokenUserInfo;
import team07.airbnb.data.user.enums.Role;
import team07.airbnb.entity.UserEntity;

import java.util.ArrayList;

@Component
public class AuthHelper {

    @Autowired
    private JwtUtil jwtUtil;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public String login(Role role) throws JsonProcessingException {
        UserEntity dummyUser = new UserEntity(null, "", "testUser", "test@test.com", role, "regTest", new ArrayList<>());
        entityManager.persist(dummyUser);

        TokenUserInfo dummyTUI = TokenUserInfo.of(dummyUser);
        JwtUserDetails dummyDetails = new JwtUserDetails(dummyTUI, null);


        return jwtUtil.generateToken(dummyDetails);
    }

    @Transactional
    public void logout() {
        entityManager.createNativeQuery("truncate table USERS");
    }
}
