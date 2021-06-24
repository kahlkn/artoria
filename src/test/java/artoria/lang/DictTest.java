package artoria.lang;

import artoria.logging.Logger;
import artoria.logging.LoggerFactory;
import org.junit.Test;

public class DictTest {
    private static Logger log = LoggerFactory.getLogger(DictTest.class);

    @Test
    public void test1() {
        Dict dict = new Dict();
        Dict dict1 = Dict.of();
    }

    @Test
    public void test2() {
        Dict dict = Dict.of("name", null, "age", "19");
        log.info("{}", dict);
    }

}
