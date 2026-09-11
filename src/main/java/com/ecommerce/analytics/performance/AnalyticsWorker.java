package com.ecommerce.analytics.performance;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AnalyticsWorker {

    // Пул потоков для симуляции параллельной асинхронной обработки
    private final ExecutorService executor = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors() * 2
    );

    /**
     * Асинхронно обрабатывает событие и вызывает callback по завершении
     */
    public void processAsync(Event event, Runnable onComplete) {
        executor.submit(() -> {
            try {
                // Симуляция быстрой логики обработки/записи в батч
                doWork(event);
            } finally {
                // Уведомляем CountDownLatch в тесте о завершении задачи
                onComplete.run();
            }
        });
    }

    private void doWork(Event event) {
        // Минимальная задержка или логика валидации
        if (event == null || event.type() == null) {
            throw new IllegalArgumentException("Invalid event");
        }
    }

    public void shutdown() {
        executor.shutdown();
    }
}