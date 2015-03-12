import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by aloys on 12/03/15.
 */
public class Output {

    public static void out(Data d, String filename) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(filename, "UTF-8");
            for (int s = 0; s < d.num_serveurs; s ++) {
                Serveur serv = d.serveurs[s];
                if(serv.groupe < 0) {   //Unused server
                    writer.println('x');
                } else {
                    writer.println("" + serv.rangee + " " + serv.position + " " + serv.groupe);
                }
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
