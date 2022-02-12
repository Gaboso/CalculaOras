package com.github.gaboso;

import com.github.gaboso.config.Props;
import com.github.gaboso.converter.TidyConverter;
import com.github.gaboso.entity.Day;
import com.github.gaboso.entity.Employer;
import com.github.gaboso.entity.Worker;
import com.github.gaboso.template.TemplateHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.util.List;

public class GeneratePDF {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeneratePDF.class);

    private final List<Day> days;
    private final Worker worker;
    private final Employer employer;
    private final Boolean enableJustificationAllDays;
    private final String outputFileName;

    public GeneratePDF(List<Day> days, Props props) {
        this.days = days;

        worker = new Worker(props.getWorkerName(), props.getWorkerSocialSecurityId());
        employer = new Employer(props.getEmployerName(), props.getEmployerSocialSecurityId());
        enableJustificationAllDays = props.getEnableDaysJustification();
        outputFileName = props.getOutputFileName();
    }

    public void download() {
        ClassLoaderTemplateResolver templateResolver = TemplateHelper.createTemplateResolver();
        TemplateEngine templateEngine = TemplateHelper.createTemplateEngine(templateResolver);

        Context context = createThymeleafContext();

        String renderedHtmlContent = templateEngine.process("template", context);
        String xhtml = TidyConverter.toXHTML(renderedHtmlContent);

        ITextRenderer renderer = new ITextRenderer();

        try (OutputStream outputStream = new FileOutputStream(outputFileName)) {

            String baseURL = getBaseURL();
            renderer.setDocumentFromString(xhtml, baseURL);
            renderer.layout();
            renderer.createPDF(outputStream);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private Context createThymeleafContext() {
        Context context = new Context();
        context.setVariable("days", days);
        context.setVariable("worker", worker);
        context.setVariable("employer", employer);
        context.setVariable("enableJustificationAllDays", enableJustificationAllDays);
        return context;
    }

    private String getBaseURL() {
        try {
            return FileSystems.getDefault()
                              .getPath("src", "resources")
                              .toUri()
                              .toURL()
                              .toString();
        } catch (MalformedURLException e) {
            LOGGER.error("Error while trying to get path", e);
        }

        return "";
    }

}