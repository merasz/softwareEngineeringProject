package at.qe.skeleton.utils;

import at.qe.skeleton.model.Term;
import at.qe.skeleton.services.TermsService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TermConverter {
    void runner(TermsService termsService) {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<Term> typeReference = new TypeReference<Term>(){};
        InputStream inputStream = TypeReference.class.getResourceAsStream("terms.json");
        try {
            List<Term> terms = (List<Term>) mapper.readValue(inputStream, typeReference);
            termsService.saveTerm(terms);
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
