package jpa.shop.jpashop.service;

import static org.assertj.core.api.Assertions.*;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpa.shop.jpashop.domain.Address;
import jpa.shop.jpashop.domain.Member;
import jpa.shop.jpashop.domain.Order;
import jpa.shop.jpashop.domain.OrderStatus;
import jpa.shop.jpashop.domain.item.Book;
import jpa.shop.jpashop.domain.item.Item;
import jpa.shop.jpashop.exception.NotEnoughStockException;
import jpa.shop.jpashop.repository.OrderRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        // given
        Member member = createMember("DD", new Address("경기", "안양시", "123456"));

        Item item = createItem("고양이 디디", 100000, 10);

        // when
        Long id = orderService.order(member.getId(), item.getId(), 7);

        // then
        Order getOrder = orderRepository.findOne(id);
        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(getOrder.getOrderItems().size()).isEqualTo(1);
        assertThat(getOrder.getTotalPrice()).isEqualTo(700000);
        assertThat(item.getStockQuantity()).isEqualTo(3);
    }

    private Member createMember(String name, Address address) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(address);
        em.persist(member);
        return member;
    }

    private Item createItem(String name, int price, int quantity) {
        Item item = new Book();
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(quantity);
        em.persist(item);
        return item;
    }

    @Test
    public void 상품주문_재고초과() throws Exception {
        // given
        Member member = createMember("DD", new Address("경기", "안양시", "123456"));
        Item item = createItem("고양이 디디", 100000, 10);

        // when
        int orderCount = 12;

        // then
        assertThatThrownBy(()->
            orderService.order(member.getId(), item.getId(), orderCount))
            .isInstanceOf(NotEnoughStockException.class)
            .hasMessageMatching("재고가 없습니다");
    }

    @Test
    public void 주문취소() throws Exception {
        // given
        Member member = createMember("DD", new Address("경기", "안양시", "123456"));
        Item item = createItem("고양이 디디", 100000, 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        // when

        orderService.cancelOrder(orderId);
        // then
        Order getOrder = orderRepository.findOne(orderId);

        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(item.getStockQuantity()).isEqualTo(10);
    }

}