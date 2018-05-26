package com.github.gaboso;

import com.github.gaboso.converter.TidyConverter;
import com.github.gaboso.entity.Day;
import com.github.gaboso.entity.Enterprise;
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

import static com.github.gaboso.Config.OUTPUT_FILE;

public class GeneratePDF {

    private static final Logger LOGGER = LoggerFactory.getLogger("com.github.gaboso.GeneratePDF");

    private List<Day> days;
    private Worker worker;
    private Enterprise enterprise;
    private Boolean enableJustificationAllDays;

    public GeneratePDF(List<Day> days, Worker worker, Enterprise enterprise, Boolean enableJustificationAllDays) {
        this.days = days;
        this.worker = worker;
        this.enterprise = enterprise;
        this.enableJustificationAllDays = enableJustificationAllDays;
    }

    public void download() {
        ClassLoaderTemplateResolver templateResolver = TemplateHelper.createTemplateResolver();
        TemplateEngine templateEngine = TemplateHelper.createTemplateEngine(templateResolver);

        Context context = createThymeleafContext();

        String renderedHtmlContent = templateEngine.process("template", context);
        String xhtml = TidyConverter.toXHTML(renderedHtmlContent);

        ITextRenderer renderer = new ITextRenderer();

        try (OutputStream outputStream = new FileOutputStream(OUTPUT_FILE)) {

            String baseURL = getBaseURL();
            renderer.setDocumentFromString(xhtml, baseURL);
            renderer.layout();
            renderer.createPDF(outputStream);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
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

    private Context createThymeleafContext() {
        Context context = new Context();
        context.setVariable("days", days);
        context.setVariable("worker", worker);
        context.setVariable("enterprise", enterprise);
        context.setVariable("enableJustificationAllDays", enableJustificationAllDays);
        return context;
    }

}