package jpa.shop.jpashop.service;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jpa.shop.jpashop.domain.item.Book;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemUpdateTest {
    @Autowired
    EntityManager em;

    @Test
    public void updateTest() {
        Book book = em.find(Book.class, 1L);

        //TX
        book.setName("asedfwe");

        //변경감지 == dirty checking
    }
}
