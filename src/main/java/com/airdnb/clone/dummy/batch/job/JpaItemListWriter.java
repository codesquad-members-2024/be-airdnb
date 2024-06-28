package com.airdnb.clone.dummy.batch.job;

import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class JpaItemListWriter<T> extends JpaItemWriter<List<T>> {

    private final JpaItemWriter<T> jpaItemWriter;
    private final EntityManagerFactory entityManagerFactory;

    public JpaItemListWriter(JpaItemWriter<T> jpaItemWriter, EntityManagerFactory entityManagerFactory) {
        this.jpaItemWriter = jpaItemWriter;
        this.entityManagerFactory = entityManagerFactory;
        this.jpaItemWriter.setEntityManagerFactory(this.entityManagerFactory);
    }

    @Override
    public void write(Chunk<? extends List<T>> items) {
        for (List<T> itemT : items) {
            Chunk<T> stays = new Chunk<>();
            stays.addAll(itemT);
            jpaItemWriter.write(stays);
        }
    }
}
