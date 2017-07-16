package analyzer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is responsible for reading HTML from input URL
 */
public class DocumentReader {
    private URL url;
    private Logger logger = Logger.getLogger(DocumentReader.class.getName());

    public DocumentReader(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            logger.log(Level.SEVERE, "Invalid URL format");
            System.exit(0);
        }
    }

    public Document readDocument() {
        Document document = null;
        try {
            document = Jsoup.connect(url.toString()).get();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Unable to connect to page");
            System.exit(0);
        }
        return document;
    }
}
