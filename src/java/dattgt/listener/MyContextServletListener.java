package dattgt.listener;

import java.io.IOException;
import java.util.Properties;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import dattgt.utils.DBHelper;

/**
 *
 * @author DATTGT
 */
public class MyContextServletListener implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Deploying................");
        ServletContext context = sce.getServletContext();
        String siteMapsFile = context.getInitParameter("SITE_MAP_FILE_PATH");
        try {
            Properties siteMaps = DBHelper.getSiteMaps(siteMapsFile, context);
            if (siteMaps != null) {
                context.setAttribute("SITE_MAP", siteMaps);
            }
        } catch (IOException ex) {
            context.log("My Context Listener _ IO" + ex.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Destroying..............");
    }
}
