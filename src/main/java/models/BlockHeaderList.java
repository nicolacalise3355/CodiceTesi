package models;

import java.util.ArrayList;

public class BlockHeaderList {

    private ArrayList<BlockHeader> list;

    public BlockHeaderList(ArrayList<BlockHeader> list) {
        this.list = list;
    }

    public ArrayList<BlockHeader> getList() {
        return list;
    }

    public void setList(ArrayList<BlockHeader> list) {
        this.list = list;
    }
}
