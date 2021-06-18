package at.qe.skeleton.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomConverterTest {

    private CustomConverter customConverterUnderTest;

    @BeforeEach
    void setUp() {
        customConverterUnderTest = new CustomConverter();
    }

    @Test
    void testGetAsObject() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            final FacesContext context = FacesContext.getCurrentInstance();
            final UIComponent component = UIComponent.getCurrentComponent(FacesContext.getCurrentInstance());
//        final Object result = customConverterUnderTest.getAsObject(context, component, "value");
        });
   }

    @Test
    void testGetAsString() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            final FacesContext context = FacesContext.getCurrentInstance();
            final UIComponent component = UIComponent.getCurrentComponent(FacesContext.getCurrentInstance());
            final String result = customConverterUnderTest.getAsString(context, component, "value");
            assertThat(result).isEqualTo("result");
        });
    }
}
