import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JComponent;

public class Painter extends JComponent implements MouseMotionListener {
  private int cols;
  private int rows;
  private int chessboardOffset;
  private Point mousePosition;

  public Painter(int cols, int rows, double chessboardOffset) {
    super();
    this.cols = cols;
    this.rows = rows;
    this.chessboardOffset = approximation(chessboardOffset);

    addMouseMotionListener(this);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    drawChessboard(g);

    if (mousePosition != null)
      outlineHoveredCell(g);
  }

  private void drawChessboard(Graphics g) {
    Point componentDimensions = new Point(getWidth(), getHeight());
    int chessboardSize = Math.min(componentDimensions.x, componentDimensions.y);
    Point cellDimensions = new Point(chessboardSize / cols, chessboardSize / rows);

    for (int i = 0; i <= (cellDimensions.x * cols); i += cellDimensions.x)
      g.drawLine(i + chessboardOffset, chessboardOffset, i + chessboardOffset, (cellDimensions.x * cols));

    for (int i = 0; i <= (cellDimensions.y * rows); i += cellDimensions.y)
      g.drawLine(chessboardOffset, i + chessboardOffset, (cellDimensions.y * rows), i + chessboardOffset);

    g.setColor(Color.white);
    g.fillRect(chessboardOffset + 1, chessboardOffset + 1, (cellDimensions.x * cols) - 1,
        (cellDimensions.y * rows) - 1);

    g.setColor(Color.black);
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if ((i + j) % 2 != 0)
          g.fillRect((cellDimensions.x * j) + chessboardOffset, (cellDimensions.y * i) + chessboardOffset,
              cellDimensions.x + chessboardOffset, cellDimensions.y + chessboardOffset);
      }
    }
  }

  private void drawPointer(Graphics g) {
    g.setColor(Color.yellow);
    g.fillOval(mousePosition.x - 10, mousePosition.y - 10, 25, 25);
  }

  private void outlineHoveredCell(Graphics g) {
    Point componentDimensions = new Point(getWidth(), getHeight());
    int chessboardSize = Math.min(componentDimensions.x, componentDimensions.y);
    Point cellDimensions = new Point(chessboardSize / cols, chessboardSize / rows);

    Point hoveredCell = new Point(-1, -1);

    for (int i = chessboardOffset; i < (cellDimensions.x * cols); i += cellDimensions.x) {
      if (i <= mousePosition.x && mousePosition.x <= (i + cellDimensions.x)) {
        hoveredCell.x = i;
        i = (cellDimensions.x * cols);
      }
    }

    for (int i = chessboardOffset; i < (cellDimensions.y * rows); i += cellDimensions.y) {
      if (i <= mousePosition.y && mousePosition.y <= (i + cellDimensions.y)) {
        hoveredCell.y = i;
        i = (cellDimensions.y * rows);
      }
    }

    if (hoveredCell.x > -1 && hoveredCell.y > -1) {
      Graphics2D g2D = (Graphics2D) g;
      g2D.setColor(Color.blue);
      g2D.setStroke(new BasicStroke(3));
      g2D.drawRect(hoveredCell.x, hoveredCell.y, cellDimensions.x, cellDimensions.y);
    }
  }

  private int approximation(double value) {
    int integerPart = (int) value;
    int firstDecimal = (int) (((value - integerPart) % 10) * 10);

    return (firstDecimal >= 5) ? (int) Math.ceil(value) : (int) Math.floor(value);
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    // TODO Auto-generated method stub

  }

  @Override
  public void mouseMoved(MouseEvent e) {
    mousePosition = e.getPoint();

    repaint();
  }
}
