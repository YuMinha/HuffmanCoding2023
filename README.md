# HuffmanCoding2023

## 프로젝트 개요
효율적인 허프만 압축 알고리즘을 통해 텍스트 파일의 용량을 줄이는 프로그램입니다.  
이 프로젝트는 다양한 텍스트 파일 크기에서 압축 및 해제를 지원하며, GUI 기반으로 사용자 친화적인 환경을 제공합니다.

---

## 구현 결과 요약

### 주요 기능
1. **텍스트 파일 준비**  
   50,000자, 100,000자, 500,000자 이상의 테스트 파일을 준비하여 실험.
2. **허프만 압축 및 해제**  
   - 텍스트 파일을 입력받아 허프만 알고리즘으로 압축.
   - 압축된 파일을 다시 해제하여 원본 파일 복원.
3. **GUI 구현**  
   메뉴 화면, 압축 및 해제 과정을 GUI로 직관적으로 구현.
4. **비트 단위 저장**  
   허프만 트리 구조를 비트 단위로 저장하여 높은 압축 효율 제공.

---

## 실행 흐름도
다음은 프로그램의 실행 흐름을 나타냅니다:

<img src = "https://github.com/user-attachments/assets/daa968b2-992c-47d5-a124-c8109c583aba" width="80%">


---

## 시스템 구성 & 모듈 설계

### 자료구조 설계
#### 1. 입력 데이터
- **File 객체**: 사용자가 선택한 파일을 가져옴.
- **String 객체**: 선택한 파일의 경로를 저장.

#### 2. 내부 데이터
- `HashMap<Character, Integer>`: 각 문자의 빈도수를 저장.
- `PriorityQueue<HuffmanNode>`: 빈도수를 기준으로 허프만 트리를 생성하는 데 사용.

### 시스템 설계 다이어그램
<img src = "https://github.com/user-attachments/assets/b48d09bd-c0cf-4d9d-8ab3-429c79f9b3c5" width="80%">

---

## 자료구조 설계

### 입력 데이터
- **File 객체**: 사용자가 선택한 파일을 가져옵니다.
- **String 객체**: 선택한 파일의 경로를 저장합니다.

### 내부 데이터
- **HashMap<Character, String>**: 각 문자와 그에 해당하는 허프만 코드를 저장합니다.
- **HashMap<Character, Integer>**: 각 문자와 빈도수를 저장합니다.
- **PriorityQueue<HuffmanNode>**: 빈도수를 기반으로 허프만 트리를 생성하는 데 사용되는 큐.

### 출력 데이터
- **byte[]**: 압축된 데이터를 바이트 단위로 저장합니다.
- **String 객체**: 압축 해제된 텍스트를 저장합니다.

---

### 자료구조 설계: `NODE`

#### HuffmanNode 클래스
```java
class HuffmanNode {
    char data;  // 문자 하나
    int frequency; // 빈도수
    HuffmanNode left, right; // 왼쪽 노드, 오른쪽 노드

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
```

### 자료구조 설계: `COMPARATOR`

#### HuffmanNodeComparator 클래스
```java
class HuffmanNodeComparator implements Comparator<HuffmanNode> {
    @Override
    public int compare(HuffmanNode node1, HuffmanNode node2) {
        int freq = Integer.compare(node1.frequency, node2.frequency);
        if (freq != 0) {
            return freq;
        }
        return Integer.compare(node1.getChildCount(), node2.getChildCount());
    }
}
```


---

## 주요 알고리즘

### 1. 압축 시 파일 읽기
```java
BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName), StandardCharsets.UTF_8));
int ch;
while ((ch = reader.read()) != -1) {
    str.append((char) ch);
    charFrequency.put((char) ch, charFrequency.getOrDefault((char) ch, 0) + 1);
}
```

### 2. 허프만 트리 생성 및 코드 부여
```java
private void buildHuffmanTree(HashMap<Character, Integer> charFrequency) {
    PriorityQueue<HuffmanNode> priority = new PriorityQueue<>();
    for (char c : charFrequency.keySet()) {
        priority.offer(new HuffmanNode(c, charFrequency.get(c)));
    }

    while (priority.size() > 1) {
        HuffmanNode left = priority.poll();
        HuffmanNode right = priority.poll();
        HuffmanNode mergedNode = new HuffmanNode('\0', left.frequency + right.frequency);
        mergedNode.left = left;
        mergedNode.right = right;
        priority.offer(mergedNode);
    }
}
```

### 3. 파일 저장
```java
public void writeCompressedFile() {
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), StandardCharsets.UTF_8));
    writer.write(modeCount + System.lineSeparator());
    writer.write(padding + System.lineSeparator());
    for (char c : huffmanCodes.keySet()) {
        writer.write(c + ":" + huffmanCodes.get(c) + "#");
    }
    writer.write(System.lineSeparator());
    writer.write(new String(bytes));
    writer.close();
}
```

---

## 개발 내용 및 실행 화면

### GUI 화면 구성
1. 시작 화면

<img src = "https://github.com/user-attachments/assets/354ecd87-8973-42a7-b360-f97b180d7bcd" width="80%">
  
2. 파일 선택 및 압축 실행

<img src = "https://github.com/user-attachments/assets/abd9aa6f-f257-45db-b74b-62fa0aba6e77" width="80%">

3. 압축 완료 후 통계

<img src = "https://github.com/user-attachments/assets/3d5384cd-e094-4ffc-a908-3fb18bbb8b3c" width="80%">

4. (파일선택 후)압축 해제

<img src = "https://github.com/user-attachments/assets/61ecb67c-e009-4af5-8c7a-bd3d76a005c9" width="80%">


---

## 수행 결과

### 결과 데이터
  <img src = "https://github.com/user-attachments/assets/fa4589ca-51bb-4e0b-98ae-462c928aee25" width="80%">

### 50,000자 100,000자 500,000자 이상의 텍스트 파일에서 동작 가능
  <img src = "https://github.com/user-attachments/assets/3575d200-7b33-48fa-b515-bafd6bfe02e9" width="80%">

---

## 결론
허프만 압축 프로그램은 텍스트 데이터를 효율적으로 압축하고, 원본 파일로 해제하는 기능을 성공적으로 구현했습니다. GUI를 통해 사용자 경험을 개선했으며, 다양한 텍스트 크기에 대해 실험하여 높은 압축 효율을 달성했습니다.

## 개발일
  - 2023년 12월 4일까지
