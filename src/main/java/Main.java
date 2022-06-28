import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.BlockHeader;
import models.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    static int NUMBER_BLOCKS = 128; //8192
    static int POW_TIME = 13;
    static int POW_TIME_STOP = 7;
    static int NUMBER_POW = 2;
    static int START_POW = 1;


    public static void main(String[] args) throws IOException {
        System.out.println("***START***");

        //Program Data ---------------------------------------------//

        ArrayList<Node> nodeList = new ArrayList<>();
        ArrayList<Integer> listaPow = new ArrayList<>();

        //Objects utils .. --------------------------------------------//
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        //--------------------------------------------------------------//

        //Make List
        FileReader f;
        f=new FileReader("C:\\Users\\Nicola\\IdeaProjects\\JavaBloomFilterScript\\src\\main\\resources\\files\\blocksLog10k.json");
        BufferedReader b;
        b=new BufferedReader(f);
        String s;
        int count = 0;
        while(count < NUMBER_BLOCKS) {
            s=b.readLine();
            if(s==null)
                break;

            BlockHeader tmpBlock = gson.fromJson(s, BlockHeader.class);
            nodeList.add(new Node(tmpBlock.getNumber(), tmpBlock.getLogsBloom()));
            count = count + 1;
        }
        //--------------------------------------------------------------//

        int tmpNumber = NUMBER_POW;
        int tmpPow = START_POW;


        //Fare POW_TIME volte le cose per creare
        nodeList.get(0).pointSingleNode();
        nodeList.get(1).pointSingleNode();
        for(int i = 0; i < POW_TIME_STOP; i++){
            int tmp = (int) Math.pow(NUMBER_POW, tmpPow);
            //System.out.println("Numero: " + tmp);
            listaPow.add(tmp);
            if(i > 0){
                nodeList.get(tmp-1).pointNode(tmp, listaPow.get(i-1));
            }
            tmpPow++;
        }
        /*
        for(int j = 0; j < nodeList.size(); j++){
            System.out.println(nodeList.get(j).getLogsBloom());
        }

         */


        for(int j = 0; j < nodeList.size(); j++){
            nodeList.get(j).initNode(nodeList);
        }

        for(int j = 0; j < nodeList.size(); j++){
            nodeList.get(j).printLogsDone();
        }


    }

}
