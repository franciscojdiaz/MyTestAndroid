package pausasactivas.francisco.com.grabilityapp;

/**
 * Created by francisco.diaz on 16/01/2017.
 */
import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by francisco.diaz on 07/01/2017.
 */

public class  DataBaseConfigUtil extends OrmLiteConfigUtil {




    public static void main(String[] args) throws IOException, SQLException {
        writeConfigFile("ormlite_config.txt");
    }


}
