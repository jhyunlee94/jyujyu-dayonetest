package com.jyujuy.dayonetest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class JUnitPracticeTest {

    @Test
    public void assertEqualsTest() {
        String expect = "Something";
        String actual = "Something";

        Assertions.assertEquals(expect, actual);
    }

    @Test
    public void assertNotEqualsTest() {
        String expect = "Something";
        String actual = "Hello";

        Assertions.assertNotEquals(expect, actual);
    }

    @Test
    public void assertTrueTest() {
//        Boolean condition = 1 == 1;
        Integer expect = 10;
        Integer actual = 10;

        Assertions.assertEquals(expect, actual);
    }

    @Test
    public void assertFalseTest() {
        Integer expect = 10;
        Integer actual = 11;

        Assertions.assertNotEquals(expect, actual);
    }

    @Test
    public void assertThrowsTest() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            throw new RuntimeException("임의로 발생시킨 에러");
        });
    }

    @Test
    public void assertNotNullTest() {
        String value = "Hello";
        Assertions.assertNotNull(value);
    }

    @Test
    public void assertNullTest() {
        String value = null;
        Assertions.assertNull(value);
    }

    @Test
    public void assertIterableEquals() {
        List<Integer> list1 = List.of(1, 2);
        List<Integer> list2 = List.of(1, 2);

        Assertions.assertIterableEquals(list1, list2);
    }

    @Test
    void assertAllTest() {
        Integer expect = 10;
        Integer actual = 11;


        List<Integer> list1 = List.of(1, 2);
        List<Integer> list2 = List.of(1, 2);


        Assertions.assertAll("Assert All", List.of(
                () -> {Assertions.assertNotEquals(expect, actual);},
                () -> {Assertions.assertIterableEquals(list1, list2);}
        ));
    }
}
