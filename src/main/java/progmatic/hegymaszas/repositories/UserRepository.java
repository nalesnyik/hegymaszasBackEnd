package progmatic.hegymaszas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import progmatic.hegymaszas.modell.MyUser;

public interface UserRepository extends JpaRepository<MyUser, String>, UserRepositoryCustom {
    boolean existsMyUserByName(String name);
}
