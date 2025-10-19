package org.example.hrmOrange.allure;
import java.util.Objects;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Setter
@Accessors(chain = true, fluent = true)
public class AllureLogger {
    public static final String DEFAULT_LOG_LEVEL = "INFO";
    private static final String KEY_VALUE_PATTERN = "{}: {}";

    private final Logger logger;
    private Allure.StepContext stepContext;

    public AllureLogger(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    // #### The below methods for log info in both report and console

    public AllureLogger infoStep(String message) {
        Allure.step(message , () -> Allure.getLifecycle().updateStep(testResult -> testResult.setDescription(DEFAULT_LOG_LEVEL)));

        logger.info(message);
        return this;
    }

    public AllureLogger pass(String message) {
        Allure.step(message, Status.PASSED);

        logger.info(message);
        return this;
    }

    public AllureLogger fail(String message) {
        Allure.step(message, Status.FAILED);

        logger.error(message);
        return this;
    }

    public void failAndStop(String message) {
        fail(message);
        throw new AssertionError();
    }


    // #### The below methods for log with parameters, can use for both report and console

    public AllureLogger parameter(String message, Object param) {
        if (Objects.nonNull(stepContext)) {
            stepContext.parameter(message, param);
        }

        logger.info(KEY_VALUE_PATTERN, message, param);

        return this;
    }


    // #### The below methods for log console only

    public AllureLogger info(String message) {
        logger.info(message);
        return this;
    }

    public AllureLogger info(String message, Object param) {
        logger.info(KEY_VALUE_PATTERN, message, param);

        return this;
    }

    public AllureLogger debug(String message) {
        logger.debug(message);
        return this;
    }

    public AllureLogger debug(String message, Object param) {
        logger.debug(KEY_VALUE_PATTERN, message, param);
        return this;
    }


    public AllureLogger warn(String message) {
        logger.warn(message);
        return this;
    }

    public AllureLogger error(String message) {
        logger.error(message);
        return this;
    }

    public AllureLogger error(String message, Throwable t) {
        logger.error(message, t);
        return this;
    }

}
