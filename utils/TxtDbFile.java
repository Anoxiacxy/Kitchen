package utils;



import java.io.*;
import java.util.*;
import java.util.function.Function;

public class TxtDbFile implements AutoCloseable {
    public static String folder = "./txt";
    final private static String SEPARATOR = ",";
    final private List<Map<String, String>> rows = new ArrayList<>();
    final private String path;
    final private String[] head;


    static {
        // ensure dir
        File dir = new File(folder);
        if(!dir.exists()){
            dir.mkdirs();
        }
    }

    public TxtDbFile(String path, String[] head) throws IOException {

        this.path = path;
        this.head = head;
        this.readContext();
    }

    public void clear(){
        this.rows.clear();
    }

    public List<Map<String,String>> getRows(){
        return rows;
    }

    public int removeBy(Function<Map<String,String>,Boolean> func){
        Iterator<Map<String, String>> ita = rows.iterator();
        int ret = 0;
        while (ita.hasNext()){
            if(func.apply(ita.next())){
                ita.remove();
                ret++;
            }
        }
        return ret;
    }

    public Collection<Map<String,String>> getAll(Function<Map<String,String>,Boolean> func){
        List<Map<String,String>> ret = new ArrayList<>();
        for(Map<String,String> row : rows){
            if(func.apply(row)){
                 ret.add(row);
            }
        }
        return ret;
    }

    public Map<String,String> getFirst(Function<Map<String,String>,Boolean> func){
        for(Map<String,String> row : rows){
            if(func.apply(row)){
                return row;
            }
        }
        return null;
    }
    private String join(String[] buf){
        StringBuilder ret = new StringBuilder();
        for(int idx = 0 ;idx < buf.length ;idx ++){
            ret.append(buf[idx]);
            if(idx != buf.length -1){
                ret.append(SEPARATOR);
            }
        }
        return ret.toString();
    }

    public void save() throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path,false))){
             writer.write(join(this.head));
             writer.newLine();
            for(Map<String, String> row: rows){
                 String[] buf = new String[head.length];
                 for(int idx = 0; idx < head.length ; idx++){
                     buf[idx] = row.get(head[idx]);
                 }
                 writer.write(join(buf));
                 writer.newLine();
            }
        }
    }

    private void readContext() throws IOException {
        if(!new File(path).exists()){
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            reader.readLine(); // skip head
            while ((line = reader.readLine()) != null) {
                Map<String, String> row = new HashMap<>();
                String[] splited = line.split(SEPARATOR, -1);
                for (int idx = 0; idx < head.length; idx++) {
                    row.put(head[idx], splited[idx]);
                }
                rows.add(row);
            }
        }
    }


    public void writeRecord(String ... newRow) {
        Map<String, String> row = new HashMap<>();
        for (int idx = 0; idx < head.length; idx++) {
            row.put(head[idx], newRow[idx]);
        }
        rows.add(row);
    }

    @Override
    public void close() throws IOException {
        save();
    }


}
