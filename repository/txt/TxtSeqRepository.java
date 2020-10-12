package repository.txt;

import repository.SequenceRepository;
import utils.TxtDbFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class TxtSeqRepository implements SequenceRepository {
    private final static String NAME = "name";
    private final static String VALUE = "value";
    private final static String[] head = new String[]{NAME,VALUE};
    @Override
    public int getNext(String seqName) throws IOException {

        TxtDbFile txt = new TxtDbFile(TxtDbFile.folder + "/sequence.txt",head);
        Map<String, String> row = txt.getFirst(r -> r.get(NAME).equals(seqName));
        int seq = 0;
        if(row != null){
            seq = Integer.parseInt(row.get(VALUE)) + 1;
            row.put(VALUE,seq + "");
        }else{
            txt.writeRecord(seqName,"0");
        }
        txt.save();
        return seq;
    }

}
