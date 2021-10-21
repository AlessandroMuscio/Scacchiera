import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class App {
  private JFrame frame;
  private Painter painter;

  public static void main(String[] args) throws Exception {
    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        new App();
      }
    };

    EventQueue.invokeLater(runnable);
  }

  public App() {
    initializeFrame();

    initializePainter();

    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  private void initializeFrame() {
    frame = new JFrame("Scacchiera");
    frame.setBounds(0, 0, 500, 500);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setBackground(Color.lightGray);
  }

  private void initializePainter() {
    painter = new Painter(8, 8, 1.0);
    addComponentToFrame(painter);
  }

  private void addComponentToFrame(JComponent component) {
    frame.getContentPane().add(component);
  }
}
