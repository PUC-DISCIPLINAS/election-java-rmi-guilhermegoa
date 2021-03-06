import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.TreeMap;

public class File {
    public TreeMap<String, Senator> readFile() {
        final int NUMBER = 0;
        final int NAME = 1;
        final int POLITICAL_PARTY = 2;

        TreeMap map = new TreeMap<String, Senator>();

        try {
            InputStream is = new FileInputStream("files/senadores.csv");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader csvReader = new BufferedReader(isr);

            String row;

            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(";");
                Senator senator = new Senator(data[NUMBER], data[NAME], data[POLITICAL_PARTY]);
                map.put(data[NUMBER], senator);
            }

            csvReader.close();

            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    public void writeVotes(String hashName, String candidateNumber) {
        try {
            OutputStream os = new FileOutputStream("files/votes.txt", true);
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter txtWriter = new BufferedWriter(osw);

            String data = hashName + ";" + candidateNumber;

            txtWriter.write(data);
            txtWriter.newLine();

            txtWriter.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public TreeMap<String, String> readVotes() {
        final int HASHNAME = 0;
        final int NUMBER = 1;

        TreeMap mapVotes = new TreeMap<String, String>();
        String row;

        try {
            InputStream is = new FileInputStream("files/votes.txt");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader txtReader = new BufferedReader(isr);

            while ((row = txtReader.readLine()) != null) {
                String[] data = row.split(";");
                mapVotes.put(data[HASHNAME], data[NUMBER]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return mapVotes;
    }
}
