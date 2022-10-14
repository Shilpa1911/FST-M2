package FST_JunitSession.FST_JunitSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import junit.framework.Assert;

public class CalcTest {

	  private Calc calculator;
	  
	    @BeforeEach
	    public void setUp() throws Exception {
	    	calculator = new Calc();
	    }
	 
	    @Test
	    @DisplayName("Simple multiplication should work")
	    public void testMultiply() {
	        Assert.assertEquals(20, calculator.mutliply(4, 5));
	        
	    }
	
	
}
