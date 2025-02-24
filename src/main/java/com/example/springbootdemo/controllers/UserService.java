package com.example.springbootdemo.controllers;

import com.example.springbootdemo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService
{

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

//    public Mono<String> addUser(User newUser)
//    {
//        return "SUCCESS";
//    }
    /**
     * Аналог findByIdAndUpdate() из Mongoose.
     *
     * @param id         ID документа для обновления.
     * @param updatedUser Объект с новыми данными для обновления.
     * @return Mono<User> — обновленный пользователь.
     */
    public Mono<User> findByIdAndUpdate(Long id, User updatedUser)
    {
        Query query = Query.query(Criteria.where("id").is(id));

        // Создаем объект Update для обновления полей
        Update update = new Update()
                .set("personalInfo", updatedUser.getPersonalInfo())
                .set("education", updatedUser.getEducation())
                .set("employment", updatedUser.getEmployment())
                .set("contactInfo", updatedUser.getContactInfo())
                .set("skills", updatedUser.getSkills());

        // Выполняем частичное обновление и возвращаем обновленный документ
        return mongoTemplate.findAndModify(
                query,
                update,
                new FindAndModifyOptions().returnNew(true), // Возвращаем обновленный документ
                User.class
        );
    }
}