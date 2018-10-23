import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * Created by izabelawojciak on 17/10/2018.
 */
public class GraphGenerator {

    private Random random;

    public GraphGenerator(){
        random = new Random();
    }

    public void generateGraph(int numberOfVertices, double probability) throws FileNotFoundException, UnsupportedEncodingException {

        System.out.println("GENERATE GRAPH");

        PrintWriter printWriter = new PrintWriter("facebook_combined.txt", "UTF-8");

        for (int i = 0; i < numberOfVertices; i++){
            for (int j = 0; j < numberOfVertices; j++){

                if (random.nextDouble() <= probability && i != j){
                    printWriter.println(i + " " + j);
                }
            }
        }
        printWriter.close();
    }
}
