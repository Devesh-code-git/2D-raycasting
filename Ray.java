// Ray class to keep track of all the rays
public class Ray {
    private int x1, x2, y1, y2;
    private int cx, cy;
    private int collission_x, collission_y;
    private double h, k;
    private int r;
    private int dx, dy;
    private double a, b, c, t;
    private double org_t;

    public Ray(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;

        dx = x2 - x1;
        dy = y2 - y1;
    }

    // Getters
    public int getX2() {return x2;}
    public int getY2() {return y2;}
    public int getColX() {return collission_x;}
    public int getColY() {return collission_y;}

    // Setters
    public void changeXY(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;

        dx = x2 - x1;
        dy = y2 - y1;

        org_t = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }

    public void setCircle(int r, double h, double k, int cx, int cy) {
        this.h = h;
        this.k = k;
        this.r = r;
    }

    public void collission() {
        a = Math.pow(dx, 2) + Math.pow(dy, 2);
        b = 2*((x1 - h)*dx + (y1 - k)*dy);
        c = Math.pow((x1 - h), 2) + Math.pow(y1 - k, 2) - Math.pow(r, 2);

        double disc = Math.pow(b, 2) - 4*a*c;
        double t1, t2;

        if (disc >= 0) {
            t1 = (b*-1 + Math.sqrt(disc)) / (2*a);
            t2 = (b*-1 - Math.sqrt(disc)) / (2*a);

            if (t1 >= 0 && t2 >= 0) {
                t = Math.min(t1,t2);
            } else if (t1 >= 0) {
                t = t1;
            } else if (t2 >=  0) {
                t = t2;
            }
            else {
                t = org_t;
            }
        } else {
            t = org_t;
        }

        if((x1 > (h - r) && x1 < (h + r)) && (y1 > (k - r) && y1 < (k + r))) {
            t = 0;
        }

        x2 = (int)(x1 + t*dx);
        y2 = (int)(y1 + t*dy);
    }
}
