import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;

public class Decoding {
    private HuffmanCoding huffmanCoding;
    public Decoding(HuffmanCoding huffmanCoding) {
        this.huffmanCoding = huffmanCoding;
    }
    public void decompress(String inputFileName, String outputFileName) {
        Instant beforeTime = Instant.now();
        String inputText = readFile(inputFileName);

        String[] lines = inputText.split(System.lineSeparator(),4);//헤더를 제외한 문자열 분리(헤더는 \n으로 구분)
        // 0: 허프만 코드의 개수 1: 패딩의 길이 2: 허프만 코드 3: 압축된 문자열
        int nodeCount = Integer.parseInt(lines[0]);//허프만 코드의 개수
        int padding = Integer.parseInt(lines[1]);//패딩의 길이
        String[] huffmanCodeLines = lines[2].split("##");//허프만 코드


        HashMap<Character, String> huffmanCodes2 = new HashMap<>();

        for (int i = 0; i < nodeCount; i++) {
            if(!huffmanCodeLines[i].isEmpty()) {
                String key = huffmanCodeLines[i].split("`[|]")[0];
                String value = huffmanCodeLines[i].split("`[|]")[1];
                huffmanCodes2.put(key.charAt(0), value);//스트링 캐릭터 오류 머시기 저기시매ㅗ우ㅑ매쟈어ㅐㅁㅈ[ㅑ여조ㅡ매쳥ㅈ모ㅜ87ㅊ흐ㅗㅈ[ㅇㅊ8ㅁㅈㅊ왬촘
            }
            else {
                System.out.println("허프만 코드 오류");
                return;
            }
        }//허프만 코드 분리 -> 허프만 코드 해독을 위해 HashMap 저장

        HuffmanNode root = new HuffmanNode('\0', 0);//루트노드
        for (char c : huffmanCodes2.keySet()) {
            HuffmanNode currentNode = root;
            String code = huffmanCodes2.get(c);
            for (int i = 0; i < code.length(); i++) {
                if (code.charAt(i) == '0') {
                    if (currentNode.left == null) {
                        currentNode.left = new HuffmanNode('\0', 0);
                    }
                    currentNode = currentNode.left;
                } else {
                    if (currentNode.right == null) {
                        currentNode.right = new HuffmanNode('\0', 0);
                    }
                    currentNode = currentNode.right;
                }
            }
            currentNode.data = c;
        }//허프만 코드를 이용해 트리 구성

        /*압축 해제 과정*/
        StringBuilder comText = new StringBuilder();
        for (int i = 0; i < lines[3].length(); i++) {
            String byteString = Integer.toBinaryString(lines[3].charAt(i));//문자를 이진 문자열로 변환
            while (byteString.length() < 7) {
                byteString = "0" + byteString;
            }//7비트로 변환
            comText.append(byteString);
        }//압축된 문자열을 7비트씩 끊어서 이진 문자열로 변환

        comText.delete(comText.length() - padding, comText.length());//패딩 제거

        /*허프만 코드를 이용하여 트리를 탐색하고 해제된 문자열을 구성*/
        String decodeText = decodebinaryCode(root, comText.toString());
        Instant afterTime = Instant.now();
        long diffTime = Duration.between(beforeTime, afterTime).toMillis();

        huffmanCoding.setDecodedString(diffTime, comText.toString(), decodeText);
        huffmanCoding.writeDecompressedFile(outputFileName, decodeText);//파일에 저장
    }
    private static String decodebinaryCode(HuffmanNode root, String binaryCode) {
        StringBuilder decodedString = new StringBuilder();
        HuffmanNode currentNode = root;

        for (char bit : binaryCode.toCharArray()) {
            if (bit == '0') {
                currentNode = currentNode.left;
            } else {
                currentNode = currentNode.right;
            }

            if (currentNode.left == null && currentNode.right == null) {
                // 리프 노드에 도달하면 해당 노드의 문자를 결과에 추가하고 다시 루트로 돌아감
                if(currentNode.data=='\n')
                    decodedString.append("\r\n");
                else
                    decodedString.append(currentNode.data);
                currentNode = root;
            }
        }

        return decodedString.toString();
    }
    public String readFile(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8));
            //파일을 UTF-8로 읽어들임
            StringBuilder inputText = new StringBuilder();
            int ch;
            ch = reader.read();
            while (ch != -1) {
                inputText.append((char) ch);
                ch = reader.read();
            }//파일을 읽어 String 형태로 변환

            reader.close();

            return inputText.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
