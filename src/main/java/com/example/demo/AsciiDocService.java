package com.example.demo;

import org.asciidoctor.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URI;

/**
 * @author Munish Chandel
 */
@Service
public class AsciiDocService {
    private static final Logger logger = LoggerFactory.getLogger(AsciiDocService.class);

    private Asciidoctor asciidoctor;
    private Options asciidoctorOptions1;

    @PostConstruct
    public void init() {
        asciidoctor = Asciidoctor.Factory.create();
        asciidoctorOptions1 = getAsciidoctorOptions1();
    }

    private Options getAsciidoctorOptions1() {
        Attributes attributes = AttributesBuilder.attributes()
                .tableOfContents(true)
                .linkCss(false)
                .linkAttrs(true)
                .noFooter(true)
//                .iconFontCdn(URI.create("https://use.fontawesome.com/releases/v5.0.8/css/all.css"))
                .iconFontCdn(URI.create("https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"))
                .icons("font")
                .sourceLanguage("java")
//                .sourceHighlighter("highlightjs")//highlightjs, coderay, prettify, pygments, rouge
                .sourceHighlighter("coderay")//highlightjs, coderay, prettify, pygments, rouge
                .sectionNumbers(true)
                .attribute("!figure-caption")
                .attribute("table-caption!")
                .attribute("note-caption!")
//                .attribute("pygments-css", "inline")
                .get();

        Options asciiDoctorOptions = OptionsBuilder.options()
                .attributes(attributes)
                .headerFooter(false)
                .safe(SafeMode.SERVER)
                .docType("article")
                .inPlace(true)
//                .backend("html5")
                .get();

        return asciiDoctorOptions;
    }

    public String convert(String asciiDoc) {
        long t1 = System.currentTimeMillis();
        try {
            if (asciiDoc!=null && !asciiDoc.isEmpty()) {
                return asciidoctor.convert(asciiDoc, asciidoctorOptions1);
            } else {
                return "";
            }
        } finally {
            long t2 = System.currentTimeMillis();
            logger.debug("Time Taken in AsciiDoc Conversion = {}ms", (t2 - t1));
        }
    }
}
