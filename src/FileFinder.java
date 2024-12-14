import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class FileFinder {
    private FileNameExtensionFilter fileFilter;

    public FileFinder(Boolean isEncoding) {
        if (isEncoding) {
            setEncodingFilter();
        } else {
            setDecodingFilter();
        }
    }
    private void setEncodingFilter()
    {
        fileFilter = new FileNameExtensionFilter("텍스트 파일 (*.txt)", "txt");
    }
    private void setDecodingFilter()
    {
        fileFilter = new FileNameExtensionFilter("압축 파일 (*.huff)", "huff");
    }
    public File findFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(fileFilter);
        fileChooser.setLocation(500,500);

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

}

