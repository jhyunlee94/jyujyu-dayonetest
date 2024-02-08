package com.jyujuy.dayonetest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LombokTestDataTest {

    @Test
    public void testDataTest(){
        TestData testData = new TestData();
        testData.setName("jyujyu");

        Assertions.assertEquals("jyujyu", testData.getName());
        assertThat("jyujyu").isEqualTo(testData.getName());
    }
}
