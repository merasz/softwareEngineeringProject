

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

        @ExtendWith(MockitoExtension.class)
class TimeFlipTest {

            @Mock
        private java.util.Date mockLastSynchronization;
            @Mock
        private at.qe.skeleton.model.Raspberry mockRaspberry;
            @Mock
        private at.qe.skeleton.model.TimeFlipSetup mockTimeFlipSetup;
            @Mock
        private java.util.Set<at.qe.skeleton.model.TimeFlipSetupStatus> mockTimeFlipSetupStatuses;
            @Mock
        private java.util.List<at.qe.skeleton.model.Task> mockTasks;

    private at.qe.skeleton.model.TimeFlip timeFlipUnderTest;

@BeforeEach
void setUp() throws Exception {
                                timeFlipUnderTest = new TimeFlip("timeFlipName",mockLastSynchronization,0,0,mockRaspberry,mockTimeFlipSetup,false,mockTimeFlipSetupStatuses,mockTasks) ;
}
    @Test
    void testGetSerialVersionUID() throws Exception {
                 assertThat( at.qe.skeleton.model.TimeFlip.getSerialVersionUID() ).isEqualTo(0L) ;
                    }
}

