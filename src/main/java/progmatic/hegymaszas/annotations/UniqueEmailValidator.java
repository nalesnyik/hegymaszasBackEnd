package progmatic.hegymaszas.annotations;

import org.springframework.beans.factory.annotation.Autowired;
import progmatic.hegymaszas.services.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator <UniqueEmailConstraint, String> {

    @Autowired
    UserService userService;

    @Override
    public void initialize(UniqueEmailConstraint email) {
    }

    @Override
    public boolean isValid(String email,
                           ConstraintValidatorContext cxt) {
        return !userService.userEmailExists(email);
    }
}
