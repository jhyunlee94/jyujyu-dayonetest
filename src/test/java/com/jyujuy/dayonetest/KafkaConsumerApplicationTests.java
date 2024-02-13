package com.jyujuy.dayonetest;

import com.jyujuy.dayonetest.service.KafkaConsumerService;
import com.jyujuy.dayonetest.service.KafkaProducerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class KafkaConsumerApplicationTests extends IntegrationTest{
    @Autowired
    private KafkaProducerService kafkaProducerService;

    @MockBean
    private KafkaConsumerService kafkaConsumerService; // 어떤 빈을 사용하고싶을때 사용하는거 가짜 객체다라는 의미

    @Test
    public void kafkaSendAndConsumeTest() {
        // given
        String topic = "test-topic";
        String expectValue = "expect-value";

        // when
        kafkaProducerService.send(topic, expectValue);

        // then
        var stringCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(kafkaConsumerService, Mockito.timeout(5000).times(1))
                .process(stringCaptor.capture());

        Assertions.assertEquals(expectValue, stringCaptor.getValue());
    }
}
