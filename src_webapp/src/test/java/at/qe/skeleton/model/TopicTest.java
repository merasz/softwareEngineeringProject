

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

class TopicTest {

    private at.qe.skeleton.model.Topic topicUnderTest;

@BeforeEach
void setUp() throws Exception {
                                topicUnderTest = new Topic("topicName") ;
}
                
    @Test
    void testIsNew() throws Exception {
    // Setup
        
    // Run the test
 final boolean result =  topicUnderTest.isNew();

        // Verify the results
 assertThat(result).isTrue() ;
    }
                                                                
    @Test
    void testEquals() throws Exception {
    // Setup
        
    // Run the test
 final boolean result =  topicUnderTest.equals("obj");

        // Verify the results
 assertThat(result).isTrue() ;
    }
                                                                
    @Test
    void testHashCode() throws Exception {
    // Setup
        
    // Run the test
 final int result =  topicUnderTest.hashCode();

        // Verify the results
 assertThat(result).isEqualTo( 0 ) ;
    }
                                                                
    @Test
    void testToString() throws Exception {
    // Setup
        
    // Run the test
 final java.lang.String result =  topicUnderTest.toString();

        // Verify the results
 assertThat(result).isEqualTo( "result" ) ;
    }
                                                    @Test
    void testGetSerialVersionUID() throws Exception {
                 assertThat( at.qe.skeleton.model.Topic.getSerialVersionUID() ).isEqualTo(0L) ;
                    }
}

