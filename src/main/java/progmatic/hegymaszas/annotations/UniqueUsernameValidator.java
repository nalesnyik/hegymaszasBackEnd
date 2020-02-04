package progmatic.hegymaszas.annotations;

import org.springframework.beans.factory.annotation.Autowired;
import progmatic.hegymaszas.services.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsernameConstraint, String> {

    @Autowired
    UserService userService;

        @Override
        public void initialize(UniqueUsernameConstraint username) {
        }

        @Override
        public boolean isValid(String username,
                ConstraintValidatorContext cxt) {
            return !userService.userExists(username);
        }
}
