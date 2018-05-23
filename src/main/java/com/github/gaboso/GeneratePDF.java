package com.github.gaboso;

import com.github.gaboso.entity.DurationTime;
import com.github.gaboso.entity.Enterprise;
import com.github.gaboso.entity.Worker;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

import static org.thymeleaf.templatemode.TemplateMode.HTML;

public class GeneratePDF {

    private static final String OUTPUT_FILE = "test.pdf";
    private static final String UTF_8 = "UTF-8";

    public void download(List<DurationTime> days, Worker worker, Enterprise enterprise, Boolean enableJustificationAllDays) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(HTML);
        templateResolver.setCharacterEncoding(UTF_8);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("days", days);
        context.setVariable("worker", worker);
        context.setVariable("enterprise", enterprise);
        context.setVariable("enableJustificationAllDays", enableJustificationAllDays);

        String renderedHtmlContent = templateEngine.process("template", context);
        String xHtml = convertToXhtml(renderedHtmlContent);

        ITextRenderer renderer = new ITextRenderer();

        Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
        String baseUrl = "file://" + path + "/src/resources/";
        renderer.setDocumentFromString(xHtml, baseUrl);
        renderer.layout();

        try {
            OutputStream outputStream = new FileOutputStream(OUTPUT_FILE);
            renderer.createPDF(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String convertToXhtml(String html) {
        Tidy tidy = new Tidy();
        tidy.setInputEncoding(UTF_8);
        tidy.setOutputEncoding(UTF_8);
        tidy.setXHTML(true);
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(html.getBytes(UTF_8));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            tidy.parseDOM(inputStream, outputStream);
            return outputStream.toString(UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

}