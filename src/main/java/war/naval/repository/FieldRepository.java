package war.naval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import war.naval.model.Field;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {

	Field findByXAndY(int x, int y);

}
