package sesrvices;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Учимся создавать заглушки
 * Stub - заглушки, предоставляющие заранее запрограммированые
 * для теста ответы/действия заданных методов*/
public class StubbingListTest {

    // данная аннотация не относится к теме теста
    // Ею мы просто подавляем, что идея ругается на (Дженерик?) - "mock" подсвечено желтым
    @SuppressWarnings("uncheked")
    private final List<Integer> mockedList = mock(List.class);

    @Test
    void testStubbing() {

        // в случае, если вызывается get с аргументом 0, то вернуть 10
        // при следующем вызове 20, а потом 30. Далее 30 будет всё время
        // when(mockedList.get(0)).thenReturn(10).thenReturn(20).thenReturn(30); // длинная запись
        // короткая запись + решили кинуть исключение на 4-ом шаге
        when(mockedList.get(0)).thenReturn(10, 20, 30).thenThrow(RuntimeException.class);
        when(mockedList.get(1)).thenThrow(IllegalArgumentException.class);
        when(mockedList.get(2)).thenReturn(100);

        // Для 0
        assertEquals(Integer.valueOf(10), mockedList.get(0));
        assertEquals(Integer.valueOf(20), mockedList.get(0));
        assertEquals(Integer.valueOf(30), mockedList.get(0));
        // сейчас и далее будет лишь Exception
        assertThrows(RuntimeException.class, () -> mockedList.get(0));
        assertThrows(RuntimeException.class, () -> mockedList.get(0));
        assertThrows(RuntimeException.class, () -> mockedList.get(0));

        // для 1
        assertThrows(RuntimeException.class, () -> mockedList.get(1));
        assertThrows(RuntimeException.class, () -> mockedList.get(1));

        // для 2
        assertEquals(Integer.valueOf(100), mockedList.get(2));

        /** Особые варианты */
        // если возвращаемый обьект не запрограммирован изначально,
        // то Мокито возвращает NULL
        assertNull(mockedList.get(100));
        // а если это boolean то FALSE
        assertFalse(mockedList.isEmpty());
        // если требуется вернуть примитив int то 0
        assertEquals(0, mockedList.size());
        // если требуется вернуть коллекцию, то вернется Empty коллекция
        assertTrue(mockedList.subList(0, 1000).isEmpty());

        // если требуется запрограмировать выброс исключения на метод, который НЕ возвращает значение
        // то условие нужно немного переписать (относительно предыдущих версий)
        doThrow(RuntimeException.class).when(mockedList).clear();

        assertThrows(RuntimeException.class, () -> mockedList.clear());
        assertThrows(RuntimeException.class, mockedList::clear);
    }
}
