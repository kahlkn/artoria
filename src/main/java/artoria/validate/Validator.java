package artoria.validate;

/**
 * Validator provider.
 * @author Kahle
 */
public interface Validator {

    /**
     * The validate method of a target validator.
     * @param target The object that is to be validated
     * @return The validation result
     */
    boolean validate(Object target);

}
