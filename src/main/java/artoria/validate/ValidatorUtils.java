package artoria.validate;

import artoria.logging.Logger;
import artoria.logging.LoggerFactory;
import artoria.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Validator tools.
 * @author Kahle
 */
public class ValidatorUtils {
    private static final Map<String, Validator> VALIDATOR_MAP = new ConcurrentHashMap<String, Validator>();
    private static Logger log = LoggerFactory.getLogger(ValidatorUtils.class);

    public static void register(String name, Validator validator) {
        Assert.notBlank(name, "Parameter \"name\" must not blank. ");
        Assert.notNull(validator, "Parameter \"validator\" must not null. ");
        String className = validator.getClass().getName();
        log.info("Register \"{}\" to \"{}\". ", className, name);
        VALIDATOR_MAP.put(name, validator);
    }

    public static Validator unregister(String name) {
        Assert.notBlank(name, "Parameter \"name\" must not blank. ");
        Validator remove = VALIDATOR_MAP.remove(name);
        if (remove != null) {
            String className = remove.getClass().getName();
            log.info("Unregister \"{}\" to \"{}\". ", className, name);
        }
        return remove;
    }

    public static boolean validate(String name, Object target) {
        Assert.notBlank(name, "Parameter \"name\" must not blank. ");
        Validator validator = VALIDATOR_MAP.get(name);
        if (validator == null) {
            throw new IllegalStateException(
                    "The validator named \"" + name + "\" could not be found. "
            );
        }
        return validator.validate(target);
    }

}
