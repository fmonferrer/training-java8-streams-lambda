package training.java8.log;

import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;
import java.util.function.Supplier;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static training.java8.log.Logger.ERROR;

public class LoggerTest {

    private MessageFormatter messageFormatter;
    private PrintStream printer;
    private Logger logger;

    @Before
    public void setUp() throws Exception {
        messageFormatter = mock(MessageFormatter.class);
        printer = mock(PrintStream.class);
        given(messageFormatter.format(anyString())).willReturn("String");
        logger = new Logger(ERROR, printer);
    }

    @Test
    public void not_lazy_logger_error_when_log_debug() throws Exception {
        logger.debug(messageFormatter.format("Hola"));

        verify(messageFormatter, times(1)).format(anyString());
        verify(printer, never()).println(anyString());
    }

    @Test
    public void not_lazy_logger_error_when_log_error() throws Exception {
        logger.error(messageFormatter.format("Hola"));

        verify(messageFormatter, times(1)).format(anyString());
        verify(printer, times(1)).println(anyString());
    }

    //    Try to avoid call to MessageFormatter.format

    @Test
    public void lazy_logger_error_when_log_debug() throws Exception {
        logger.debug(() -> messageFormatter.format("Hola"));

        verify(messageFormatter, never()).format(anyString());
        verify(printer, never()).println(anyString());
    }

    @Test
    public void lazy_logger_error_when_log_error() throws Exception {
        logger.error(() -> messageFormatter.format("Hola"));

        verify(messageFormatter, times(1)).format(anyString());
        verify(printer, times(1)).println(anyString());
    }
}