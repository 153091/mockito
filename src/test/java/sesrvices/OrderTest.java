package sesrvices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.NoSuchElementException;

import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderTest {

    // @Mock - делает эту переменную Мок обьектом.
    // Это имитация класса.
    // Запишет вызовы всех методов (+ аргументы)
    @Mock
    private  BankAccount bankAccount;
    @Mock
    private PromotionService promotionService;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order(promotionService);
    }

    @Test
    void testSucceedIfEnoughFunds() throws NoSuchElementException {
        Item cake = new Item("Cake", valueOf(70));

        order.buyItem(cake, bankAccount);

        assertAll(
                () -> verify(bankAccount).withdraw(cake.getPrice()),
                () -> verify(promotionService).getGiftsByItem(cake),
                () -> assertTrue(order.getItems().contains(cake),
                        "Приобретаемый товар добавлен в список приеобретенных")
        );
    }

    @Test
    void testGiftsAddedToOrderItems() {
        Item cake = new Item("Cake", valueOf(70));
        Item candy = new Item("Candy", valueOf(10));
        //   *** Пишем заглушку на метод ***
        // Если вызывается метод getGiftsByItem с аргументом cake,
        // то вернуть список из одного элемента, которым является candy
        when(promotionService.getGiftsByItem(cake)).thenReturn(Collections.singletonList(candy));

        order.buyItem(cake, bankAccount);

        assertAll(
                () -> verify(promotionService).getGiftsByItem(cake),
                () -> assertTrue(order.getItems().contains(cake)),
                () -> assertTrue(order.getItems().contains(candy))
        );
    }

}