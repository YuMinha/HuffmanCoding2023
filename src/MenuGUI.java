import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MenuGUI {
    private final JFrame frame;
    public MenuGUI()
    {
        int x = 700;
        int y = 515;//가로 세로 크기
        //========메인 프레임===========
        frame = new JFrame("허프만 코딩_20220703유민하");
        Container container = frame.getContentPane();
        container.setBackground(new Color(255, 255, 255, 255));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(x,y);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        //========상단 제목=============
        JLabel mainTitle = new JLabel(" HuffmanCoding");
        mainTitle.setHorizontalAlignment(SwingConstants.LEFT);
        mainTitle.setFont(new Font("",Font.PLAIN,15));
        mainTitle.setBounds(0,0,x,20);
        frame.add(mainTitle);

        //========메인 패널=============
        JPanel mainPane = new JPanel();
        JButton encodeBtn = new JButton("압축");
        JButton decodeBtn = new JButton("압축 해제");
        mainPane.add(encodeBtn); mainPane.add(decodeBtn);
        mainPane.setBounds(0,20,x,y-20);
        mainPane.setBackground(new Color(59, 144, 221));
        mainPane.setLayout(null);
        frame.add(mainPane);
        //======버튼 설정===========

        decodeBtn.setFont(new Font("",Font.BOLD,15));
        decodeBtn.setBackground(new Color(255, 255, 255, 255));
        decodeBtn.setBounds(x/2+2,y/2-110,130,112);
        decodeBtn.addActionListener(new ButtonListener());
        encodeBtn.setFont(new Font("",Font.BOLD,15));
        encodeBtn.setBackground(new Color(255, 255, 255, 255));
        encodeBtn.setBounds(x/2-132,y/2-110,130,112);
        encodeBtn.addActionListener(new ButtonListener());

        frame.setVisible(true);


    }
    private class ButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            HuffmanCoding huffmanCoding = new HuffmanCoding();
            FileFinder file;//파일을 찾는 기능을 가진 클래스
            File selectFile;//선택된 파일
            String outputFileName;//출력 파일 이름

            if(e.getActionCommand().equals("압축"))
            {
                file = new FileFinder(true);
                selectFile = file.findFile();
                if(selectFile != null)
                {
                    outputFileName = changeExtension(selectFile.getPath());
                    if(outputFileName == null)
                    {
                        System.out.println("파일 확장자 오류");
                        return;
                    }
                    else {
                        huffmanCoding.Encode(selectFile.getPath(), outputFileName, selectFile.length());
                        frame.setVisible(false);
                    }
                }
                else
                {
                    System.out.println("인코딩 파일 오류");
                }
            }
            else if (e.getActionCommand().equals("압축 해제"))
            {
                file = new FileFinder(false);
                selectFile = file.findFile();
                if(selectFile != null)
                {
                    outputFileName = changeExtension(selectFile.getPath());
                    if(outputFileName == null)
                    {
                        System.out.println("파일 확장자 오류");
                        return;
                    }
                    else {
                        huffmanCoding.Decode(selectFile.getPath(), outputFileName);
                        frame.setVisible(false);
                    }
                }
                else
                {
                    System.out.println("디코딩 파일 오류");
                }
            }
        }

        /**파일 이름에서 가장 뒤에 있는 "." 뒤의 텍스트를 현재의 확장자로 인식해서 이름을 바꿔 반환해주는 함수
         */
        private String changeExtension(String fileName) {

            int lastDotIndex = fileName.lastIndexOf(".");

            if (lastDotIndex != -1) {
                String currentExtension = fileName.substring(lastDotIndex + 1);
                String newExtension;

                // 현재의 확장자에 따라 반대로 변경
                if (currentExtension.equals("txt")) {
                    newExtension = "huff";
                } else if (currentExtension.equals("huff")) {
                    newExtension = "txt";
                } else {
                    // 다른 확장자의 경우 변환하지 않고 null 리턴
                    return null;
                }
                // 파일 이름에서 현재의 확장자를 새로운 확장자로 변경
                return fileName.substring(0, lastDotIndex + 1) + newExtension;
            } else {
                // "."이 없는 경우..?
                return null;
            }
        }
    }

}
