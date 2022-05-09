package ru.netology.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardApplicationTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

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
    void invalidename() {
        open("http://localhost:9999");
        $("[type='text']").setValue("Anna Ivanova");
        $("[type='tel']").setValue("+79301003496");
        $("[data-test-id='agreement']").click();
        $("[type='button']").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void doubleSurname() {
        open("http://localhost:9999");
        $("[type='text']").setValue("Анна Иванова-Петрова");
        $("[type='tel']").setValue("+79301003496");
        $("[data-test-id='agreement']").click();
        $("[type='button']").click();
        $("[data-test-id='order-success']").shouldHave(text("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));

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
    void emptyNumberField() {
        open("http://localhost:9999");
        $("[type='text']").setValue("Анна Петрова");
        $("[type='tel']").setValue("");
        $("[data-test-id='agreement']").click();
        $("[type='button']").click();
        $("[data-test-id='phone'].input_invalid").shouldHave(text("Поле обязательно для заполнения"));
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
