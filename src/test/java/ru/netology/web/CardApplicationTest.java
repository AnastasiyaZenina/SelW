package ru.netology.web;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardApplicationTest {
    @Test
    void cardOrderRequest() {
        open("http://localhost:9999");
        $("[type='text']").setValue("Анна Иванова");
        $("[type='tel']").setValue("+79301003496");
        $("[data-test-id='agreement']").click();
        $("[type='button']").click();
        $("[data-test-id='order-success']").shouldHave(text("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));

    }

    @Test
    void nameFieldEmpty() {
        open("http://localhost:9999");
        $("[type='text']").setValue("");
        $("[type='tel']").setValue("+79301003496");
        $("[data-test-id='agreement']").click();
        $("[type='button']").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void phoneNumberIncorrect() {
        open("http://localhost:9999");
        $("[type='text']").setValue("Анна Петрова");
        $("[type='tel']").setValue("+7930100");
        $("[data-test-id='agreement']").click();
        $("[type='button']").click();
        $("[data-test-id='phone'].input_invalid").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void formWithoutAgreement() {
        open("http://localhost:9999");
        $("[type='text']").setValue("Анна Петрова");
        $("[type='tel']").setValue("+79301003496");
        $("[data-test-id='agreement']");
        $("[type='button']").click();
        $("[data-test-id='agreement'].input_invalid .checkbox__text").shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй "));
    }
}
