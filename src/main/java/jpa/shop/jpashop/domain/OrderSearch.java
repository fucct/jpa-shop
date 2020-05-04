package jpa.shop.jpashop.domain;

import org.springframework.lang.Nullable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

    @Nullable
    private String memberName;
    @Nullable
    private String orderStatus;
}
