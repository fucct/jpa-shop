package jpa.shop.jpashop.repository;

import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import jpa.shop.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (Objects.isNull(item.getId())) {
            em.persist(item);
        } else{
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
            .getResultList();
    }
}
