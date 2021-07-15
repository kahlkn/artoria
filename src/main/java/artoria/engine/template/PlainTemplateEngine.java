package artoria.engine.template;

public interface PlainTemplateEngine extends TemplateEngine {

    String render(String template, Object[] arguments);

}
