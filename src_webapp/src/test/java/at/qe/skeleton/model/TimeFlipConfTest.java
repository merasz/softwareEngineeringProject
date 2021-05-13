

package at.qe.skeleton.model;

import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;
    import static org.mockito.ArgumentMatchers.any;
    import static org.mockito.ArgumentMatchers.anyInt;
    import static org.mockito.ArgumentMatchers.anyString;
    import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;   
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import org.mockito.stubbing.Answer;

class TimeFlipConfTest {

    private at.qe.skeleton.model.TimeFlipConf timeFlipConfUnderTest;

@BeforeEach
void setUp() throws Exception {
                                timeFlipConfUnderTest = new TimeFlipConf(0,0,at.qe.skeleton.model.RequestType.PANTOMIME) ;
}
}

