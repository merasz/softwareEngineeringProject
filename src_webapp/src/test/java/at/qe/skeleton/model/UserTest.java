

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

class UserTest {

    private at.qe.skeleton.model.User userUnderTest;

@BeforeEach
void setUp() throws Exception {
                                userUnderTest = new User() ;
                userUnderTest.enabled =  false ;
}
                
    @Test
    void testHashCode() throws Exception {
    // Setup
        
    // Run the test
 final int result =  userUnderTest.hashCode();

        // Verify the results
 assertThat(result).isEqualTo( 0 ) ;
    }
                                                                
    @Test
    void testEquals() throws Exception {
    // Setup
        
    // Run the test
 final boolean result =  userUnderTest.equals("obj");

        // Verify the results
 assertThat(result).isTrue() ;
    }
                                                                
    @Test
    void testToString() throws Exception {
    // Setup
        
    // Run the test
 final java.lang.String result =  userUnderTest.toString();

        // Verify the results
 assertThat(result).isEqualTo( "result" ) ;
    }
                                                                
    @Test
    void testGetId() throws Exception {
    // Setup
        
    // Run the test
 final java.lang.String result =  userUnderTest.getId();

        // Verify the results
 assertThat(result).isEqualTo( "result" ) ;
    }
                                                                
    @Test
    void testIsNew() throws Exception {
    // Setup
        
    // Run the test
 final boolean result =  userUnderTest.isNew();

        // Verify the results
 assertThat(result).isTrue() ;
    }
                                                                
    @Test
    void testCompareTo() throws Exception {
    // Setup
                final at.qe.skeleton.model.User o = new at.qe.skeleton.model.User();
                o.setUsername("username");
                o.setPassword("password");
                o.setEnabled(false);
                                        o.setTeam(java.util.Arrays.asList(new at.qe.skeleton.model.Team(java.util.Arrays.asList(new at.qe.skeleton.model.User()), "teamName", new at.qe.skeleton.model.Game(0, 0, 0, new at.qe.skeleton.model.Topic("topicName"), new at.qe.skeleton.model.Raspberry(0, "hostname", java.util.Arrays.asList(new at.qe.skeleton.model.User()), false, "ipAddress")), java.util.Arrays.asList(new at.qe.skeleton.model.Score(0, 0L, null, new at.qe.skeleton.model.Game(0, 0, 0, new at.qe.skeleton.model.Topic("topicName"), new at.qe.skeleton.model.Raspberry(0, "hostname", java.util.Arrays.asList(new at.qe.skeleton.model.User()), false, "ipAddress")))), new java.util.HashMap<>())));
                o.setRoles(new java.util.HashSet<>(java.util.Arrays.asList(at.qe.skeleton.model.UserRole.ADMIN)));
                                        o.setRaspberry(new at.qe.skeleton.model.Raspberry(0, "hostname", java.util.Arrays.asList(new at.qe.skeleton.model.User()), false, "ipAddress"));
                o.setCreateUser(new at.qe.skeleton.model.User());
                o.setCreateDate(new java.util.GregorianCalendar(2020, java.util.Calendar.JANUARY, 1).getTime());
                o.setUpdateUser(new at.qe.skeleton.model.User());
                o.setUpdateDate(new java.util.GregorianCalendar(2020, java.util.Calendar.JANUARY, 1).getTime());

    // Run the test
 final int result =  userUnderTest.compareTo(o);

        // Verify the results
 assertThat(result).isEqualTo( 0 ) ;
    }
                                                                
    @Test
    void testCompareTo_ThrowsNullPointerException() throws Exception {
    // Setup
                final at.qe.skeleton.model.User o = new at.qe.skeleton.model.User();
                o.setUsername("username");
                o.setPassword("password");
                o.setEnabled(false);
                                        o.setTeam(java.util.Arrays.asList(new at.qe.skeleton.model.Team(java.util.Arrays.asList(new at.qe.skeleton.model.User()), "teamName", new at.qe.skeleton.model.Game(0, 0, 0, new at.qe.skeleton.model.Topic("topicName"), new at.qe.skeleton.model.Raspberry(0, "hostname", java.util.Arrays.asList(new at.qe.skeleton.model.User()), false, "ipAddress")), java.util.Arrays.asList(new at.qe.skeleton.model.Score(0, 0L, null, new at.qe.skeleton.model.Game(0, 0, 0, new at.qe.skeleton.model.Topic("topicName"), new at.qe.skeleton.model.Raspberry(0, "hostname", java.util.Arrays.asList(new at.qe.skeleton.model.User()), false, "ipAddress")))), new java.util.HashMap<>())));
                o.setRoles(new java.util.HashSet<>(java.util.Arrays.asList(at.qe.skeleton.model.UserRole.ADMIN)));
                                        o.setRaspberry(new at.qe.skeleton.model.Raspberry(0, "hostname", java.util.Arrays.asList(new at.qe.skeleton.model.User()), false, "ipAddress"));
                o.setCreateUser(new at.qe.skeleton.model.User());
                o.setCreateDate(new java.util.GregorianCalendar(2020, java.util.Calendar.JANUARY, 1).getTime());
                o.setUpdateUser(new at.qe.skeleton.model.User());
                o.setUpdateDate(new java.util.GregorianCalendar(2020, java.util.Calendar.JANUARY, 1).getTime());

    // Run the test
        assertThatThrownBy(() -> userUnderTest.compareTo(o)).isInstanceOf(java.lang.NullPointerException.class);
    }
                
    @Test
    void testCompareTo_ThrowsClassCastException() throws Exception {
    // Setup
                final at.qe.skeleton.model.User o = new at.qe.skeleton.model.User();
                o.setUsername("username");
                o.setPassword("password");
                o.setEnabled(false);
                                        o.setTeam(java.util.Arrays.asList(new at.qe.skeleton.model.Team(java.util.Arrays.asList(new at.qe.skeleton.model.User()), "teamName", new at.qe.skeleton.model.Game(0, 0, 0, new at.qe.skeleton.model.Topic("topicName"), new at.qe.skeleton.model.Raspberry(0, "hostname", java.util.Arrays.asList(new at.qe.skeleton.model.User()), false, "ipAddress")), java.util.Arrays.asList(new at.qe.skeleton.model.Score(0, 0L, null, new at.qe.skeleton.model.Game(0, 0, 0, new at.qe.skeleton.model.Topic("topicName"), new at.qe.skeleton.model.Raspberry(0, "hostname", java.util.Arrays.asList(new at.qe.skeleton.model.User()), false, "ipAddress")))), new java.util.HashMap<>())));
                o.setRoles(new java.util.HashSet<>(java.util.Arrays.asList(at.qe.skeleton.model.UserRole.ADMIN)));
                                        o.setRaspberry(new at.qe.skeleton.model.Raspberry(0, "hostname", java.util.Arrays.asList(new at.qe.skeleton.model.User()), false, "ipAddress"));
                o.setCreateUser(new at.qe.skeleton.model.User());
                o.setCreateDate(new java.util.GregorianCalendar(2020, java.util.Calendar.JANUARY, 1).getTime());
                o.setUpdateUser(new at.qe.skeleton.model.User());
                o.setUpdateDate(new java.util.GregorianCalendar(2020, java.util.Calendar.JANUARY, 1).getTime());

    // Run the test
        assertThatThrownBy(() -> userUnderTest.compareTo(o)).isInstanceOf(java.lang.ClassCastException.class);
    }
}

