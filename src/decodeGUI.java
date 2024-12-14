import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class decodeGUI {
    private final JTextArea originText;
    private final JTextArea decodedText;
    private final JLabel timeRate;

    public decodeGUI() {
        int x = 700;
        int y = 515;//가로 세로 크기
        JFrame frame = new JFrame("통계");
        Container container = frame.getContentPane();
        container.setBackground(new Color(59, 144, 221, 255));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(x+14,y+14);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        //========상단 제목=============
        JLabel mainTitle = new JLabel(" HuffmanCoding");
        mainTitle.setOpaque(true);
        mainTitle.setBackground(new Color(255, 255, 255));
        mainTitle.setHorizontalAlignment(SwingConstants.LEFT);
        mainTitle.setFont(new Font("",Font.PLAIN,15));
        mainTitle.setBounds(0,0,x,20);
        frame.add(mainTitle);

        JPanel originPane = new JPanel();
        originPane.setBounds(10,30,x/2-20,y/2+80);
        originPane.setBackground(new Color(255, 255, 255));
        LineBorder lb = new LineBorder(Color.black, 1);
        originPane.setBorder(lb);
        originPane.setLayout(null);
        frame.add(originPane);

        JLabel originLabel = new JLabel("원본");
        labelSet(originLabel);
        originLabel.setBounds(0,0,x/2-20,20);
        originPane.add(originLabel);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(lb);
        scrollPane.setBounds(0,20,x/2-20,y/2+60);
        originPane.add(scrollPane);
        originText = new JTextArea();
        originText.setLineWrap(true);
        originText.setFont(new Font("",Font.PLAIN,15));
        scrollPane.setViewportView(originText);

        JPanel decodedPane = new JPanel();
        decodedPane.setBounds(x/2+10,30,x/2-20,y/2+80);
        decodedPane.setBackground(new Color(255, 255, 255));
        decodedPane.setBorder(lb);
        decodedPane.setLayout(null);
        frame.add(decodedPane);

        JLabel decodedLabel = new JLabel("압축 해제");
        labelSet(decodedLabel);
        decodedLabel.setBounds(0,0,x/2-20,20);
        decodedPane.add(decodedLabel);
        JScrollPane scrollPane2 = new JScrollPane();
        scrollPane2.setBorder(lb);
        scrollPane2.setBounds(0,20,x/2-20,y/2+60);
        decodedPane.add(scrollPane2);
        decodedText = new JTextArea();
        decodedText.setLineWrap(true);
        decodedText.setFont(new Font("",Font.PLAIN,15));
        scrollPane2.setViewportView(decodedText);

        JPanel staticsPane = new JPanel();
        staticsPane.setBounds(10,y/2+120,x-20,y/2-200);
        staticsPane.setBackground(new Color(255, 255, 255, 255));
        staticsPane.setBorder(lb);
        staticsPane.setLayout(null);
        frame.add(staticsPane);
        JLabel timeLabel = new JLabel("소요 시간 : ");
        labelSet(timeLabel);
        timeLabel.setBounds(10,17,80,20);
        staticsPane.add(timeLabel);
        timeRate = new JLabel("0 (ms)");
        labelSet(timeRate);
        timeRate.setBounds(90,17,80,20);
        staticsPane.add(timeRate);

        JButton exit = new JButton("종료");
        exit.setBackground(new Color(255, 255, 255, 255 ));
        exit.setBounds(x-90,y-75,80,45);
        exit.addActionListener(e -> System.exit(0));
        frame.add(exit);

        JButton back = new JButton("메뉴 화면");
        back.setBackground(new Color(255, 255, 255, 255 ));
        back.setBounds(10,y-75,100,45);
        back.addActionListener(e -> {
            frame.dispose();
            new MenuGUI();
        });
        frame.add(back);


        frame.setVisible(true);


    }

    private void labelSet(JLabel label)
    {
        label.setOpaque(true);
        //label.setBorder(lb);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("",Font.PLAIN,15));
        label.setBackground(new Color(255, 255, 255, 255));
    }

    public void setTimeRate(String string) {
        timeRate.setText(string+" (ms)");
    }

    public void setOriginalDataText(String s) {
        originText.setText(s);
    }

    public void setDecodedDataText(String decodedString) {
        decodedText.setText(decodedString);
    }
}
