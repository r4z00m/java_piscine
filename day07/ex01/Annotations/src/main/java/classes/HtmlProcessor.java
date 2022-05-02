package classes;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@SupportedAnnotationTypes(value = {"classes.HtmlForm", "classes.HtmlInput"})
@SupportedSourceVersion(value = SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class HtmlProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Element userForm : roundEnv.getElementsAnnotatedWith(HtmlForm.class)) {
            HtmlForm htmlFormAnn = userForm.getAnnotation(HtmlForm.class);

            stringBuilder
                    .append("<form action = \"")
                    .append(htmlFormAnn.action())
                    .append("\" method = \"")
                    .append(htmlFormAnn.method())
                    .append("\">\n");

            List<? extends Element> userFormElements = userForm.getEnclosedElements();

            for (Element field : roundEnv.getElementsAnnotatedWith(HtmlInput.class)) {
                if (!userFormElements.contains(field)) {
                    continue;
                }

                HtmlInput htmlInputAnn = field.getAnnotation(HtmlInput.class);

                stringBuilder
                        .append("\t<input type = ")
                        .append(htmlInputAnn.type())
                        .append("\" name = \"")
                        .append(htmlInputAnn.name())
                        .append("\" placeholder = \"")
                        .append(htmlInputAnn.placeholder())
                        .append("\">\n");
            }

            stringBuilder.append("\t<input type = \"submit\" value = \"Send\">\n</form>");

            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter("target/classes/" + htmlFormAnn.fileName()))) {
                writer.write(stringBuilder.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }
}
