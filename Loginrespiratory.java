@Repository
public interface LoginRepository extends JpaRepository<Login, Long>{
Login findByUsernameAndPassword(String username, String password);
 
}