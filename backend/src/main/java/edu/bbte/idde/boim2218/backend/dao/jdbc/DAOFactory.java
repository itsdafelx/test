package edu.bbte.idde.boim2218.backend.dao.jdbc;

import edu.bbte.idde.boim2218.backend.dao.EventDAO;

public interface DAOFactory {
    EventDAO getEventDAO();
}
