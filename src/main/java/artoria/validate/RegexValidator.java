package artoria.validate;

import artoria.util.Assert;

import java.util.regex.Pattern;

/**
 * Regex validator.
 * @author Kahle
 */
public class RegexValidator implements Validator {
    private Pattern pattern;
    private String regex;

    public RegexValidator(String regex) {
        Assert.notBlank(regex, "Parameter \"regex\" must not blank. ");
        this.pattern = Pattern.compile(regex);
        this.regex = regex;
    }

    public String getRegex() {

        return regex;
    }

    @Override
    public boolean validate(Object target) {
        if (!(target instanceof String)) {
            throw new IllegalArgumentException(
                    "The argument must be of type string. ");
        }
        return pattern.matcher((String) target).matches();
    }

}
