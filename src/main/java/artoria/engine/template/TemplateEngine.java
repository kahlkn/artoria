package artoria.engine.template;

import java.io.Reader;
import java.io.Writer;

/**
 * Template renderer.
 * @see <a href="https://en.wikipedia.org/wiki/Template_processor">Template processor</a>
 */
public interface TemplateEngine {

    /**
     * Renders the input reader using the context into the output writer.
     * @param data context to use in rendering input string
     * @param output Writer in which to render the output
     * @param tag string to be used as the template name for log messages in case of error
     * @param reader Reader containing the VTL to be rendered
     */
    void render(Object data, Writer output, String tag, Reader reader);

    /**
     * Renders the input string using the context into the output writer.
     * @param data The data to be populated into the template
     * @param output The output stream of rendered result
     * @param tag String to be used as the template name for log messages in case of error
     * @param template The template string (input string containing the VTL to be rendered)
     */
    void render(Object data, Writer output, String tag, String template);

}
