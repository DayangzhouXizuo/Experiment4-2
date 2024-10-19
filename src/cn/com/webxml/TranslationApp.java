package cn.com.webxml;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TranslationApp extends JFrame {
    private JTextArea outputArea;
    private JTextField inputField;
    private JButton translateButton;
    private JButton exampleButton; // 增加获取例句的按钮

    public TranslationApp() {
        setTitle("English-Chinese Translator");
        setSize(600, 500); // 增大窗口尺寸
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 输出区域
        outputArea = new JTextArea();
        outputArea.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setBackground(new Color(240, 240, 240)); // 设置背景为浅灰色
        Border border = BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Translation Output"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
        outputArea.setBorder(border);

        // 输入框
        inputField = new JTextField();
        inputField.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        inputField.setColumns(20); // 设置输入框的列数
        Border inputBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Enter Text"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        );
        inputField.setBorder(inputBorder);

        // 按钮
        translateButton = new JButton("Translate");
        translateButton.setFont(new Font("Arial", Font.BOLD, 16)); // 设置按钮字体
        translateButton.setPreferredSize(new Dimension(150, 50)); // 设置按钮大小
        translateButton.setBackground(new Color(0, 120, 215)); // 设置按钮背景颜色
        translateButton.setForeground(Color.WHITE); // 设置按钮文字颜色
        translateButton.setFocusPainted(false); // 去除焦点边框
        translateButton.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        exampleButton = new JButton("Get Example");
        exampleButton.setFont(new Font("Arial", Font.BOLD, 16)); // 设置按钮字体
        exampleButton.setPreferredSize(new Dimension(150, 50)); // 设置按钮大小
        exampleButton.setBackground(new Color(0, 120, 215)); // 设置按钮背景颜色
        exampleButton.setForeground(Color.WHITE); // 设置按钮文字颜色
        exampleButton.setFocusPainted(false); // 去除焦点边框
        exampleButton.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        // 创建面板以存放输入框和按钮
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.add(inputField);
        inputPanel.add(translateButton);

        JPanel examplePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        examplePanel.add(exampleButton);

        add(new JScrollPane(outputArea), BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH); // 输入框和翻译按钮在顶部
        add(examplePanel, BorderLayout.SOUTH); // 例句按钮在底部

        // 按钮事件处理
        translateButton.addActionListener(new TranslateAction());
        exampleButton.addActionListener(new ExampleAction()); // 添加例句按钮的事件处理

        setVisible(true);
        setLocationRelativeTo(null); // 窗口居中显示
    }

    private class TranslateAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String wordToTranslate = inputField.getText().trim();
            if (!wordToTranslate.isEmpty()) {
                try {
                    EnglishChinese service = new EnglishChinese();
                    EnglishChineseSoap englishChineseSoap = service.getEnglishChineseSoap();

                    ArrayOfString translatedResult = englishChineseSoap.translatorString(wordToTranslate);
                    StringBuilder result = new StringBuilder("Translation:\n");
                    for (String translation : translatedResult.getString()) {
                        result.append(translation).append("\n"); // 直接添加翻译结果
                    }

                    outputArea.setText(result.toString());

                } catch (Exception ex) {
                    outputArea.setText("Error: " + ex.getMessage());
                }
            }
        }
    }

    private class ExampleAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String wordToTranslate = inputField.getText().trim();
            if (!wordToTranslate.isEmpty()) {
                try {
                    EnglishChinese service = new EnglishChinese();
                    EnglishChineseSoap englishChineseSoap = service.getEnglishChineseSoap();

                    ArrayOfString exampleResult = englishChineseSoap.translatorSentenceString(wordToTranslate);
                    StringBuilder result = new StringBuilder("Example Sentences:\n");
                    for (String example : exampleResult.getString()) {
                        result.append(example).append("\n"); // 添加例句结果
                    }

                    outputArea.setText(result.toString());

                } catch (Exception ex) {
                    outputArea.setText("Error: " + ex.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TranslationApp::new);
    }
}