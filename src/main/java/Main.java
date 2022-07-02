import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.BlockHeader;
import models.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

public class Main {

    /**
     * README:
     *
     * Per verificare che il programma funzioni , recupera il file che contiene i logs e verifica che nella stampa del programma
     * dentro ogni blocco in pos 2^x ci siao tutti i logs dalla posizione (2^x-1)+1 alla posizione (2^x-1)
     * Controllare screen mandato e file
     *
     * Struttura Lista:
     * La struttura della lista e' leggermente diversa da quella pre stabilita
     * partendo da 2^0, 2^1, 2^2 ...  abbiamo due blocchi, il numero 1 e numero 2 con un solo logs
     *
     */

    static int NUMBER_BLOCKS = 128; //8192
    static int POW_TIME = 13;
    static int POW_TIME_STOP = 7;
    static int NUMBER_POW = 2;
    static int START_POW = 1;


    public static void main(String[] args) throws IOException {
        System.out.println("***START***");

        //Program Data -----------------------------------------------//

        ArrayList<Node> nodeList = new ArrayList<>();
        ArrayList<Integer> listaPow = new ArrayList<>();

        //Objects utils .. --------------------------------------------//
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        //--------------------------------------------------------------//

        //Make List
        FileReader f;
        // Leggo il file che contiene gli ultimi 10k blocchi scaricati dalla blockchain
        f=new FileReader("C:\\Users\\Nicola\\IdeaProjects\\JavaBloomFilterScript\\src\\main\\resources\\files\\blocksLog10k.json");
        BufferedReader b;
        b=new BufferedReader(f);
        String s;
        int count = 0;
        while(count < NUMBER_BLOCKS) {
            s=b.readLine();
            if(s==null)
                break;
            //Uso questa classe utils per andare a recuperare il campo "LogsBloom" per ogni blocco letto dal file precedente
            BlockHeader tmpBlock = gson.fromJson(s, BlockHeader.class);
            //Aggiungo alla lista dei nodi il numero del blocco e il logsBloom
            nodeList.add(new Node(tmpBlock.getNumber(), tmpBlock.getLogsBloom()));
            count = count + 1;
        }
        //--------------------------------------------------------------//

        int tmpNumber = NUMBER_POW;
        int tmpPow = START_POW;

        //Flaggo i primi due elementi della lista in quanto non avranno al loro interno una lista di logsbloom (2^0 e 2^1)
        nodeList.get(0).pointSingleNode();
        nodeList.get(1).pointSingleNode();
        //Per ogni potenza del due scelta da (2^0 a 2^POW_TIME_STOP)
        for(int i = 0; i < POW_TIME_STOP; i++){
            int tmp = (int) Math.pow(NUMBER_POW, tmpPow);
            //System.out.println("Numero: " + tmp);
            listaPow.add(tmp); //Lista con le potenze del due
            if(i > 0){
                //Flaggo a true i nodi nelle posizioni 2^x fino a 2^POW_TIME_STOP
                //come parametro passo la potenza del 2 e la potenza precedente
                //(se siamo a 2^4 = 16, passo 16 e 8)
                nodeList.get(tmp-1).pointNode(tmp, listaPow.get(i-1));
            }
            tmpPow++;
        }

        //Inizializzo i nodi flaggati
        for(int j = 0; j < nodeList.size(); j++){
            nodeList.get(j).initNode(nodeList);
        }

        //Vado a verificare che i nodi flaggati contengano la lista dei logsBloom dei nodi precedenti fino alla posizine richieste

        /*
        for(int j = 0; j < nodeList.size(); j++){
            nodeList.get(j).printLogsDone();
        }*/


        for(int j = 0; j < nodeList.size(); j++){
            nodeList.get(j).doOrBitwiseToLogsBloom2();
        }


        /*
        for(int j = 0; j < nodeList.size(); j++){
            nodeList.get(j).printLogsOrBitted();
        }*/




    }

}
