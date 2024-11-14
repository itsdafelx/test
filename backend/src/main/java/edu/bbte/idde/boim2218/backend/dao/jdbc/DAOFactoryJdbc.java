package edu.bbte.idde.boim2218.backend.dao.jdbc;

import edu.bbte.idde.boim2218.backend.dao.EventDAO;

public class DAOFactoryJdbc implements DAOFactory {
    @Override
    public EventDAO getEventDAO() {
        return new EventDAOJdbc();
    }
}
