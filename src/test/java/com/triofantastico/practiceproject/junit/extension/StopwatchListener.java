package com.triofantastico.practiceproject.junit.extension;

import lombok.extern.slf4j.Slf4j;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

@Slf4j
public class StopwatchListener implements TestExecutionListener {

    private static final long START_TIME = System.nanoTime();

    public void testPlanExecutionStarted(TestPlan testPlan) {
        log.info("Stopwatch started at: " + LocalTime.now());
    }

    public void testPlanExecutionFinished(TestPlan testPlan) {
        long elapsedTime = System.nanoTime() - START_TIME;
        long convertedNanoTime = TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
        log.info("Stopwatch stopped at: " + LocalTime.now() + ". Elapsed time rounded to seconds: "
                + convertedNanoTime + "s");
    }
}
