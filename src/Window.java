import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Window extends JFrame{

    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel buttonPanel;
    private JTextArea textArea;
    public static JButton closeButton;

    public void create(int width, int height, String title){
        setTitle(title);

        mainPanel = new JPanel();
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        buttonPanel = new JPanel();

        mainPanel.setBackground(Color.WHITE);
        leftPanel.setBackground(Color.BLUE);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.CYAN);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.WHITE);

        setSize(new Dimension(width, height));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        add(mainPanel);
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.add(leftPanel, new GridBagConstraints(0,0,1,1,1.0,1.0,GridBagConstraints.NORTH,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0));
        mainPanel.add(rightPanel, new GridBagConstraints(1,0,1,1,1.0,1.0,GridBagConstraints.NORTH,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0));

    }

    public void createViewWindow(int width, int height){
        JPanel panel = new JPanel();

        textArea = new JTextArea();

        setSize(new Dimension(width, height));
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setSize(width, height);
        panel.add(scroll);
        add(panel);
    }

    public void updatePanel(JList list, int index){

        if (index==0){
            leftPanel.add(new JScrollPane(list));
        }
        if (index==1){
            rightPanel.add(new JScrollPane(list));
        }

    }

    public void setText(String text){
        textArea.setText(text);
    }

}
