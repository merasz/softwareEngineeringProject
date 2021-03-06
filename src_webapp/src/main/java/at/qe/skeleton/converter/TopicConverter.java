package at.qe.skeleton.converter;


import at.qe.skeleton.model.Topic;
import at.qe.skeleton.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@Service
public class TopicConverter implements Converter {
    @Autowired
    private TopicService topicService;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s == null || s.equals("")){
            return null;
        }

        return topicService.loadTopic(s);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object val) {

        if(val == null){
            return null;
        }

        if(val.getClass().equals(String.class)) {
            return val.toString();
        }

        return ((Topic) val).getTopicName();
    }
}
