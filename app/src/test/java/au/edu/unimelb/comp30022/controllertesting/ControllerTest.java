package au.edu.unimelb.comp30022.controllertesting;

import android.location.Location;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;

/**
 * Created by william on 31/8/17, edited by Nathan
 */
//@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {
    private EditText sourcePostCode;
    private EditText destinationPostCode;

    private AddressTools addressToolsMock = Mockito.mock(AddressTools.class);
    private PostcodeValidator postcodeValidatorMock = Mockito.mock(PostcodeValidator.class);
    private PostageRateCalculator postageRateCalculatorMock = Mockito.mock(PostageRateCalculator.class);

    private Controller testController;

    @Before
    public void setUp() throws Exception {
        sourcePostCode = new EditText();
        sourcePostCode.setText("3133");
        destinationPostCode = new EditText();
        destinationPostCode.setText("3000");
        UI.addWidget("SOURCE_POST_CODE", sourcePostCode);
        UI.addWidget("DESTINATION_POST_CODE", destinationPostCode);
        UI.addWidget("COST_LABEL", new TextView());
        testController = new Controller(addressToolsMock, postcodeValidatorMock, postageRateCalculatorMock);

    }

    @Test
    public void calculateButtonPressed() throws Exception{
        Mockito.when(addressToolsMock.locationFromAddress(any(Address.class))).thenReturn(any(Location.class));
        Mockito.when(postcodeValidatorMock.isValid("3133")).thenReturn(true);
        Mockito.when(postageRateCalculatorMock.computeCost(any(Location.class),any(Location.class))).thenReturn(5);
        assertEquals("3133", testController.sourcePostCodeField.getText());
        assertEquals("3000", testController.destinationPostCodeField.getText());
        testController.calculateButtonPressed();
        Mockito.verify(postageRateCalculatorMock).computeCost(any(Location.class),any(Location.class));
        assertEquals("$5", testController.costLabel.getText());

    }

    @After
    public void tearDown() throws Exception {

    }

}