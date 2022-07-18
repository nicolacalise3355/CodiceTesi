package models;

public class BlockHeader {

    private int number;
    private String logsBloom;

    public BlockHeader(int number, String logsBloom) {
        this.number = number;
        this.logsBloom = logsBloom;
    }

    public void toStringer(){
        System.out.println("Number: " + this.number + " Logs="  + this.logsBloom);
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getLogsBloom() {
        return logsBloom;
    }

    public void setLogsBloom(String logsBloom) {
        this.logsBloom = logsBloom;
    }
}
