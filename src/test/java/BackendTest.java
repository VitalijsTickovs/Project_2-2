import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class BackendTest {
    @BeforeEach()
    void init(){
        Assumptions.assumeTrue(System.getProperty("os.name").contains("Mac"));
    }

    @Test
    void test(){
        assertEquals(20,20);
    }
}
