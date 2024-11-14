package edu.bbte.idde.boim2218.desktop;

import edu.bbte.idde.boim2218.backend.dao.jdbc.DAOFactory;
import edu.bbte.idde.boim2218.backend.dao.jdbc.DAOFactoryJdbc;
import edu.bbte.idde.boim2218.backend.service.CalendarService;
import edu.bbte.idde.boim2218.desktop.presentation.AppUI;

public class Main {
    public static void main(String[] args) {
        DAOFactory daoFactory = new DAOFactoryJdbc();
        CalendarService calendarService = new CalendarService(daoFactory.getEventDAO());

        new AppUI(calendarService);
    }
}
