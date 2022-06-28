package models;

import java.util.ArrayList;

public class Node {

    /**
     * represents a node of the list
     * if the node is pointed means it is reached by prev block
     */

    private int position;
    private boolean isPointed;

    private String logsBloom;
    private ArrayList<String> prevsLogsList = null;

    private int expValue;
    private int preExpValue;

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


    public void printLogsDone(){
        if(isPointed && prevsLogsList.size() > 0){
            System.out.println("Lista logs del blocco: " + this.position + " con indice exp: " + this.expValue);
            System.out.println("Logs Interno: " + this.logsBloom);
            for(int i = 0; i < this.prevsLogsList.size(); i++){
                System.out.println("Logs: " + this.prevsLogsList.get(i));
            }
            System.out.println("---------------------------------------------------");
        }else if(isPointed && prevsLogsList.size() == 0){
            System.out.println("Logs Interno: " + this.logsBloom);
            System.out.println("---------------------------------------------------");
        }

    }

    public void initNode(ArrayList<Node> listaNodi){
        prevsLogsList = new ArrayList<String>();
        if(isPointed){
            for(int i = (this.expValue-this.preExpValue); i<this.expValue-1;i++){
                //System.out.println(listaNodi.get(i).getPosition());
                prevsLogsList.add(listaNodi.get(i).getLogsBloom());
            }
        }
    }

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
}
