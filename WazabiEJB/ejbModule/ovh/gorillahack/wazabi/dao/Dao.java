package ovh.gorillahack.wazabi.dao;

import java.util.List;

public interface Dao<E> {
	E enregistrer(E entite);
	
	List<E> lister();
}
