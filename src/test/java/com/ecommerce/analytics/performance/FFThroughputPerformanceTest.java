package com.ecommerce.analytics.performance;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class FFThroughputPerformanceTest {

    private static final int REQUIRED_EVENTS_PER_SECOND = 5000;
    private static final int TEST_BATCH_SIZE = 50000;

    @Test
    void workerMustProcessMinRequiredEventsPerSecond() throws InterruptedException {
        AnalyticsWorker worker = new AnalyticsWorker();
        CountDownLatch latch = new CountDownLatch(TEST_BATCH_SIZE);

        Instant start = Instant.now();

        for (int i = 0; i < TEST_BATCH_SIZE; i++) {
            worker.processAsync(new Event("VIEW_ITEM", "user_123"), latch::countDown);
        }

        boolean completedInTime = latch.await(15, TimeUnit.SECONDS);
        Instant finish = Instant.now();

        long timeElapsedMillis = Duration.between(start, finish).toMillis();
        double actualEventsPerSecond = ((double) TEST_BATCH_SIZE / timeElapsedMillis) * 1000;

        worker.shutdown();

        Assertions.assertTrue(completedInTime, "Обработка заняла слишком много времени.");
        Assertions.assertTrue(actualEventsPerSecond >= REQUIRED_EVENTS_PER_SECOND,
                String.format("Фитнес-функция НЕ ПРОЙДЕНА: Текущая скорость %.2f msg/sec ниже требуемого порога в %d msg/sec",
                        actualEventsPerSecond, REQUIRED_EVENTS_PER_SECOND));
    }
}



