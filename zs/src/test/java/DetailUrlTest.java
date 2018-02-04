import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.time.LocalDate;

/** 
* DayDetailUrl Tester.
* 
* @author <Authors name> 
* @since <pre>���� 1, 2018</pre> 
* @version 1.0 
*/ 
public class DetailUrlTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getUrl() 
* 
*/ 
@Test
public void testGetUrl() throws Exception {
    String url = new DayDetailUrl("333333", LocalDate.now()).getUrl();
    System.out.println(url);
//TODO: Test goes here... 
} 


} 
