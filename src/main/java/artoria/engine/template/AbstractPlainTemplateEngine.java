package artoria.engine.template;

import artoria.io.IOUtils;
import artoria.util.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public abstract class AbstractPlainTemplateEngine implements PlainTemplateEngine {

    @Override
    public void render(Object data, Writer output, String tag, Reader reader) {
        Assert.notNull(reader, "Parameter \"reader\" must not null. ");
        String template;
        try {
            reader = reader instanceof BufferedReader
                    ? reader : new BufferedReader(reader);
            template = IOUtils.toString(reader);
        }
        catch (IOException e) {
            throw new RenderException(e);
        }
        render(data, output, tag, template);
    }

    @Override
    public void render(Object data, Writer output, String tag, String template) {
        Assert.notNull(output, "Parameter \"output\" must not null. ");
        if (template == null) { return; }
        if (!(data == null || data instanceof Object[])) {
            throw new IllegalArgumentException("Parameter \"data\" must instance of Object[]. ");
        }
        Object[] arguments = (Object[]) data;
        String render = render(template, arguments);
        try {
            output.write(render);
        }
        catch (IOException e) {
            throw new RenderException(e);
        }
    }

    @Override
    public String render(String template, Object[] arguments) {

        throw new UnsupportedOperationException();
    }

}
