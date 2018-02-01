import common.AStockHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * common.AStockHelper Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>���� 1, 2018</pre>
 */
public class AStockHelperTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: prefix(String code)
     */
    @Test
    public void testPrefix() throws Exception {
        Assert.assertEquals(AStockHelper.prefix("300123"), "sz300123");
    }


} 
