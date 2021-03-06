package models;

import utils.Utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Node {

    /**
     *
     * Il nodo rappresenta un oggetto che contiene i dati di un blocco dell blockchain
     * se un nodo ha il flag "isPointed" settato a true significa che il nodo e' raggiunto da
     * una freccia, se raggiunto da una freccia allora avra' al suo interno una lista di String che
     * conterra' i logsBloom di tutti i nodi precedenti fino ad uno specifico nodo
     * ad esmepio il nodo nella posizione 2^3 = 8 conterra' tutti i logsBloom dei nodi tra 2^3 e 2^2 + 1
     * Quindi i logs dei nodi 5,6,7 saranno inseriti nella lista + il suo logsBloom salvato nella var di instanza
     *
     * NOTE:
     * Alcuni metodi presi da internet sono implementati nella classe Utils.java del package Utils
     *
     */

    private int position; //Posizione nella lista dei blocchi della blockchain (Partendo da 15 000 ... scendendo)
    private boolean isPointed; //Se il nodo e' raggiunto da una freccia

    private String logsBloom; //LogsBloom del nodo
    private ArrayList<String> prevsLogsList = null; //Se isPointed == true allora questa lista contiene i logsBloom come citato sopra

    private int expValue; //x di 2^x se questo e' il blocco 2^x
    private int preExpValue; //x-1 di 2^x sequesto e' il blocco 2^x

    private ArrayList<String> listaLogsBytes = new ArrayList<>();
    private String[] listaBinaryLogsBloom;
    private BitSet finalBitSetLogsBloom;

    public Node(int number, String logs) {
        this.position = number;
        this.isPointed = false;
        this.logsBloom = logs;
        this.expValue = -1;
        this.preExpValue = -1;
    }

    public Node(int number) {
        this.position = number;
        this.isPointed = false;
        this.logsBloom = "";
    }


    /**
     * v1.0
     * Questa funzione stampa a schermo prima il proprio LogsBloom (Logs Interno) e successivamente i logsBloom degli elementi che si trovano
     * Tra questo blocco 2^x e 2^x-1 + 1 (Vedi Descrizione Iniziale)
     * se il nodo non e' flaggato non stampiamo il proprio logsBloom in quanto fara' parte della lista di logs del prossimo nodo in posizione 2^x
     *
     * v1.1
     * Anche il logs "Interno" e' stato aggiunto nella lista in ultima posizione in modo averli in ordine quando li recuperiamo
     */
    public void printLogsDone(){
        if(isPointed && prevsLogsList.size() > 0){
            System.out.println("Lista logs del blocco: " + this.position + " con indice exp: " + this.expValue);
            //System.out.println("Logs Interno: " + this.logsBloom);
            for(int i = 0; i < this.prevsLogsList.size(); i++){
                System.out.println("Logs: " + this.prevsLogsList.get(i));
            }
            System.out.println("---------------------------------------------------");
        }else if(isPointed && prevsLogsList.size() == 0){
            System.out.println("Logs Interno: " + this.logsBloom);
            System.out.println("---------------------------------------------------");
        }

    }

    /**
     *
     * @param listaNodi
     * se il nodo e' flaggato allora inserisco nella lista dei logsBloom i logs dei nodi dalla pos (2^x-1)+1 alla posizione (2^x)-1
     * Se questo e' il nodo in posizione 2^3 allora inserisco i nodi 5,6,7 nella lista
     */
    public void initNode(ArrayList<Node> listaNodi){
        prevsLogsList = new ArrayList<String>();
        if(isPointed){
            if(this.expValue == -1){
                this.listaLogsBytes.add(Utils.convertStringToBinary(this.logsBloom));
                listaBinaryLogsBloom = new String[1];
                listaBinaryLogsBloom[0] = Utils.convertStringToBinary(this.logsBloom);
            }else{
                listaBinaryLogsBloom = new String[this.expValue-this.preExpValue];
            }
            int count = 0;
            for(int i = (this.expValue-this.preExpValue); i<this.expValue-1;i++){
                //System.out.println(listaNodi.get(i).getPosition());
                this.prevsLogsList.add(listaNodi.get(i).getLogsBloom());
                this.listaLogsBytes.add(Utils.convertStringToBinary(listaNodi.get(i).getLogsBloom()));
                this.listaBinaryLogsBloom[count] = Utils.convertStringToBinary(listaNodi.get(i).getLogsBloom());
                count = count + 1;
            }
            this.listaLogsBytes.add(Utils.convertStringToBinary(logsBloom));
            this.prevsLogsList.add(logsBloom);
            this.listaBinaryLogsBloom[count] = Utils.convertStringToBinary(logsBloom);
        }
    }

    /**
     *
     * @param valueExp x se il nodo si trova nella posizione 2^X
     * @param precValueExp x-1 se il nodo si trova nella pos 2^x
     * Setto il nodo a true
     */
    public void pointNode(int valueExp, int precValueExp){
        this.expValue = valueExp;
        this.preExpValue = precValueExp;
        this.isPointed = true;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isPointed() {
        return isPointed;
    }

    public void setPointed(boolean pointed) {
        this.isPointed = pointed;
    }

    public String getLogsBloom() {
        return logsBloom;
    }

    public void setLogsBloom(String logsBloom) {
        this.logsBloom = logsBloom;
    }

    public void pointSingleNode() {
        this.isPointed = true;
    }

    private void setFinalBitSetLogsBloom(BitSet tmp) {
        this.finalBitSetLogsBloom = tmp;
    }


    /**
     * Converte una stringa in BitSet
     * @param s
     * @return
     */
    public BitSet stringToBitSet(String s){
        String binaryStr = Utils.convertStringToBinary(s);
        char[] binaryStrArr = binaryStr.toCharArray();
        BitSet newBitSet = new BitSet();
        for(int i = 0; i < binaryStr.length(); i++){
            newBitSet.set(i,Utils.BitCharToBool(binaryStrArr[i]));
        }
        return newBitSet;
    }

    /**
     * Se il nodo e' pointed (Quindi ha una lista di logsbloom dalla sua posizione 2^x fino alla posizione (2^x-1)+ 1 )
     * creo un bitset con il proprio logsBloom (della sua posizione)
     * e faccio un or bit a bit con tutti gli altri bitset che creo che fanno parte della lista
     */
    public void doOrBitwiseForLogsBloom(){
        if(this.isPointed){
            BitSet tmp = stringToBitSet(this.prevsLogsList.get(0));
            for(int i = 1; i < this.prevsLogsList.size(); i++){
                tmp.or(stringToBitSet(this.prevsLogsList.get(i)));
            }
            //Setto il logsBloom finale
            setFinalBitSetLogsBloom(tmp);
            /*
            StringBuilder newStr = new StringBuilder();
            for(int j = 0; j < tmp.size(); j++){
                newStr.append(Utils.booleanToChar(tmp.get(j)));
            }
            //newStr.toString()
            */
        }
    }

    /**
     * Recupero il logsBloom Finale BitSet
     * @return finalBitSetLogsBloom
     */
    public BitSet getFinalBitSetLogsBloom(){
        if(this.isPointed){
            return this.finalBitSetLogsBloom;
        }
        return null;
    }


}
