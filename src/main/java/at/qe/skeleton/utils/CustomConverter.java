package at.qe.skeleton.utils;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.WeakHashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * Converter like the {@code omnifaces.SelectItemsConverter}. The Entity needs a
 * good toString(), hashCode() and equals() function
 *
 * @author Üner Ismail
 * @see <a href=
 *      "http://showcase.omnifaces.org/converters/SelectItemsConverter">omnifaces.org/converters/SelectItemsConverter</a>
 *
 *      Converter defined in faces-config.xml because the
 *      notation @FacesConverter doesn't work
 */

// @FacesConverter(value="labItemConverter", forClass=LabItem.class)
public class CustomConverter implements Converter
{

    private static Map<Object, String> entities = new WeakHashMap<Object, String>();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {

        if (value == null || value.length() == 0)
        {
            return null;
        }

        for (Entry<Object, String> entry : entities.entrySet())
        {
            if (entry.getValue().equals(value))
            {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {

        synchronized (entities)
        {
            if (!entities.containsKey(value))
            {
                String uuid = UUID.randomUUID().toString();
                entities.put(value, uuid);
                return uuid;
            } else
            {
                return entities.get(value);
            }
        }
    }
}
