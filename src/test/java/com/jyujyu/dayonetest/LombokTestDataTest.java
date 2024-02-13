package com.jyujyu.dayonetest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LombokTestDataTest {

  @Test
  public void testDataTest() {
    TestData testData = new TestData();
    testData.setName("jyujyu");

    Assertions.assertEquals("jyujyu", testData.getName());
    assertThat("jyujyu").isEqualTo(testData.getName());
  }
}
