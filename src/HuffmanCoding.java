import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.HashMap;

class HuffmanNode {
    char data;//문자 하나
    int frequency;//빈도수
    HuffmanNode left, right;//왼쪽 노드, 오른쪽 노드

    public HuffmanNode(char data, int frequency) {
        this.data = data;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }

    public int getChildCount() {
        int count = 0;
        if (left != null) {
            count++;
        }
        if (right != null) {
            count++;
        }
        return count;
    }
}
class HuffmanNodeComparator implements Comparator<HuffmanNode> {
    @Override
    public int compare(HuffmanNode node1, HuffmanNode node2) {
        //빈도수 비교
        int freq = Integer.compare(node1.frequency, node2.frequency);
        if(freq != 0){
            return freq;
        }
        //빈도수가 같다면 자식 노드의 개수 비교
        return Integer.compare(node1.getChildCount(), node2.getChildCount());
    }
}

public class HuffmanCoding {
    private HashMap<Character, String> huffmanCodes;//코드 부여된 문자
    private long processTime;//소요 시간
    private HashMap<Character, Integer> charFreq;
    private byte[] bytes;

    //여러 가지 텍스트 파일에 대해 압축을 수행하여 파일별 및 평균적인 통계정보(압축률, 문자당 평균 비트 수, 압축 및 복원 시간 등)를 계산한다.
    //압축률 = (압축된 파일 크기 / 원본 파일 크기) * 100
    //문자당 평균 비트 수 = (압축된 파일 크기 / 원본 파일 크기) * 8
    private encodeGUI gui;
    private decodeGUI deGui;
    private String binaryString;//이진 문자열
    private String decodedString;//복호화된 문자열

    public HuffmanCoding() {}

    public void Encode(String inputFileName, String outputFileName, long inputfileSize) {
        //인코딩 과정
        Encoding encoding = new Encoding(this);
        encoding.compress(inputFileName, outputFileName);

        //압축률 계산
        File file = new File(outputFileName);
        long outputfileSize = file.length();
        //압축률
        double compressRate = (1 - (double) outputfileSize / inputfileSize) * 100;

        //GUI 세팅
        gui = new encodeGUI();
        guiAppend();
        gui.setCompressRate(Double.toString(compressRate));
        gui.setTimeRate(Long.toString(processTime));
        gui.setaverageBits(Double.toString(averageBits(true)));
        gui.setCompressRateNoneHeader(Double.toString(averageBits(false)));
        gui.setCompressDataText(new String(bytes));
    }

    private double averageBits(boolean select) {//문자당 평균 비트 수 계산
        double totalBits = 0;//압축된 파일의 비트 수
        double totalChars = 0;//압축된 파일의 문자 수
        double originalBits = 0;//원본 파일의 비트 수
        for (char c : huffmanCodes.keySet()) {
            int freq = charFreq.get(c);
            totalBits += freq * huffmanCodes.get(c).length();
            totalChars += freq;
            if (c <= 0x7F) { // ASCII 문자
                originalBits += freq * 8;
            } else if (c <= 0x7FF) { // 2바이트 유니코드 문자
                originalBits += freq * 16;
            } else if (c <= 0xFFFF) { // 3바이트 유니코드 문자
                originalBits += freq * 24;
            } else { // 4바이트 유니코드 문자
                originalBits += freq * 32;
            }
        }

        if (select)
            return totalBits / totalChars;//문자당 평균 비트 수
        else
            return (1 - (totalBits / originalBits)) * 100;//압축률
    }
    private void guiAppend()
    {
        for (char c : charFreq.keySet()) {
            gui.freqText.append(" "+c + ": " + charFreq.get(c)+"\n");
        }

        for (char c : huffmanCodes.keySet()) {
            gui.huffmanText.append(" "+c + ": " + huffmanCodes.get(c)+"\n");
        }
    }
    public void setMapQueue(long encodeTime,HashMap<Character, String> huffmanCodes, HashMap<Character, Integer> charFreq , byte[] bytes) {
        this.processTime = encodeTime;
        this.huffmanCodes = huffmanCodes;
        this.charFreq = charFreq;
        this.bytes = bytes;
    }

    public void Decode(String inputFileName, String outputFileName) {

        Decoding decoding = new Decoding(this);
        decoding.decompress(inputFileName, outputFileName);

        deGui = new decodeGUI();
        deGui.setTimeRate(Long.toString(processTime));
        deGui.setOriginalDataText(binaryString);
        deGui.setDecodedDataText(decodedString);
    }
    public void setDecodedString(long decodeTime, String binaryString, String decodedString) {
        this.processTime = decodeTime;
        this.binaryString = binaryString;
        this.decodedString = decodedString;
    }


    /**헤더를 추가하여 파일에 저장*/
    public void writeCompressedFile(String outputFileName, int nodeCount, int padding, HashMap<Character, String> huffmanCodes, byte[] bytes) {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), StandardCharsets.UTF_8));
            writer.write(nodeCount + System.lineSeparator());//허프만 코드의 개수
            writer.write(padding + System.lineSeparator());//패딩의 길이
            for (char c : huffmanCodes.keySet()) {
                writer.write(c+"`|"+huffmanCodes.get(c)+"##");
            }writer.write(System.lineSeparator());
            writer.write(new String(bytes));
            writer.close();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**압축해제된 내용 저장*/
    public void writeDecompressedFile(String outputFileName, String string) {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), StandardCharsets.UTF_8));
            writer.write(string);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
