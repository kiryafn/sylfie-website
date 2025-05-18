package com.sylfie.util;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Component;

@Component
public class HtmlSanitizer {
    public String sanitize(String dirtyHtml) {
        return Jsoup.clean(
                dirtyHtml,
                Safelist.basicWithImages()
                        .addTags("h1", "h2", "h3")
                        .addAttributes("a", "target")
        );
    }
}