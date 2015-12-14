package ovh.gorillahack.wazabi.dao;

import java.io.Serializable;
import java.util.List;

public interface Dao<E> extends Serializable {
	E rechercher(int id);
	E enregistrer(E entit�);
	E mettreAJour(E entit�);
	E recharger(int id);
	void supprimer(int id);
	List<E> lister();
}
