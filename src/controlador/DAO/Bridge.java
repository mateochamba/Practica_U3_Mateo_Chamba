package controlador.DAO;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import java.io.File;


/**
 *
 * @author FA506ICB-HN114W
 */

public class Bridge {
    public static String URL = "files" + File.separatorChar;
    private static XStream conection;

    public static XStream getConection() {
        if (conection == null) {
            conection = new XStream(new JettisonMappedXmlDriver()); //Transformar lista como objeto a Json, guardar y obtenerlo de nuevo
            conection.addPermission((AnyTypePermission.ANY));
        }
        return conection;
    }

    public static void setConection(XStream conection) {
        Bridge.conection = conection;
    }
    
    
}
