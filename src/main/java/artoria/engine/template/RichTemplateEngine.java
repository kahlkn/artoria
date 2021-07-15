package artoria.engine.template;

import java.io.Writer;

public interface RichTemplateEngine extends TemplateEngine {

    void render(String name, String encoding, Object data, Writer output);

}
