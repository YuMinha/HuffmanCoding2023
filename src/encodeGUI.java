import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class encodeGUI {
    public final JTextArea freqText;
    public final JTextArea huffmanText;
    private final JTextArea compressDataText;
    private final JLabel compressRate;
    private final JLabel compressRate2;
    private final JLabel timeRate;
    private final JLabel averageBits;


    public encodeGUI()
    {
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

        JPanel mainPane = new JPanel();
        mainPane.setBounds(10,30,x/2-20,y-120);
        mainPane.setBackground(new Color(255, 255, 255));
        LineBorder lb = new LineBorder(Color.black, 1);
        mainPane.setBorder(lb);
        mainPane.setLayout(null);
        frame.add(mainPane);

        int x_=mainPane.getWidth();
        int y_=mainPane.getHeight();
        JLabel freqLabel = new JLabel("빈도수");
        labelSet(freqLabel);
        freqLabel.setBounds(0,0,x_/2,20);
        mainPane.add(freqLabel);
        JLabel huffmanLabel = new JLabel("허프만 코드");
        labelSet(huffmanLabel);
        huffmanLabel.setBounds(x_/2,0,x_/2,20);
        mainPane.add(huffmanLabel);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(lb);
        freqText = new JTextArea();
        freqText.setFont(new Font("",Font.PLAIN,15));
        scrollPane.setViewportView(freqText);
        scrollPane.setBounds(0,20,x_/2,y_-20);
        mainPane.add(scrollPane);
        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBorder(lb);
        huffmanText = new JTextArea();
        huffmanText.setFont(new Font("",Font.PLAIN,15));
        scrollPane_1.setViewportView(huffmanText);
        scrollPane_1.setBounds(x_/2,20,x_/2,y_-20);
        mainPane.add(scrollPane_1);

        JPanel staticsPane = new JPanel();
        staticsPane.setBounds(x/2+10,30,x/2-20,y-120);
        staticsPane.setBackground(new Color(255, 255, 255, 255));
        staticsPane.setBorder(lb);
        staticsPane.setLayout(null);
        frame.add(staticsPane);

        int x_s=staticsPane.getWidth();
        int y_s=staticsPane.getHeight();
        JLabel time = new JLabel("소요 시간 : ");
        labelSet(time);
        time.setBounds(10,10,80,20);
        staticsPane.add(time);
        timeRate = new JLabel("0 (ms)");
        labelSet(timeRate);
        timeRate.setBounds(100,10,x_s-110,20);
        staticsPane.add(timeRate);
        JLabel average = new JLabel("평균 비트 수 : ");
        labelSet(average);
        average.setBounds(10,40,100,20);
        staticsPane.add(average);
        averageBits = new JLabel("0");
        labelSet(averageBits);
        averageBits.setBounds(100,40,x_s-130,20);
        staticsPane.add(averageBits);
        JLabel compress = new JLabel("압축률 : ");
        labelSet(compress);
        compress.setBounds(10,70,80,20);
        staticsPane.add(compress);
        compressRate = new JLabel("0%");
        labelSet(compressRate);
        compressRate.setBounds(100,70,x_s-110,20);
        staticsPane.add(compressRate);
        JLabel compress2 = new JLabel("압축률");
        JLabel headerNone = new JLabel("(헤더 고려x) : ");
        labelSet(headerNone);
        headerNone.setBounds(10,120,100,20);
        staticsPane.add(headerNone);
        labelSet(compress2);
        compress2.setBounds(10,100,80,20);
        staticsPane.add(compress2);
        compressRate2 = new JLabel("0%");
        labelSet(compressRate2);
        compressRate2.setBounds(100,120,x_s-110,20);
        staticsPane.add(compressRate2);

        JLabel compressData = new JLabel("압축된 문자열");
        labelSet(compressData);
        compressData.setBorder(lb);
        compressData.setBounds(0,170,x_s,20);
        staticsPane.add(compressData);
        JScrollPane scrollPane_2 = new JScrollPane();
        scrollPane_2.setBorder(lb);
        scrollPane_2.setBounds(0,190,x_s,y_s-190);
        staticsPane.add(scrollPane_2);
        compressDataText = new JTextArea();
        compressDataText.setFont(new Font("",Font.PLAIN,15));
        scrollPane_2.setViewportView(compressDataText);

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
    

    public void setCompressDataText(String text)
    {
        compressDataText.setText(text);
    }
    public void setCompressRateNoneHeader(String rate)
    {
        compressRate2.setText(rate+"%");
    }
    public void setaverageBits(String aver)
    {
        averageBits.setText(aver);
    }
    public void setCompressRate(String rate)
    {
        compressRate.setText(rate+"%");
    }
    public void setTimeRate(String rate)
    {
        timeRate.setText(rate+" (ms)");
    }

}
