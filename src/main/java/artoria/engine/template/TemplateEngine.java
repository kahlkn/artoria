package artoria.engine.template;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * The template engine.
 * @see <a href="https://en.wikipedia.org/wiki/Template_processor">Template processor</a>
 * @author Kahle
 */
public interface TemplateEngine {

    void render(Object data, OutputStream out, String tag, InputStream in);

}
