import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Encoding {
    private HashMap<Character, String> huffCode;
    HashMap<Character, Integer> charFrequency;
    private PriorityQueue<HuffmanNode> priority;
    private HuffmanCoding huffmanCoding;
    public Encoding(HuffmanCoding huffmanCoding){
        this.huffmanCoding = huffmanCoding;
        huffCode = new HashMap<>();
        charFrequency = new HashMap<>();
        priority = new PriorityQueue<>(new HuffmanNodeComparator());
    }
    public void compress(String inputFileName, String outputFileName) {
        //long beforeTime = System.currentTimeMillis();
        Instant beforeTime = Instant.now();
        StringBuilder str = new StringBuilder();
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName), StandardCharsets.UTF_8));
            int ch;
            ch=reader.read();
            while(ch!=-1)
            {
                str.append((char)ch);
                if(ch!=13) {
                    charFrequency.put((char) ch, charFrequency.getOrDefault((char) ch, 0) + 1);
                }
                ch=reader.read();
            }
        }catch (IOException e){
            e.printStackTrace();
        }


        buildHuffmanTree(charFrequency);//허프만 트리 생성
        generateHuffmanCodes(priority.peek(), "");//코드 부여


        StringBuilder compressedText = new StringBuilder();
        String originText = str.toString();

        for (char c :originText.toCharArray()) {
            if(huffCode.get(c)!=null)
                compressedText.append(huffCode.get(c));
        }

        /*압축된 이진 문자열을 7비트씩 끊어서 10진수로 변환*/
        //문자열의 길이가 7의 배수가 아닐 경우 0으로 패딩
        int bitCount = compressedText.length();//문자열의 길이
        int padding = 0;
        if (bitCount % 7 != 0) {
            padding = 7 - bitCount % 7;
        }//패딩의 길이 계산 -> 7의 배수가 되도록
        for (int i = 0; i < padding; i++) {
            compressedText.append("0");
        }//패딩 추가
        int byteCount = compressedText.length() / 7;//문자열을 7비트씩 끊었을 때의 개수
        byte[] bytes = new byte[byteCount];
        for (int i = 0; i < byteCount; i++) {
            String byteString = compressedText.substring(i * 7, (i + 1) * 7);
            bytes[i] = (byte) Integer.parseInt(byteString, 2);//문자열을 10진수로 변환
            //문자열을 7비트씩 끊어서 저장
        }

        //long afterTime = System.currentTimeMillis();
        Instant afterTime = Instant.now();
        //long secDiffTime = (afterTime - beforeTime)/1000;
        long diffTime = Duration.between(beforeTime, afterTime).toMillis();

        huffmanCoding.setMapQueue(diffTime, huffCode, charFrequency, bytes);
        /*넘겨 줘야 하는 것
        * 압축률*/

        /*허프만 코드의 개수, 패딩의 길이(원래의 문자열을 알기 위해), 허프만 코드, 압축된 문자열 저장*/
        huffmanCoding.writeCompressedFile(outputFileName, huffCode.size(), padding, huffCode, bytes);//ㅏ 어려워ㅏ...

    }
    /**허프만 트리 생성*/
    private void buildHuffmanTree(HashMap<Character, Integer> charFrequency) {
        for (char c : charFrequency.keySet()) {
            priority.offer(new HuffmanNode(c, charFrequency.get(c)));
        }

        while (priority.size() > 1) {//우선순위 큐에 노드가 1개 남을 때까지(루트 노드)
            HuffmanNode left = priority.poll();
            HuffmanNode right = priority.poll();

            HuffmanNode mergedNode = new HuffmanNode('\0', left.frequency + right.frequency);
            mergedNode.left = left;
            mergedNode.right = right;

            priority.offer(mergedNode);
        }
    }
    /**순회로 노드를 돌아 코드를 입력*/
    private void generateHuffmanCodes(HuffmanNode root, String code) {
        if (root != null) {
            if (root.left == null && root.right == null) {//리프 노드 -> 문자가 있는 노드
                huffCode.put(root.data, code);//해당 문자에 코드를 입력
            }

            generateHuffmanCodes(root.left, code + "0");
            generateHuffmanCodes(root.right, code + "1");
        }
        else {
            return;
        }
    }
}
