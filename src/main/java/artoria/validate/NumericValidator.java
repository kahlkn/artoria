package artoria.validate;

import artoria.util.StringUtils;

/**
 * Numeric validator.
 * @author Kahle
 */
public class NumericValidator implements Validator {

    @Override
    public boolean validate(Object target) {
        if (!(target instanceof String)) {
            throw new IllegalArgumentException(
                    "The argument must be of type string. ");
        }
        return StringUtils.isNumeric((String) target);
    }

}
