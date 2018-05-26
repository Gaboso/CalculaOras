package com.github.gaboso.template;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.nio.charset.StandardCharsets;

import static org.thymeleaf.templatemode.TemplateMode.HTML;

public class TemplateHelper {

    private static final String UTF_8 = StandardCharsets.UTF_8.toString();

    private TemplateHelper() {
    }

    public static ClassLoaderTemplateResolver createTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(HTML);
        templateResolver.setCharacterEncoding(UTF_8);
        return templateResolver;
    }

    public static TemplateEngine createTemplateEngine(ClassLoaderTemplateResolver templateResolver) {
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

}