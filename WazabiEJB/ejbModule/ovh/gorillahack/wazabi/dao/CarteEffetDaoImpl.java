package ovh.gorillahack.wazabi.dao;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ovh.gorillahack.wazabi.domaine.CarteEffet;

/**
 * Session Bean implementation class CarteEffetDaoImpl
 */
@Stateless
@Local(Dao.class)
@LocalBean
public class CarteEffetDaoImpl extends DaoImpl<CarteEffet> {
	private static final long serialVersionUID = 8777128905342645928L;

	public CarteEffetDaoImpl() {
		super(CarteEffet.class);
	}

	
}
