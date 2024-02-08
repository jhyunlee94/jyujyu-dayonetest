package com.jyujuy.dayonetest;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MyCalculatorTest {

    @Test
    void addTest() {
        // GWT 패턴
        // AAA 패턴

        // given - 준비
        // Arrange - 준비
        MyCalculator myCalculator = new MyCalculator();

        // when - 행동/연산
        // Act - 행동
        myCalculator.add(10.0);

        // then - 검증/비교/단언
        // Assert - 단연/검증
        assertThat(10.0).isEqualTo(myCalculator.getResult());
        Assertions.assertEquals(10.0, myCalculator.getResult());
    }

    @Test
    void minusTest() {
        // given
        MyCalculator myCalculator = new MyCalculator();

        // when
        myCalculator.add(10.0);
        myCalculator.minus(2.0);

        // then
        assertThat(8.0).isEqualTo(myCalculator.getResult());
        Assertions.assertEquals(8.0, myCalculator.getResult());
    }

    @Test
    void multiplyTest() {
        // given
        MyCalculator myCalculator = new MyCalculator(2.0);

        // when
        myCalculator.multiply(2.0);

        // then
        Assertions.assertEquals(4.0,myCalculator.getResult());
    }

    @Test
    void divideTest() {
        // given
        MyCalculator myCalculator = new MyCalculator(2.0);

        // when
        myCalculator.divide(1.0);

        // then
        Assertions.assertEquals(2.0,myCalculator.getResult());
    }

    @Test
    public void comliccatedCalculatedTest() {
        // given
        MyCalculator myCalculator = new MyCalculator(0.0);

        // when
        Double result = myCalculator
                .add(10.0)
                .minus(4.0)
                .multiply(2.0)
                .divide(3.0)
                .getResult();
        // then
        Assertions.assertEquals(4.0, result);
    }

    @Test
    void divideZeroTest() {
        // given
        MyCalculator myCalculator = new MyCalculator(10.0);

        // when
        // then
        //assertj 문법
        assertThatThrownBy(() -> {
            myCalculator.divide(0.0);
        }).isInstanceOf(MyCalculator.ZeroDivisionException.class);
        // junit 문법
        Assertions.assertThrows(MyCalculator.ZeroDivisionException.class, () -> {
            myCalculator.divide(0.0);
        });
    }
}