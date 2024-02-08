package com.jyujuy.dayonetest;

import org.junit.jupiter.api.*;

import java.util.List;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class JUnitPracticeTest {

    @Test
    @DisplayName("Assert Equals 메소드 테스트")
    public void assert_Equals_Test() {
        String expect = "Something";
        String actual = "Something";

        Assertions.assertEquals(expect, actual);
    }

    @Test
    @DisplayName("Assert Not Equals 메소드 테스트")
    public void assertNotEqualsTest() {
        String expect = "Something";
        String actual = "Hello";

        Assertions.assertNotEquals(expect, actual);
    }

    @Test
    @DisplayName("Assert True 메소드 테스트")
    public void assertTrueTest() {
//        Boolean condition = 1 == 1;
        Integer expect = 10;
        Integer actual = 10;

        Assertions.assertEquals(expect, actual);
    }

    @Test
    @DisplayName("Assert False 메소드 테스트")
    public void assertFalseTest() {
        Integer expect = 10;
        Integer actual = 11;

        Assertions.assertNotEquals(expect, actual);
    }

    @Test
    @DisplayName("Assert Throws 메소드 테스트")
    public void assertThrowsTest() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            throw new RuntimeException("임의로 발생시킨 에러");
        });
    }

    @Test
    @DisplayName("Assert Not Null 메소드 테스트")
    public void assertNotNullTest() {
        String value = "Hello";
        Assertions.assertNotNull(value);
    }

    @Test
    @DisplayName("Assert Null 메소드 테스트")
    public void assertNullTest() {
        String value = null;
        Assertions.assertNull(value);
    }

    @Test
    @DisplayName("Assert Iterable 메소드 테스트")
    public void assertIterableEquals() {
        List<Integer> list1 = List.of(1, 2);
        List<Integer> list2 = List.of(1, 2);

        Assertions.assertIterableEquals(list1, list2);
    }

    @Test
    @DisplayName("Assert All 메소드 테스트")
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
