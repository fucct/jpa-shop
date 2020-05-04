package jpa.shop.jpashop.controller;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {

    @NotEmpty(message = "이름은 필수 입력 항목입니다.")
    private String name;

    private String city;
    private String street;
    private String zipCode;

}
