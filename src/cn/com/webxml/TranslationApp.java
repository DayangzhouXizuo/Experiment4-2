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
    private JButton exampleButton; // ���ӻ�ȡ����İ�ť

    public TranslationApp() {
        setTitle("English-Chinese Translator");
        setSize(600, 500); // ���󴰿ڳߴ�
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // �������
        outputArea = new JTextArea();
        outputArea.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setBackground(new Color(240, 240, 240)); // ���ñ���Ϊǳ��ɫ
        Border border = BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Translation Output"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
        outputArea.setBorder(border);

        // �����
        inputField = new JTextField();
        inputField.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        inputField.setColumns(20); // ��������������
        Border inputBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Enter Text"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        );
        inputField.setBorder(inputBorder);

        // ��ť
        translateButton = new JButton("Translate");
        translateButton.setFont(new Font("Arial", Font.BOLD, 16)); // ���ð�ť����
        translateButton.setPreferredSize(new Dimension(150, 50)); // ���ð�ť��С
        translateButton.setBackground(new Color(0, 120, 215)); // ���ð�ť������ɫ
        translateButton.setForeground(Color.WHITE); // ���ð�ť������ɫ
        translateButton.setFocusPainted(false); // ȥ������߿�
        translateButton.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        exampleButton = new JButton("Get Example");
        exampleButton.setFont(new Font("Arial", Font.BOLD, 16)); // ���ð�ť����
        exampleButton.setPreferredSize(new Dimension(150, 50)); // ���ð�ť��С
        exampleButton.setBackground(new Color(0, 120, 215)); // ���ð�ť������ɫ
        exampleButton.setForeground(Color.WHITE); // ���ð�ť������ɫ
        exampleButton.setFocusPainted(false); // ȥ������߿�
        exampleButton.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        // ��������Դ�������Ͱ�ť
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.add(inputField);
        inputPanel.add(translateButton);

        JPanel examplePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        examplePanel.add(exampleButton);

        add(new JScrollPane(outputArea), BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH); // �����ͷ��밴ť�ڶ���
        add(examplePanel, BorderLayout.SOUTH); // ���䰴ť�ڵײ�

        // ��ť�¼�����
        translateButton.addActionListener(new TranslateAction());
        exampleButton.addActionListener(new ExampleAction()); // ������䰴ť���¼�����

        setVisible(true);
        setLocationRelativeTo(null); // ���ھ�����ʾ
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
                        result.append(translation).append("\n"); // ֱ����ӷ�����
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
                        result.append(example).append("\n"); // ���������
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