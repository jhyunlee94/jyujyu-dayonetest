package com.jyujyu.dayonetest;

import com.jyujyu.dayonetest.service.KafkaConsumerService;
import com.jyujyu.dayonetest.service.KafkaProducerService;
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

    // 실제로는 통합테스트를 할때
    // 정말 인프라레벨과 가까이 있는 코드들은 완벽하게 검증을하고
    // 그 이후에 서비스 레벨 로직은 모킹해서 딱 인프라 레이어 코드만 검증하게 됩니다.
    // 카프카는 send 를 잘하는지 consume을 잘 하는지 예시가 되겠죠
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
