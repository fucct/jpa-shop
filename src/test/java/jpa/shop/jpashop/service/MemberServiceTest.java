package jpa.shop.jpashop.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpa.shop.jpashop.domain.Member;
import jpa.shop.jpashop.repository.MemberRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void join() throws Exception {
        //given 
        Member member = new Member();
        member.setName("DD");
        //when 
        Long id = memberService.join(member);
        //then
        assertThat(member).isEqualTo(memberRepository.findOne(id));
    }
    
    @Test
    public void 중복_회원_검증() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("DD");

        Member member2 = new Member();
        member2.setName("DD");
        // when
        memberService.join(member1);
        // then
        assertThatThrownBy(()->memberService.join(member2))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageMatching("이미 존재하는 이름입니다");
    }

    
}