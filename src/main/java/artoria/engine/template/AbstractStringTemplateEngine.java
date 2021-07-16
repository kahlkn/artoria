package artoria.engine.template;

import artoria.beans.BeanUtils;
import artoria.util.Assert;
import artoria.util.StringUtils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Map;

import static artoria.util.ObjectUtils.cast;

public abstract class AbstractStringTemplateEngine implements StringTemplateEngine {

    @Override
    public void render(Object data, OutputStream out, String tag, InputStream in) {
        Assert.notNull(data, "Parameter \"data\" must not null. ");
        Map<String, Object> dataMap = cast(data instanceof Map ? (Map) data : BeanUtils.beanToMap(data));
        String encoding = (String) dataMap.get("encoding");
        if (StringUtils.isBlank(encoding)) {
            encoding = (String) dataMap.get("charset");
        }
        Assert.notBlank(encoding, "Parameter \"encoding\" must not null. ");
        Charset charset = Charset.forName(encoding);
        render(data, new OutputStreamWriter(out, charset), tag, new InputStreamReader(in, charset));
    }

}
