import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {
    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
    }

    public String generateDate(long day, String pattern) {
        return LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern(pattern));

    }

    @Test
    public void testSendSuccessForm() {
        String planningDate = generateDate(3, "dd.MM.yyyy");

        $(By.cssSelector("[data-test-id=\"city\"] input")).setValue("Казань");
        $(By.cssSelector(".calendar-input__custom-control input")).doubleClick().sendKeys(planningDate);
        $(By.cssSelector("[data-test-id=\"name\"] input")).setValue("Артём-Алексей Петрович");
        $(By.cssSelector("[data-test-id=\"phone\"] input")).setValue("+98075673415");
        $(By.className("checkbox__box")).click();
        $(".button").click();
        $("[data-test-id=\"notification\"] .notification__title").shouldHave(Condition.text("Успешно!"), Duration.ofSeconds(15));
        $("[data-test-id=\"notification\"] .notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate));

    }

    @Test
    public void testValidateCity() {
        String planningDate = generateDate(3, "dd.MM.yyyy");

        $(By.cssSelector("[data-test-id=\"city\"] input")).setValue("Нью-Йорк");
        $(By.cssSelector(".calendar-input__custom-control input")).doubleClick().sendKeys(planningDate);
        $(By.cssSelector("[data-test-id=\"name\"] input")).setValue("Артем-Алексей Петрович");
        $(By.cssSelector("[data-test-id=\"phone\"] input")).setValue("+98075673415");
        $(By.className("checkbox__box")).click();
        $(".button").click();
        $("[data-test-id=\"city\"].input_invalid .input__sub").shouldHave(Condition.text("Доставка в выбранный город недоступна"), Duration.ofSeconds(15));
    }

    @Test
    public void testNoCity() {
        String planningDate = generateDate(3, "dd.MM.yyyy");

        $(By.cssSelector(".calendar-input__custom-control input")).doubleClick().sendKeys(planningDate);
        $(By.cssSelector("[data-test-id=\"name\"] input")).setValue("Артем-Алексей Петрович");
        $(By.cssSelector("[data-test-id=\"phone\"] input")).setValue("+98075673415");
        $(By.className("checkbox__box")).click();
        $(".button").click();
        $("[data-test-id=\"city\"].input_invalid .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"), Duration.ofSeconds(15));
    }

    @Test
    public void testValidateDate() {

        $(By.cssSelector("[data-test-id=\"city\"] input")).setValue("Казань");
        $(By.cssSelector(".calendar-input__custom-control input")).doubleClick().sendKeys(Keys.BACK_SPACE);
        $(By.cssSelector("[data-test-id=\"name\"] input")).setValue("Артем-Алексей Петрович");
        $(By.cssSelector("[data-test-id=\"phone\"] input")).setValue("+98075673415");
        $(By.className("checkbox__box")).click();
        $(".button").click();
        $("[data-test-id=\"date\"] .input_invalid .input__sub").shouldHave(Condition.text("Неверно введена дата"), Duration.ofSeconds(15));

    }

    @Test
    public void testValidateName() {
        String planningDate = generateDate(3, "dd.MM.yyyy");

        $(By.cssSelector("[data-test-id=\"city\"] input")).setValue("Казань");
        $(By.cssSelector(".calendar-input__custom-control input")).doubleClick().sendKeys(planningDate);
        $(By.cssSelector("[data-test-id=\"name\"] input")).setValue("NameFameli?(&$$%#%$@!#~:<>>?<,[]{}");
        $(By.cssSelector("[data-test-id=\"phone\"] input")).setValue("+98075673415");
        $(By.className("checkbox__box")).click();
        $(".button").click();
        $("[data-test-id=\"name\"].input_invalid .input__sub").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы"), Duration.ofSeconds(15));

    }

    @Test
    public void testNoName() {
        String planningDate = generateDate(3, "dd.MM.yyyy");

        $(By.cssSelector("[data-test-id=\"city\"] input")).setValue("Казань");
        $(By.cssSelector(".calendar-input__custom-control input")).doubleClick().sendKeys(planningDate);
        $(By.cssSelector("[data-test-id=\"phone\"] input")).setValue("+98075673415");
        $(By.className("checkbox__box")).click();
        $(".button").click();
        $("[data-test-id=\"name\"].input_invalid .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"), Duration.ofSeconds(15));

    }

    @Test
    public void testValidatePhone() {
        String planningDate = generateDate(3, "dd.MM.yyyy");

        $(By.cssSelector("[data-test-id=\"city\"] input")).setValue("Казань");
        $(By.cssSelector(".calendar-input__custom-control input")).doubleClick().sendKeys(planningDate);
        $(By.cssSelector("[data-test-id=\"name\"] input")).setValue("Артем-Алексей Петрович");
        $(By.cssSelector("[data-test-id=\"phone\"] input")).setValue("+9807");
        $(By.className("checkbox__box")).click();
        $(".button").click();
        $("[data-test-id=\"phone\"].input_invalid .input__sub").shouldHave(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."), Duration.ofSeconds(15));
    }

    @Test
    public void testNoPhone() {
        String planningDate = generateDate(3, "dd.MM.yyyy");

        $(By.cssSelector("[data-test-id=\"city\"] input")).setValue("Казань");
        $(By.cssSelector(".calendar-input__custom-control input")).doubleClick().sendKeys(planningDate);
        $(By.cssSelector("[data-test-id=\"name\"] input")).setValue("Артем-Алексей Петрович");
        $(By.className("checkbox__box")).click();
        $(".button").click();
        $("[data-test-id=\"phone\"].input_invalid .input__sub").shouldHave(Condition.text("Поле обязательно для заполнения"), Duration.ofSeconds(15));
    }

    @Test
    public void testValidateCheckBox() {
        String planningDate = generateDate(3, "dd.MM.yyyy");

        $(By.cssSelector("[data-test-id=\"city\"] input")).setValue("Казань");
        $(By.cssSelector(".calendar-input__custom-control input")).doubleClick().sendKeys(planningDate);
        $(By.cssSelector("[data-test-id=\"name\"] input")).setValue("Артем-Алексей Петрович");
        $(By.cssSelector("[data-test-id=\"phone\"] input")).setValue("+98075673415");
        $(".button").click();
        $("[data-test-id=\"agreement\"].input_invalid").shouldHave(Condition.text("Я соглашаюсь с условиями обработки и использования моих персональных данных"), Duration.ofSeconds(15));
    }

}