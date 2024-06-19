package team07.airbnb.integration.util;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Transactional
public class DataBaseHelper {

    private static final String FOREIGN_KEY_CHECK_FORMAT = "SET FOREIGN_KEY_CHECKS = %d";
    private static final String TRUNCATE_FORMAT = "TRUNCATE TABLE %s";

    private final List<String> tableNames = new ArrayList<>();

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void findDatabaseTableNames() {
        List<String> tableInfos = entityManager.createNativeQuery("SHOW TABLES").getResultList();
        tableNames.addAll(tableInfos);
    }

    public void clear(String... withOuts) {
        entityManager.clear();
        Set<String> names = new HashSet<>(Arrays.asList(withOuts));
        names.add("USERS"); // 유저 테이블은 초기화 제외
        names.add("DISCOUNT_POLICY");
        truncate(names);
    }

    public <T> T find(Long id, Class<T> tClass) {
        entityManager.clear();
        return entityManager.find(tClass, id);
    }

    public void put(List<Object> dummies) {
        entityManager.clear();
        for (Object dummy : dummies) {
            if (entityManager.contains(dummy)) entityManager.merge(dummy);
            else entityManager.persist(dummy);
        }
    }

    private void truncate(Set<String> withOut) {
        entityManager.createNativeQuery(String.format(FOREIGN_KEY_CHECK_FORMAT, 0)).executeUpdate(); // FK 제약조건 해제
        for (String tableName : tableNames) {
            if (withOut.contains(tableName)) continue;
            entityManager.createNativeQuery(String.format(TRUNCATE_FORMAT, tableName)).executeUpdate();
        }
        entityManager.createNativeQuery(String.format(FOREIGN_KEY_CHECK_FORMAT, 1)).executeUpdate();
    }
}
