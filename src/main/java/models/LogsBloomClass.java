package models;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class LogsBloomClass {

    /**
     * La classe rappresenta un logsBloom generato facendo l'or bit a bit dei logsBloom del suo gruppo
     * Un gruppo e' formato da i logsBloom che fanno parte del salto
     */


    private HashMap<Integer, BitSet> logsBloom; //Integer = posizione , Bitset = finaLog

    public LogsBloomClass(){
        this.logsBloom = new HashMap<>();
    }


    public LogsBloomClass(HashMap<Integer, BitSet> logsBloom) {
        this.logsBloom = logsBloom;
    }

    //----- GETTER AND SETTERS ---//

    public HashMap<Integer, BitSet> getLogsBloom() {
        return logsBloom;
    }

    public void setLogsBloom(HashMap<Integer, BitSet> logsBloom) {
        this.logsBloom = logsBloom;
    }

    //--------------------------------------------//

    /**
     * Aggiungo all'hashMap tutti i logsBloom che ho generato che si trovano nella list dei nodi
     * @param nodeList
     */
    public void initByNodeList(ArrayList<Node> nodeList) {
        for(int i = 0; i < nodeList.size(); i++){
            if(nodeList.get(i).getFinalBitSetLogsBloom() != null){
                this.logsBloom.put(nodeList.get(i).getPosition(), nodeList.get(i).getFinalBitSetLogsBloom());
            }
        }
    }


    public void printData(){
        for(Map.Entry<Integer, BitSet> entry: this.logsBloom.entrySet()) {
            System.out.print(entry);
            System.out.print(", ");
        }
    }
}
