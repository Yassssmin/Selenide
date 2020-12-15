package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    @BeforeEach
    void setUp() {
        openSite();
        clearDateField();
    }

    void openSite() {
        open("http://localhost:9999");
    }

    void clearDateField() {
        for (int i = 0; i < 10; i++) {
            $("input[type='tel']").toWebElement().sendKeys(Keys.BACK_SPACE);
        }
    }
    void clearDateFieldEmpty() {
        for (int i = 0; i < 10; i++) {
            $("input[type='tel']").setValue("");
        }
    }

    private String getValidDateString() {
        int daysToAdd = 3 + (int)(Math.random() * 5);

        LocalDate validDate = LocalDate.now().plusDays(daysToAdd);

        return formatDate(validDate);
    }

    private String getInvalidDateString() {
        return formatDate(LocalDate.now());
    }

    private String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateString = date.format(formatter);

        return dateString;
    }

    @Test
    void shouldCardDeliverySuccess() {
        $("input[type='text']").setValue("Москва");
        $("input[type='tel']").setValue(getValidDateString());
        $("input[name='name']").setValue("Василий Иванович");
        $("input[name='phone']").setValue("+79990873456");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $(byText("Успешно!")).waitUntil(visible, 15000);
    }
    @Test
    void shouldCardDeliverySuccessWithHyphen() {
        $("input[type='text']").setValue("Москва");
        $("input[type='tel']").setValue(getValidDateString());
        $("input[name='name']").setValue("Василий Иванович-оглы");
        $("input[name='phone']").setValue("+79990873456");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $(byText("Успешно!")).waitUntil(visible, 15000);
    }
    @Test
    void shouldCardDeliveryCityInvalid() {
        $("input[type='text']").setValue("Подольск");
        $("input[type='tel']").setValue(getValidDateString());
        $("input[name='name']").setValue("Василий Иванович");
        $("input[name='phone']").setValue("+79990873456");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $(byText("Доставка в выбранный город недоступна")).shouldHave(text("Доставка в выбранный город недоступна"));
    }
    @Test
    void shouldCardDeliveryCityEmpty() {
        $("input[type='text']").setValue("");
        $("input[type='tel']").setValue(getValidDateString());
        $("input[name='name']").setValue("Василий Иванович");
        $("input[name='phone']").setValue("+79990873456");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $(byText("Поле обязательно для заполнения")).shouldHave(text("Поле обязательно для заполнения"));
    }
    @Test
    void shouldCardDeliveryDataInvalid() {
        $("input[type='text']").setValue("Москва");
        $("input[type='tel']").toWebElement().sendKeys(Keys.BACK_SPACE);
        $("input[type='tel']").setValue("15.12.2020");
        $("input[name='name']").setValue("Василий Иванович");
        $("input[name='phone']").setValue("+79990873456");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $(byText("Заказ на выбранную дату невозможен")).shouldHave(text("Заказ на выбранную дату невозможен"));
    }
    @Test
    void shouldCardDeliveryDataEmpty() {
        $("input[type='text']").setValue("Москва");
        $("input[type='tel']").setValue("");
        $("input[name='name']").setValue("Василий Иванович");
        $("input[name='phone']").setValue("+79990873456");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $(byText("Неверно введена дата")).shouldHave(text("Неверно введена дата"));
    }
    @Test
    void shouldCardDeliveryNameAndSurnameInvalid() {
        $("input[type='text']").setValue("Москва");
        $("input[type='tel']").setValue(getValidDateString());
        $("input[name='name']").setValue("John Cooper");
        $("input[name='phone']").setValue("+79990873456");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $(byText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    void shouldCardDeliveryNameAndSurnameInvalidWithSymbols() {
        $("input[type='text']").setValue("Москва");
        $("input[type='tel']").setValue(getValidDateString());
        $("input[name='name']").setValue("!@#$%^&*()_+=/.,");
        $("input[name='phone']").setValue("+79990873456");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $(byText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    void shouldCardDeliveryNameAndSurnameEmpty() {
        $("input[type='text']").setValue("Москва");
        $("input[type='tel']").setValue(getValidDateString());
        $("input[name='name']").setValue("");
        $("input[name='phone']").setValue("+79990873456");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $(byText("Поле обязательно для заполнения")).shouldHave(text("Поле обязательно для заполнения"));
    }
    @Test
    void shouldCardDeliveryPhoneInvalid() {
        $("input[type='text']").setValue("Москва");
        $("input[type='tel']").setValue(getValidDateString());
        $("input[name='name']").setValue("Василий Иванович");
        $("input[name='phone']").setValue("79990873456");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $(byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
    @Test
    void shouldCardDeliveryPhoneInvalidWithSymbols() {
        $("input[type='text']").setValue("Москва");
        $("input[type='tel']").setValue(getValidDateString());
        $("input[name='name']").setValue("Василий Иванович");
        $("input[name='phone']").setValue("!@#$%^&*()_+=/.,");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $(byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
    @Test
    void shouldCardDeliveryPhoneEmpty() {
        $("input[type='text']").setValue("Москва");
        $("input[type='tel']").setValue(getValidDateString());
        $("input[name='name']").setValue("Василий Иванович");
        $("input[name='phone']").setValue("");
        $("[data-test-id=agreement]").click();
        $(withText("Забронировать")).click();
        $(byText("Поле обязательно для заполнения")).shouldHave(text("Поле обязательно для заполнения"));
    }
    @Test
    void shouldCardDeliveryFlagOff() {
        $("input[type='text']").setValue("Москва");
        $("input[type='tel']").setValue(getValidDateString());
        $("input[name='name']").setValue("Василий Иванович");
        $("input[name='phone']").setValue("+79990873456");
        $(withText("Забронировать")).click();
        $("[data-test-id=agreement]").shouldHave(cssClass("input_invalid"));
    }
}
