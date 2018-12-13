import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.*;

public class Main {

    public static Desktop desktop = null;
    public static File buffer;
    public static String lcurrentPath;
    public static String rcurrentPath;

    public static void main(String[] args) throws IOException, InterruptedException{

        DefaultListModel modelLeft = new DefaultListModel();
        JList listLeft = new JList(modelLeft);
        DefaultListModel modelRight = new DefaultListModel();
        JList listRight = new JList(modelRight);

        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
        }

        File[] roots = File.listRoots();

        if(roots!=null){
            for(int i=0;i<roots.length;i++) {
                modelLeft.addElement(roots[i]);
                modelRight.addElement(roots[i]);
            }
        }

        Thread.sleep(1000);

        listLeft.setVisibleRowCount(5);
        listRight.setVisibleRowCount(5);
        listLeft.setSelectedIndex(0);

        Window window = new Window();
        window.create(500, 600, "FileManager");

        window.updatePanel(listLeft,0);
        window.updatePanel(listRight,1);

        window.setFocusable(false);

        JOptionPane.showMessageDialog(null,"F1 - открыть" + "\n" + "F3 - назад" + "\n" + "F4 - удалить"
                + "\n" + "F5 - создать файл" + "\n" + "F6 - создать папку" + "\n" + "F7 - просмотр" + "\n" +
                "R - переименовать" + "\n" + "Ctrl+C - копировать в буфер" + "\n" + "Ctrl+V - вставить из буфера" + "\n" + "Пробел - переход на другое окно");

        listLeft.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                listRight.clearSelection();
            }
        });

        listRight.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                listLeft.clearSelection();
            }
        });

        listLeft.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    if(listRight.isSelectionEmpty()) {
                        listLeft.clearSelection();
                        listRight.grabFocus();
                        listRight.setSelectedIndex(0);
                    }
                }
            }
        });

        listRight.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if(listLeft.isSelectionEmpty()) {
                        listRight.clearSelection();
                        listLeft.grabFocus();
                        listLeft.setSelectedIndex(0);
                    }
                }
            }
        });

        listLeft.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_F12){
                    System.exit(0);
                }
            }
        });

        listRight.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_F12){
                    System.exit(0);
                }
            }
        });
        //открыть
        listLeft.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_F1){
                    if(listLeft.getSelectedValue()!=null) {
                        if (listLeft.getSelectedValue().toString().contains("C:") | listLeft.getSelectedValue().toString().contains("D:") | listLeft.getSelectedValue().toString().contains("E:") | listLeft.getSelectedValue().toString().contains("F:")  | listLeft.getSelectedValue().toString().contains("G:") ) {
                            File root = (File) listLeft.getSelectedValue();
                            if (root.isDirectory()) {
                                lcurrentPath = listLeft.getSelectedValue().toString();
                                System.out.println("Текущая папка" + lcurrentPath);
                                modelLeft.removeAllElements();
                                modelLeft.addElement(lcurrentPath);
                                File[] children = root.listFiles();
                                if (children != null) {
                                    for (int i = 0; i < children.length; i++) {
                                        modelLeft.addElement(children[i].getName());
                                    }
                                }
                                listLeft.setSelectedIndex(0);
                            }
                            if (root.isFile()) {
                                try {
                                    desktop.open(new File(root.toString()));
                                } catch (IOException ioe) {
                                    ioe.printStackTrace();
                                }
                            }
                        }
                        else{
                            File root = new File(lcurrentPath+"/"+listLeft.getSelectedValue().toString());
                            if (root.isDirectory()) {
                                lcurrentPath = lcurrentPath+"/"+listLeft.getSelectedValue().toString();
                                System.out.println("Текущая папка" + lcurrentPath);
                                modelLeft.removeAllElements();
                                modelLeft.addElement(lcurrentPath);
                                File[] children = root.listFiles();
                                if (children != null) {
                                    for (int i = 0; i < children.length; i++) {
                                        modelLeft.addElement(children[i].getName());
                                    }
                                }
                                listLeft.setSelectedIndex(0);
                            }
                            if (root.isFile()) {
                                try {
                                    desktop.open(new File(root.toString()));
                                } catch (IOException ioe) {
                                    ioe.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        });

        listRight.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_F1){
                    if(listRight.getSelectedValue()!=null) {
                        if (listRight.getSelectedValue().toString().contains("C:") | listRight.getSelectedValue().toString().contains("D:") | listRight.getSelectedValue().toString().contains("E:") | listRight.getSelectedValue().toString().contains("F:") | listRight.getSelectedValue().toString().contains("G:")) {
                            File root = (File) listRight.getSelectedValue();
                            if (root.isDirectory()) {
                                rcurrentPath = listRight.getSelectedValue().toString();
                                System.out.println("Текущая папка" + rcurrentPath);
                                modelRight.removeAllElements();
                                modelRight.addElement(rcurrentPath);
                                File[] children = root.listFiles();
                                if (children != null) {
                                    for (int i = 0; i < children.length; i++) {
                                        modelRight.addElement(children[i].getName());
                                    }
                                }
                                listRight.setSelectedIndex(0);
                            }
                            if (root.isFile()) {
                                try {
                                    desktop.open(new File(root.toString()));
                                } catch (IOException ioe) {
                                    ioe.printStackTrace();
                                }
                            }
                        }
                        else{
                            File root = new File(rcurrentPath+"/"+listRight.getSelectedValue().toString());
                            if (root.isDirectory()) {
                                rcurrentPath = rcurrentPath+"/"+listRight.getSelectedValue().toString();
                                System.out.println("Текущая папка" + rcurrentPath);
                                modelRight.removeAllElements();
                                modelRight.addElement(rcurrentPath);
                                File[] children = root.listFiles();
                                if (children != null) {
                                    for (int i = 0; i < children.length; i++) {
                                        modelRight.addElement(children[i].getName());
                                    }
                                }
                                listRight.setSelectedIndex(0);
                            }
                            if (root.isFile()) {
                                try {
                                    desktop.open(new File(root.toString()));
                                } catch (IOException ioe) {
                                    ioe.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        });
        //кнопка назад
        listLeft.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_F3){
                    if(listLeft.getSelectedValue()!= null && listLeft.getSelectedIndex()!=0){
                        try {
                            File root = new File(lcurrentPath+"/"+listLeft.getSelectedValue().toString());
                            String parent = root.getParent();
                            File paarent = new File(parent);
                            String parentt = paarent.getParent();
                            lcurrentPath = parentt;
                            File paaarent = new File(parentt);
                            File[] children = paaarent.listFiles();
                            if (children != null) {
                                modelLeft.removeAllElements();
                                modelLeft.addElement(lcurrentPath);
                                for (int i = 0; i < children.length; i++) {
                                    modelLeft.addElement(children[i].getName());
                                }
                            }
                        }catch(NullPointerException n){
                            modelLeft.removeAllElements();
                            File[] roots = File.listRoots();
                            if(roots!=null){
                                for(int i=0;i<roots.length;i++) {
                                    modelLeft.addElement(roots[i]);
                                }
                            }
                            listLeft.setSelectedIndex(0);
                        }
                    }
                }
            }
        });

        listRight.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_F3){
                    if(listRight.getSelectedValue()!= null && listRight.getSelectedIndex()!=0){
                        try {
                            File root = new File(rcurrentPath+"/"+listRight.getSelectedValue().toString());
                            String parent = root.getParent();
                            File paarent = new File(parent);
                            String parentt = paarent.getParent();
                            rcurrentPath = parentt;
                            File paaarent = new File(parentt);
                            File[] children = paaarent.listFiles();
                            if (children != null) {
                                modelRight.removeAllElements();
                                modelRight.addElement(rcurrentPath);
                                for (int i = 0; i < children.length; i++) {
                                    modelRight.addElement(children[i].getName());
                                }
                            }
                        }catch(NullPointerException n){
                            modelRight.removeAllElements();
                            File[] roots = File.listRoots();
                            if(roots!=null){
                                for(int i=0;i<roots.length;i++) {
                                    modelRight.addElement(roots[i]);
                                }
                            }
                            listRight.setSelectedIndex(0);
                        }
                    }
                }
            }
        });
        //удалить файл
        listLeft.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F4) {
                    int reply = JOptionPane.showConfirmDialog(null, "Вы действиетльно хотите удалить этот файл?", "Подтверждение удаления", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        if (listLeft.getSelectedValue() != null && listLeft.getSelectedIndex()!=0) {
                            File fileD = new File(lcurrentPath+"/"+listLeft.getSelectedValue().toString());
                            int index = listLeft.getSelectedIndex();
                            if (fileD.delete()) {
                                JOptionPane.showMessageDialog(null, "Файл удален");
                                modelLeft.remove(index);
                            } else {
                                JOptionPane.showMessageDialog(null, "Невозможно удалить файл");
                            }
                        }
                    }
                }
            }
        });

        listRight.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F4) {
                    int reply = JOptionPane.showConfirmDialog(null, "Вы действиетльно хотите удалить этот файл?", "Подтверждение удаления", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        if (listRight.getSelectedValue() != null && listRight.getSelectedIndex()!=0) {
                            File fileD = new File(rcurrentPath+"/"+listLeft.getSelectedValue().toString());
                            int index = listRight.getSelectedIndex();
                            if (fileD.delete()) {
                                JOptionPane.showMessageDialog(null, "Файл удален");
                                modelRight.remove(index);
                            } else {
                                JOptionPane.showMessageDialog(null, "Невозможно удалить файл");
                            }
                        }
                    }
                }
            }
        });
        //переименовать файл
        listLeft.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_R){
                    if(listLeft.getSelectedValue()!=null && listLeft.getSelectedIndex()!=0) {
                        String name = JOptionPane.showInputDialog(null, "Введите новое имя файла");
                        String oldname = lcurrentPath+"/"+listLeft.getSelectedValue().toString();
                        //modelLeft.remove(listLeft.getSelectedIndex());
                        System.out.println(oldname);
                        File file = new File(oldname);
                        file.renameTo(new File(file.getParent()+"/"+name));
                        //modelLeft.addElement(file);
                    }
                }
            }
        });

        listRight.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_R){
                    if(listRight.getSelectedValue()!=null && listRight.getSelectedIndex()!=0) {
                        String name = JOptionPane.showInputDialog(null, "Введите новое имя файла");
                        String oldname = rcurrentPath+"/"+listLeft.getSelectedValue().toString();
                        //modelLeft.remove(listLeft.getSelectedIndex());
                        System.out.println(oldname);
                        File file = new File(oldname);
                        file.renameTo(new File(file.getParent()+"/"+name));
                        //modelLeft.addElement(file);
                    }
                }
            }
        });

        //создать файл
        listLeft.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_F5){
                        if((listLeft.getSelectedValue()!=null || lcurrentPath!=null) && listLeft.getSelectedIndex()!=0) {
                            String name = JOptionPane.showInputDialog(null, "Введите имя файла");
                            File file = new File(lcurrentPath, name);
                            try {
                                if (file.createNewFile()) {
                                    JOptionPane.showMessageDialog(null, "Файл успешно создан");
                                    modelLeft.addElement(file.getName());
                                }
                            } catch (IOException e1) {
                                JOptionPane.showMessageDialog(null, "Невозможно создать файл");
                            }
                        }
                }
            }
        });

        listRight.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_F5){
                    if((listRight.getSelectedValue()!=null || rcurrentPath!=null) && listRight.getSelectedIndex()!=0){
                        String name = JOptionPane.showInputDialog(null, "Введите имя файла");
                        File file = new File(rcurrentPath,name);
                        try {
                            if(file.createNewFile()){
                                JOptionPane.showMessageDialog(null,"Файл успешно создан");
                                modelRight.addElement(file.getName());
                            }
                        } catch (IOException e1) {
                            JOptionPane.showMessageDialog(null,"Невозможно создать файл");
                        }
                    }
                }
            }
        });

        //создать директорию

        listLeft.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_F6){
                    if((listLeft.getSelectedValue()!=null || lcurrentPath!=null) && listLeft.getSelectedIndex()!=0) {
                        String name = JOptionPane.showInputDialog(null, "Введите имя папки");
                        File file = new File(lcurrentPath, name);
                        if (file.mkdir()) {
                            JOptionPane.showMessageDialog(null, "Папка успешно создана");
                            modelLeft.addElement(file.getName());
                        }
                    }
                }
            }
        });

        listRight.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_F6){
                    if((listRight.getSelectedValue()!=null || rcurrentPath!=null) && listRight.getSelectedIndex()!=0) {
                        String name = JOptionPane.showInputDialog(null, "Введите имя папки");
                        File file = new File(rcurrentPath, name);
                        if (file.mkdir()) {
                            JOptionPane.showMessageDialog(null, "Папка успешно создана");
                            modelRight.addElement(file.getName());
                        }
                    }
                }
            }
        });

        //копирование в буфер
        listLeft.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_C && e.isControlDown() && listLeft.getSelectedIndex()!=0){
                    buffer = new File(lcurrentPath+"/"+ listLeft.getSelectedValue().toString());
                    System.out.println(buffer.toPath());
                }
            }
        });

        listRight.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_C && e.isControlDown() && listRight.getSelectedIndex()!=0){
                    buffer = new File(rcurrentPath+"/"+ listRight.getSelectedValue().toString());
                    System.out.println(buffer);
                }
            }
        });

        listLeft.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_V && e.isControlDown()){
                    if(buffer!=null){
                        File dest = new File(lcurrentPath+"/"+buffer.getName());
                        System.out.println(dest);
                        try {
                            Files.copy(buffer.toPath(), dest.toPath());
                            modelLeft.addElement(dest.getName());
                        } catch (IOException e1) {
                            JOptionPane.showMessageDialog(null, "Невозможно копировать файл");
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });

        listRight.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_V && e.isControlDown()){
                    if(buffer!=null){
                        File dest = new File(rcurrentPath+"/"+buffer.getName());
                        System.out.println(dest);
                        try {
                            Files.copy(buffer.toPath(), dest.toPath());
                            modelRight.addElement(dest.getName());
                        } catch (IOException e1) {
                            JOptionPane.showMessageDialog(null, "Невозможно копировать файл");
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });

        //просмотр

        listLeft.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_F7){
                    if(listLeft.getSelectedValue()!=null && listLeft.getSelectedIndex()!=0){
                        try{
                            FileInputStream fstream = new FileInputStream(lcurrentPath+"/"+listLeft.getSelectedValue().toString());
                            BufferedReader br = new BufferedReader(new InputStreamReader(fstream, "Cp866"));
                            String strLine;
                            Window view = new Window();
                            view.createViewWindow(500,500);
                            String buffer = "";
                            while ((strLine = br.readLine()) != null){
                                buffer=buffer+strLine+"\n";
                            }
                            view.setText(buffer);
                        }catch (IOException e1){
                            System.out.println("Ошибка");
                        }
                    }
                }
            }
        });

        listRight.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_F7){
                    if(listRight.getSelectedValue()!=null && listRight.getSelectedIndex()!=0){
                        try{
                            FileInputStream fstream = new FileInputStream(rcurrentPath+"/"+listRight.getSelectedValue().toString());
                            BufferedReader br = new BufferedReader(new InputStreamReader(fstream, "Cp866"));
                            String strLine;
                            Window view = new Window();
                            view.createViewWindow(500,500);
                            String buffer = "";
                            while ((strLine = br.readLine()) != null){
                                buffer=buffer+strLine+"\n";
                            }
                            view.setText(buffer);
                        }catch (IOException e1){
                            System.out.println("Ошибка");
                        }
                    }
                }
            }
        });

        listLeft.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_H){
                    JOptionPane.showMessageDialog(null,"F1 - открыть" + "\n" + "F3 - назад" + "\n" + "F4 - удалить"
                            + "\n" + "F5 - создать файл" + "\n" + "F6 - создать папку" + "\n" + "F7 - просмотр" + "\n" +
                    "R - переименовать" + "\n" + "Ctrl+C - копировать в буфер" + "\n" + "Ctrl+V - вставить из буфера" + "\n" + "Пробел - переход на другое окно");
                }
            }
        });

        listRight.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_H){
                    JOptionPane.showMessageDialog(null,"F1 - открыть" + "\n" + "F3 - назад" + "\n" + "F4 - удалить"
                            + "\n" + "F5 - создать файл" + "\n" + "F6 - создать папку" + "\n" + "F7 - просмотр" + "\n" +
                            "R - переименовать" + "\n" + "Ctrl+C - копировать в буфер" + "\n" + "Ctrl+V - вставить из буфера" + "\n" + "Пробел - переход на другое окно");
                }
            }
        });

        listLeft.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE){
                    if(listLeft.getSelectedValue()!=null){
                        File[] roots = File.listRoots();
                        modelLeft.removeAllElements();
                        if(roots!=null){
                            for(int i=0;i<roots.length;i++) {
                                modelLeft.addElement(roots[i]);
                            }
                        }
                    }
                }
            }
        });

        listRight.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE){
                    if(listRight.getSelectedValue()!=null){
                        File[] roots = File.listRoots();
                        modelRight.removeAllElements();
                        if(roots!=null){
                            for(int i=0;i<roots.length;i++) {
                                modelRight.addElement(roots[i]);
                            }
                        }
                    }
                }
            }
        });

    }

}
