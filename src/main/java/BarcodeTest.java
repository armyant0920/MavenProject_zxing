import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;


public class BarcodeTest {
        private JLabel label;

        public void addComponentsToPane(Container pane) {

            JTextField textField = new JTextField();
            textField.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    JTextField textField = (JTextField) e.getSource();
                    String text = textField.getText();
                    createQRCode(label, text);
                }
            });
            pane.add(textField, BorderLayout.NORTH);

            label = new JLabel();
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            pane.add(label, BorderLayout.CENTER);

        }

        private static void createQRCode(JLabel view, String text) {
            if (view == null) {
                return;
            }

            if (text.equals("")) {
                //資料為空不產生 QrCoce
                view.setIcon(null);
            } else {
                // 二維碼基本參數設置
                Map<EncodeHintType, Object> hints = new HashMap<>();
                hints.put(EncodeHintType.CHARACTER_SET, CharacterSetECI.UTF8);// 設置編碼字符集utf-8
                hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);// 設置糾錯等級L/M/Q/H,糾錯等級越高越不易識別，當前設置等級為最高等級H
                hints.put(EncodeHintType.MARGIN, 1);// 白邊間距，可設置範圍為0-10，但僅四個變化0 1(2) 3(4 5 6) 7(8 9 10)
                // 生成圖片類型為QRCode
                BarcodeFormat format = BarcodeFormat.QR_CODE;
                // 創建位矩陣對象
                BitMatrix matrix = null;
                try {
                    // 生成二維碼對應的位矩陣對象
                    matrix = new MultiFormatWriter().encode(text, format, 300, 300, hints);
                    // 設置位矩陣轉圖片的參數
                    MatrixToImageConfig config = new MatrixToImageConfig(Color.black.getRGB(), Color.white.getRGB());
                    // 位矩陣對象轉BufferedImage對象
                    BufferedImage qrcode = MatrixToImageWriter.toBufferedImage(matrix, config);
                    view.setIcon(new ImageIcon(qrcode));

                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        }

        public BarcodeTest() {
            JFrame frame = new JFrame("HKT線上教室-二維條碼產生器");
            // 獲取螢幕解析度
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setSize(dimension.width / 2, dimension.height / 2);

            //設定視窗顯示在螢幕畫面中間位置
            int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
            int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
            frame.setLocation(x, y);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//設定關閉可以關掉程式
            //在 Pane 畫面中加入元件
            addComponentsToPane(frame.getContentPane());
//        frame.pack();
            frame.setVisible(true);
        }

        //最一開始程式進入點
        public static void main(String[] args) {
            //使用 invokeLater 確保 UI 在排程執行緒內
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new BarcodeTest();
                }
            });
        }
    }

