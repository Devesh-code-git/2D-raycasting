import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

public class Screen extends JPanel implements MouseListener{
    private int sunX = 400, sunY = 300;
    private final int sunWidth = 100, sunHeight = 100, circleWidth = 150, circleHeight = 150;
    private int circleX = 1000, circleY = 600;
    private final int panel_width = 1400, panel_heigth = 800;
    private final Ray[] rays = new Ray[600];
    private final int numRays = 600;
    private double sunCenter_X = sunX + sunWidth / 2.0;
    private double sunCenter_Y = sunY + sunHeight / 2.0;
    private double circleCenter_X = circleX + circleWidth / 2.0;
    private double circleCenter_Y = circleY + circleHeight / 2.0;
    private int radius = Math.max(panel_width, panel_heigth);
    private int circleRadius = circleWidth / 2;
    private int dsunX = 0, dsunY = 0, dcircleX = 0, dcircleY = 0;
    private boolean dragging_sun = false, dragging_circle = false;
    private int x2, y2;

    public static void main(String[] args) { // Main method
        new Frame(); // Game screen
    }

    public Screen() {
        this.setPreferredSize(new Dimension(panel_width, panel_heigth));
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        this.addMouseListener(this);

        // Adding rays
        for (int i = 0; i < numRays; i++) {
            double angle = 2 * Math.PI * i / numRays;
            int x2 = (int) (sunCenter_X + radius * Math.cos(angle));
            int y2 = (int) (sunCenter_Y + radius * Math.sin(angle));

            rays[i] = new Ray ((int)sunCenter_X, (int)sunCenter_Y, x2, y2);
            rays[i].setCircle(circleRadius, circleCenter_X, circleCenter_Y, circleX, circleY);
        }
        updateRays();

        // Code for moving the sun and circle
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (dragging_sun) {
                    sunX = e.getX() - dsunX;
                    sunY = e.getY() - dsunY;
                    sunCenter_X = sunX + sunWidth / 2.0;
                    sunCenter_Y = sunY + sunHeight / 2.0;

                    if ((sunX + sunWidth) > (panel_width + 40) || sunX < -40) {
                        dragging_sun = false;
                        sunX = panel_width / 2;
                        sunCenter_X = sunX + sunWidth / 2.0;
                    }

                    if ((sunY + sunHeight) > (panel_heigth + 40) || sunY < -40) {
                        dragging_sun = false;
                        sunY = panel_heigth / 2;
                        sunCenter_Y = sunY + sunHeight / 2.0;
                    } 

                    updateRays();
                    repaint();
                }

                if (dragging_circle) {
                    circleX = e.getX() - dcircleX;
                    circleY = e.getY() - dcircleY;
                    circleCenter_X = circleX + circleWidth / 2.0;
                    circleCenter_Y = circleY + circleHeight / 2.0;

                    if ((circleX + circleWidth) > (panel_width + 40) || circleX < -40) {
                        dragging_circle = false;
                        circleX = 1000;
                        circleCenter_X = circleX + circleWidth / 2.0;
                    }

                    if ((circleY + circleHeight) > (panel_heigth + 40) || circleY < -40) {
                        dragging_circle = false;
                        circleY = 600;
                        circleCenter_Y = circleY + circleHeight / 2.0;
                    }

                    for (int i = 0; i < numRays; i++) {
                        rays[i].setCircle(circleRadius, circleCenter_X, circleCenter_Y, circleX, circleY);
                    }
                    
                    updateRays();
                    repaint();
                }
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Makes the graphics look better
        g2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY); // Makes the graphics look better

        // Creating circle graphic
        g2D.setColor(Color.WHITE);
        g2D.fillOval(circleX, circleY, circleWidth, circleHeight);

        // Creating sun graphic
        g2D.setColor(Color.ORANGE);
        g2D.fillOval(sunX, sunY, sunWidth, sunHeight);

        // Drawing the rays
        for (int i = 0; i < numRays; i++) {
            int x2 = rays[i].getX2();
            int y2 = rays[i].getY2();

            g2D.setColor(Color.ORANGE);
            g2D.drawLine((int)sunCenter_X, (int)sunCenter_Y, x2, y2);
        }
    }

    private void updateRays() {
        for (int i = 0; i < numRays; i++) {
            double angle = 2 * Math.PI * i / numRays;
            int x2 = (int) (sunCenter_X + radius * Math.cos(angle));
            int y2 = (int) (sunCenter_Y + radius * Math.sin(angle));

            rays[i].changeXY((int)sunCenter_X, (int)sunCenter_Y, x2, y2);
            rays[i].collission();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX() > sunX && e.getX() < (sunX + sunWidth) && e.getY() > sunY && e.getY() < (sunY + sunHeight)) {
            dragging_sun = true;
            dsunX = e.getX() - sunX;
            dsunY = e.getY() - sunY;
        }

        if (e.getX() > circleX && e.getX() < (circleX + circleWidth) && e.getY() > circleY && e.getY() < (circleY + circleHeight)) {
            dragging_circle = true;
            dcircleX = e.getX() - circleX;
            dcircleY = e.getY() - circleY;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        dragging_sun = false;
        dragging_circle = false;
    }
}
